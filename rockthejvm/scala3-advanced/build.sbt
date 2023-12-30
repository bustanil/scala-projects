val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-advanced",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
