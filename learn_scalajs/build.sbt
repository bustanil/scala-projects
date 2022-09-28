enablePlugins(ScalaJSPlugin)

name := "Scala.js tutorial"
scalaVersion := "2.13.1"

scalaJSUseMainModuleInitializer := true

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0"

