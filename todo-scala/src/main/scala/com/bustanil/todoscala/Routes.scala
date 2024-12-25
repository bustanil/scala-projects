package com.bustanil.todoscala

import cats.effect.Concurrent
import cats.syntax.all.*
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object Routes:

  def todoRoute[F[_] : Concurrent](T: Todo[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / "todo" =>
        for {
          todos <- T.list
          resp <- Ok(TodoResponse(todos))
        } yield resp
      case req@POST -> Root / "todo" =>
        for {
          todo <- req.as[TodoItem]
          _ <- T.add(todo)
          resp <- Ok()
        } yield resp
      case req@POST -> Root / "todo" / IntVar(itemId) / "complete" =>
        for {
          _ <- T.complete(itemId)
          resp <- Ok()
        } yield resp
      case req@DELETE -> Root / "todo" / IntVar(itemId) =>
        for {
          _ <- T.delete(itemId)
          resp <- Ok()
        } yield resp
    }