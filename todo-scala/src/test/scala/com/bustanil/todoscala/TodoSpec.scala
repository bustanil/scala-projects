package com.bustanil.todoscala

import cats.effect.IO
import org.http4s.*
import org.http4s.implicits.*
import munit.CatsEffectSuite

class TodoSpec extends CatsEffectSuite:

  test("get todo returns 200") {
    val statusIO = for {
      todo <- InMemoryTodo.make[IO]
      getTodo = Request[IO](Method.GET, uri"/todo")
      response <- Routes.todoRoute(todo).orNotFound(getTodo)
      status = response.status
    } yield status
    assertIO(statusIO ,Status.Ok)
  }

  test("returns empty todos") {
    val bodyIO = for {
      todo <- InMemoryTodo.make[IO]
      getTodo = Request[IO](Method.GET, uri"/todo")
      response <- Routes.todoRoute(todo).orNotFound(getTodo)
      responseBody <- response.as[String]
    } yield responseBody
    assertIO(bodyIO, "{\"todos\":[]}")
  }
