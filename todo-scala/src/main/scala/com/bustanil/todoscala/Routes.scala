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
          resp <- Ok(GetTodoResponse(todos))
        } yield resp
      case req@POST -> Root / "todo" =>
        for {
          req <- req.as[CreateTodoRequest]
          uuid <-  generator.uuid
          _ <- T.add(req.todo.copy(id = uuid))
          resp <- Ok()
        } yield resp
      case req@PATCH -> Root / "todo" / UUIDVar(itemId) =>
        for {
          req <- req.as[CompleteTodoRequest]
          _ <- T.complete(itemId, req.completed)
          resp <- Ok()
        } yield resp
      case DELETE -> Root / "todo" / UUIDVar(itemId) =>
        for {
          _ <- T.delete(itemId)
          resp <- Ok()
        } yield resp
    }