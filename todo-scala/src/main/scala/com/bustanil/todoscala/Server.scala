package com.bustanil.todoscala

import cats.effect.Async
import cats.syntax.all.*
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*
import org.http4s.server.middleware.Logger

object Server:

 def run[F[_]: Async: Network]: F[Nothing] = {
  for {
    todo <- InMemoryTodo.make[F]
    todoRoutes = Routes.todoRoute(todo)
    httpApp = todoRoutes.orNotFound
    finalHttpApp = Logger.httpApp(true, true)(httpApp)
    server <- EmberServerBuilder.default[F]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(finalHttpApp)
      .build
      .useForever
  } yield server
}