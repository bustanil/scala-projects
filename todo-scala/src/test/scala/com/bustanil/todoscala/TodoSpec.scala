package com.bustanil.todoscala

import cats.effect.IO
import org.http4s.*
import org.http4s.implicits.*
import munit.CatsEffectSuite
import tech.ant8e.uuid4cats.UUIDGenerator
import java.util.UUID

class TodoSpec extends CatsEffectSuite:

  extension (sc: StringContext)
    def uuid(args: Any*): UUID = UUID.fromString(sc.s(args: _*))

  given generator: UUIDGenerator[IO] =  new UUIDGenerator[IO] {
    def uuid: IO[UUID] = IO(uuid"00000000-0000-0000-0000-000000000001")
  }

  test("get todo returns 200") {
    val statusIO = for {
      todo <- InMemoryTodo.make[IO]
      getTodo = Request[IO](Method.GET, uri"/todo")
      response <- Routes.todoRoute(todo)(generator).orNotFound(getTodo)
      status = response.status
    } yield status
    assertIO(statusIO, Status.Ok)
  }

  test("returns empty todos") {
    val bodyIO = for {
      todo <- InMemoryTodo.make[IO]
      getTodo = Request[IO](Method.GET, uri"/todo")
      response <- Routes.todoRoute(todo)(generator).orNotFound(getTodo)
      responseBody <- response.as[String]
    } yield responseBody
    assertIO(bodyIO, "{\"todos\":[]}")
  }

  test("return some todos") {
    val bodyIO = for {
      todo <- InMemoryTodo.make[IO]
      _ <- todo.add(TodoItem(uuid"00000000-0000-0000-0000-000000000001", "Learn Scala 3", false))
      _ <- todo.add(TodoItem(uuid"00000000-0000-0000-0000-000000000002", "Learn Scala 3", false))
      getTodo = Request[IO](Method.GET, uri"/todo")
      response <- Routes.todoRoute(todo)(generator).orNotFound(getTodo)
      responseBody <- response.as[String]
    } yield responseBody
    assertIO(bodyIO, "{\"todos\":[{\"id\":\"00000000-0000-0000-0000-000000000001\",\"description\":\"Learn Scala 3\",\"completed\":false},{\"id\":\"00000000-0000-0000-0000-000000000002\",\"description\":\"Learn Scala 3\",\"completed\":false}]}")
  }

  test("complete a todo") {
    val statusAndTodos = for {
      todo <- InMemoryTodo.make[IO]
      _ <- todo.add(TodoItem(uuid"00000000-0000-0000-0000-000000000001", "Learn Scala 3", false))
      _ <- todo.add(TodoItem(uuid"00000000-0000-0000-0000-000000000002", "Learn Scala 3", false))
      completeTodo = Request[IO](Method.POST, uri"/todo/00000000-0000-0000-0000-000000000001/complete")
      response <- Routes.todoRoute(todo)(generator).orNotFound(completeTodo)
      todos <- todo.list
      status = response.status
    } yield (status, todos)

    statusAndTodos.flatMap((status, todos) => IO {
      assertEquals(status, Status.Ok)
      assertEquals(todos, List(
        TodoItem(uuid"00000000-0000-0000-0000-000000000001", "Learn Scala 3", true),
        TodoItem(uuid"00000000-0000-0000-0000-000000000002", "Learn Scala 3", false)))
    })
  }

  test("delete a todo") {
    val statusAndTodos = for {
      todo <- InMemoryTodo.make[IO]
      _ <- todo.add(TodoItem(uuid"00000000-0000-0000-0000-000000000001", "Learn Scala 3", false))
      _ <- todo.add(TodoItem(uuid"00000000-0000-0000-0000-000000000002", "Learn Scala 3", false))
      deleteTodo = Request[IO](Method.DELETE, uri"/todo/00000000-0000-0000-0000-000000000001")
      response <- Routes.todoRoute(todo)(generator).orNotFound(deleteTodo)
      todos <- todo.list
      status = response.status
    } yield (status, todos)
    statusAndTodos.flatMap((status, todos) => IO {
      assertEquals(status, Status.Ok)
      assertEquals(todos, List(TodoItem(uuid"00000000-0000-0000-0000-000000000002", "Learn Scala 3", false)))
    })
  }

  test("add a todo") {
    val statusAndTodos = for {
      todo <- InMemoryTodo.make[IO]
      addTodo = Request[IO](Method.POST, uri"/todo").withEntity(TodoItem(uuid"00000000-0000-0000-0000-000000000001", "Learn Scala 3", false))
      response <- Routes.todoRoute(todo)(generator).orNotFound(addTodo)
      todos <- todo.list
      status = response.status
    } yield (status, todos)
    statusAndTodos.flatMap((status, todos) => IO {
      assertEquals(status, Status.Ok)
      assertEquals(todos, List(TodoItem(uuid"00000000-0000-0000-0000-000000000001", "Learn Scala 3", false)))
    })
  }