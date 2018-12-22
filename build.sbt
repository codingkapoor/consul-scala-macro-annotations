
name := "scala-macro-annotations-consul-service-registration"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.7"

lazy val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.12.8"
lazy val macroParadise = compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
lazy val consulClient = "com.orbitz.consul" % "consul-client" % "1.2.6"

lazy val macroannotations = (project in file("./macroannotations")).
  settings(libraryDependencies ++= Seq(scalaCompiler, macroParadise))

lazy val myservice = (project in file("./myservice")).
  settings(libraryDependencies ++= Seq(scalaCompiler, macroParadise, consulClient)).
  dependsOn(macroannotations).
  enablePlugins(JavaAppPackaging)
