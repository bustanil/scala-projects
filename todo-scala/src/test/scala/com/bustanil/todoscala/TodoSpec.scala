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

  test("return some todos") {
    val bodyIO = for {
      todo <- InMemoryTodo.make[IO]
      _ <- todo.add(TodoItem(1, "Learn Scala 3", false))
      _ <- todo.add(TodoItem(2, "Learn Scala 3", false))
      getTodo = Request[IO](Method.GET, uri"/todo")
      response <- Routes.todoRoute(todo).orNotFound(getTodo)
      responseBody <- response.as[String]
    } yield responseBody
    assertIO(bodyIO, "{\"todos\":[{\"id\":1,\"description\":\"Learn Scala 3\",\"completed\":false},{\"id\":2,\"description\":\"Learn Scala 3\",\"completed\":false}]}")
  }

  test("complete a todo") {
    val statusAndTodos = for {
      todo <- InMemoryTodo.make[IO]
      _ <- todo.add(TodoItem(1, "Learn Scala 3", false))
      _ <- todo.add(TodoItem(2, "Learn Scala 3", false))
      completeTodo = Request[IO](Method.POST, uri"/todo/1/complete")
      response <- Routes.todoRoute(todo).orNotFound(completeTodo)
      todos <- todo.list
      status = response.status
    } yield (status, todos)

    statusAndTodos.flatMap((status, todos) => IO {
      assertEquals(status, Status.Ok)
      assertEquals(todos, List(TodoItem(1, "Learn Scala 3", true), TodoItem(2, "Learn Scala 3", false)))
    })
  }

  test("delete a todo") {
    val statusAndTodos = for {
      todo <- InMemoryTodo.make[IO]
      _ <- todo.add(TodoItem(1, "Learn Scala 3", false))
      _ <- todo.add(TodoItem(2, "Learn Scala 3", false))
      deleteTodo = Request[IO](Method.DELETE, uri"/todo/1")
      response <- Routes.todoRoute(todo).orNotFound(deleteTodo)
      todos <- todo.list
      status = response.status
    } yield (status, todos)
    statusAndTodos.flatMap((status, todos) => IO {
      assertEquals(status, Status.Ok)
      assertEquals(todos, List(TodoItem(2, "Learn Scala 3", false)))
    })
  }

  test("add a todo") {
    val statusAndTodos = for {
      todo <- InMemoryTodo.make[IO]
      addTodo = Request[IO](Method.POST, uri"/todo").withEntity(TodoItem(1, "Learn Scala 3", false))
      response <- Routes.todoRoute(todo).orNotFound(addTodo)
      todos <- todo.list
      status = response.status
    } yield (status, todos)
    statusAndTodos.flatMap((status, todos) => IO {
      assertEquals(status, Status.Ok)
      assertEquals(todos, List(TodoItem(1, "Learn Scala 3", false)))
    })
  }