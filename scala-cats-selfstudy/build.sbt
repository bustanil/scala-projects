ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cats-selfstudy",
    idePackagePrefix := Some("com.bustanil")
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.12.0"
)