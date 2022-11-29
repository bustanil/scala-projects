package com.bustanil.journey

import cats.effect._
import com.comcast.ip4s._
import doobie.util.transactor.Transactor
import doobie.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router

object Main extends IOApp {

  case class Country(code: String, name: String, population: Long)
  val xa = Transactor.fromDriverManager[IO]("org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "postgres")
  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      val n = "Indonesia"
      val q = sql"select code, name, population from country where name = $n".query[Country].unique
      val response = for {
        indonesia <- q.transact(xa)
        countryName <- indonesia match {
                        case Country(_, name, _) => IO(name)
                      }
        result <- Ok(s"Hello, $name, country name: ${countryName}")
      } yield result

      response.handleErrorWith(_ => BadRequest("LOL"))
  }

  def run(args: List[String]): IO[ExitCode] = {
    val httpApp = Router("/" -> helloWorldService).orNotFound

    EmberServerBuilder.default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)

  }
}
