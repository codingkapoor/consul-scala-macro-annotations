package com.codingkapoor.myservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.codingkapoor.macroannotations.EnableServiceDiscovery

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

@EnableServiceDiscovery
object MyService extends App {

  implicit val system = ActorSystem("myservice-actor-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val interface = RuntimeEnvironment.appConfig.getString("myservice.http.interface")
  val port = RuntimeEnvironment.appConfig.getString("myservice.http.port").toInt

  val serverBinding =
    Http().bindAndHandle(
      pathSingleSlash {
        get {
          complete(StatusCodes.OK, "Microservices can use @EnableServiceDiscovery macro-annotation to register themselves to Consul out of the box.")
        }
      },
      interface,
      port
    )

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"MyService started @ http://$interface:$port")
    case Failure(e) =>
      Console.err.println(s"MyService could not start!")
      e.printStackTrace()
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
}
