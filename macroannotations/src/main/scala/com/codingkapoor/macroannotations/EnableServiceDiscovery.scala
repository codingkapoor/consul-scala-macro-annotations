package com.codingkapoor.macroannotations

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.language.experimental.macros
import scala.reflect.macros.blackbox

@compileTimeOnly("enable macro paradise to expand macro annotations")
class EnableServiceDiscovery extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro EnableServiceDiscovery.impl
}

object EnableServiceDiscovery {
  def impl(c: blackbox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    val result =  {
      annottees.map(_.tree).toList match {

        case q"$mods object $tname extends { ..$earlydefns } with ..$parents { $self => ..$stats }" :: Nil => {
          q"""$mods object $tname extends { ..$earlydefns } with ..$parents {
            $self => ..$stats

            def getConsulUrl = {
              val consulInterface = RuntimeEnvironment.appConfig.getString("consul.interface")
              val consulPort = RuntimeEnvironment.appConfig.getString("consul.port")

              val consulUrl = s"http://$$consulInterface:$$consulPort"
              consulUrl
            }

            def getServiceName = {
              val pkg = getClass.getPackage
              val serviceTitle = pkg.getImplementationTitle
              val serviceVersion = pkg.getImplementationVersion

              val serviceName = s"v$${serviceVersion}_$$serviceTitle"
              serviceName
            }

            def getServicePort = {
              val servicePort = RuntimeEnvironment.appConfig.getInt("myservice.http.port")
              servicePort
            }

            def getHealthCheckScript = {
              val healthCheckScript = s"$${RuntimeEnvironment.getHomeDir}/bin/status.sh"
              healthCheckScript
            }

            def getTags = {
              val tags = RuntimeEnvironment.appConfig.getList("myservice.tags").unwrapped().toArray().map(_.toString).toList
              tags
            }

            def registerService() = {
              import com.orbitz.consul.Consul
              import com.orbitz.consul.model.agent.ImmutableRegistration
              import com.orbitz.consul.model.agent.Registration
              import scala.collection.JavaConverters._

              val consulClient = Consul.builder().withUrl(getConsulUrl).build()
              val agentClient = consulClient.agentClient

              val hostName = java.net.InetAddress.getLocalHost.getHostName
              val serviceId = Util.md5(hostName + getServiceName)

              val service =
                ImmutableRegistration.builder().
                  id(serviceId).
                  name(getServiceName).
                  port(getServicePort).
                  check(Registration.RegCheck.args(List(getHealthCheckScript).asJava, 5L)).
                  tags(getTags.asJava).
                  build()

              agentClient.register(service)
            }

            registerService()
          }"""
        }
        case _ => c.abort(c.enclosingPosition, "Annotation @EnableServiceDiscovery can be used only with case classes which extends Scalar trait")
      }
    }

    c.Expr[Any](result)
  }
}
