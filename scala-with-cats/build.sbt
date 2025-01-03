val scala3Version = "3.6.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Scala with Cats",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.typelevel" %% "cats-core" % "2.12.0"
  )
