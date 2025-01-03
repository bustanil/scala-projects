val Http4sVersion = "0.23.30"
val CirceVersion = "0.14.10"
val MunitVersion = "1.0.3"
val LogbackVersion = "1.5.15"
val MunitCatsEffectVersion = "2.0.0"
val DoobieVersion = "1.0.0-RC6"
val MySQLVersion = "9.1.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.bustanil",
    name := "todo-scala",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "3.3.3",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % Http4sVersion,
      "org.http4s" %% "http4s-ember-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect" % MunitCatsEffectVersion % Test,
      "ch.qos.logback" % "logback-classic" % LogbackVersion % Runtime,
      "com.mysql" % "mysql-connector-j" % MySQLVersion % Runtime,

      "org.tpolecat" %% "doobie-core" % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari" % DoobieVersion, // HikariCP transactor.
      "org.tpolecat" %% "doobie-specs2" % DoobieVersion % Test, // Specs2 support for typechecking statements.
      "org.tpolecat" %% "doobie-scalatest" % DoobieVersion % Test // ScalaTest support for typechecking statements.
    ),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )
