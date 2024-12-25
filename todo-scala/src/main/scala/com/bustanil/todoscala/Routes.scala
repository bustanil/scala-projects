package com.bustanil.todoscala

import cats.effect.{Async, Concurrent}
import cats.syntax.all.*
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import tech.ant8e.uuid4cats.UUIDGenerator


object Routes:

  def todoRoute[F[_] : Concurrent : Async](T: Todo[F])(generator: UUIDGenerator[F]): HttpRoutes[F] =
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
          uuid <-  generator.uuid
          _ <- T.add(todo.copy(id = uuid))
          resp <- Ok()
        } yield resp
      case req@POST -> Root / "todo" / UUIDVar(itemId) / "complete" =>
        for {
          _ <- T.complete(itemId)
          resp <- Ok()
        } yield resp
      case req@DELETE -> Root / "todo" / UUIDVar(itemId) =>
        for {
          _ <- T.delete(itemId)
          resp <- Ok()
        } yield resp
    }