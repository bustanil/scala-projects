package com.bustanil.todoscala

import cats.data.OptionT
import cats.effect.Async
import cats.effect.kernel.Resource
import cats.syntax.all.*
import com.comcast.ip4s.*
import com.zaxxer.hikari.HikariConfig
import doobie.hikari.HikariTransactor
import fs2.io.net.Network
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.{ErrorAction, ErrorHandling}

import scala.annotation.unused


object Server:

//  private def errorRoute[F[_] : Async]: HttpRoutes[F] = {
//    val dsl = new Http4sDsl[F] {}
//    import dsl.*
//    HttpRoutes.of[F] {
//      case GET -> Root / "error" =>
//        throw new Exception("Hey don't swallow me")
//    }
//  }
//
  private def errorHandler[F[_] : Async](t: Throwable, @unused msg: => String): OptionT[F, Unit] =
    OptionT.liftF(Async[F].delay(t.printStackTrace()))

  private def withErrorLogging[F[_] : Async](routes: HttpRoutes[F]) = ErrorHandling.Recover.total(
    ErrorAction.log(
      routes,
      messageFailureLogAction = errorHandler,
      serviceErrorLogAction = errorHandler
    )
  )

  def run[F[_] : Async : Network]: F[Nothing] = {

    val todo = LiveTodo.make(transactor)
    val todoRoutes = Routes.todoRoute(todo)
    //        val httpApp = todoRoutes.orNotFound
    //        val finalHttpApp = Logger.httpApp(true, true)(httpApp)
    for {
      server <- EmberServerBuilder.default[F]
        .withHost(ipv4"0.0.0.0")
        .withPort(port"8080")
        .withHttpApp(withErrorLogging(todoRoutes).orNotFound)
        .build
        .useForever
    } yield server
  }

  def transactor[F[_] : Async]: Resource[F, HikariTransactor[F]] =
    for {
      hikariConfig <- Resource.pure {
        // For the full list of hikari configurations see https://github.com/brettwooldridge/HikariCP#gear-configuration-knobs-baby
        val config = new HikariConfig()
        config.setDriverClassName("com.mysql.cj.jdbc.Driver")
        config.setJdbcUrl("jdbc:mysql://localhost:3306/todo")
        config.setUsername("root")
        config.setPassword("root")
        config
      }
      xa <- HikariTransactor.fromHikariConfig[F](hikariConfig)
    } yield xa