
name := "consul-scala-macro-annotations"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val scalaCompilerVersion = "2.12.8"
lazy val macroParadiseVersion = "2.1.0"
lazy val slf4jVersion = "1.7.25"
lazy val typeSafeConfigVersion = "1.3.1"
lazy val consulVersion = "1.2.6"
lazy val akkaHttpVersion = "10.1.5"
lazy val akkaVersion    = "2.5.18"

lazy val scalaCompiler = "org.scala-lang" % "scala-compiler" % scalaCompilerVersion
lazy val macroParadise = compilerPlugin("org.scalamacros" % "paradise" % macroParadiseVersion cross CrossVersion.full)

lazy val macroAnnotations = (project in file("./macroannotations")).
  settings(libraryDependencies ++= Seq(scalaCompiler, macroParadise))

lazy val myService = (project in file("./myservice")).
  settings(
    libraryDependencies ++= Seq(
      scalaCompiler,
      macroParadise,
      // -- Slf4j --
      "org.slf4j" % "slf4j-simple" % slf4jVersion,
      // -- Config --
      "com.typesafe" % "config" % typeSafeConfigVersion,
      // -- Consul ---
      "com.orbitz.consul" % "consul-client" % consulVersion,
      // -- Akka Http --
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion
    )
  ).
  aggregate(macroAnnotations).
  dependsOn(macroAnnotations).
  enablePlugins(JavaAppPackaging)
