package com.bustanil.todoscala

import cats.syntax.all.*
import cats.effect.{Concurrent, MonadCancelThrow, Ref, Sync}
import io.circe.{Decoder, Encoder}
import org.http4s.EntityDecoder
import cats.effect.kernel.Resource
import doobie.hikari.HikariTransactor
import doobie._
import doobie.implicits._
import org.http4s.*
import org.http4s.circe.*

import java.util.UUID


trait Todo[F[_]]:
  def add(item: TodoItem): F[Unit]

  def update(item: TodoItem): F[Unit]

  def list: F[List[TodoItem]]

  def complete(id: UUID, completed: Boolean): F[Unit]

  def delete(id: UUID): F[Unit]

case class TodoItem(id: UUID, description: String, completed: Boolean)

object TodoItem:
  given Encoder[TodoItem] = Encoder.AsObject.derived[TodoItem]

  given Decoder[TodoItem] = Decoder.derived[TodoItem]

  given [F[_] : Concurrent]: EntityEncoder[F, TodoItem] = jsonEncoderOf

  given [F[_] : Concurrent]: EntityDecoder[F, TodoItem] = jsonOf

case class CreateTodoRequest(todo: TodoItem)

object CreateTodoRequest:
  given Encoder[CreateTodoRequest] = Encoder.AsObject.derived[CreateTodoRequest]

  given Decoder[CreateTodoRequest] = Decoder.derived[CreateTodoRequest]

  given [F[_] : Concurrent]: EntityDecoder[F, CreateTodoRequest] = jsonOf

  given [F[_] : Concurrent]: EntityEncoder[F, CreateTodoRequest] = jsonEncoderOf

case class CompleteTodoRequest(completed: Boolean)

object CompleteTodoRequest:
  given Encoder[CompleteTodoRequest] = Encoder.AsObject.derived[CompleteTodoRequest]

  given Decoder[CompleteTodoRequest] = Decoder.derived[CompleteTodoRequest]

  given [F[_] : Concurrent]: EntityDecoder[F, CompleteTodoRequest] = jsonOf

  given [F[_] : Concurrent]: EntityEncoder[F, CompleteTodoRequest] = jsonEncoderOf

case class GetTodoResponse(todos: List[TodoItem])

object GetTodoResponse:
  given Encoder[GetTodoResponse] = Encoder.AsObject.derived[GetTodoResponse]
  given Decoder[GetTodoResponse] = Decoder.derived[GetTodoResponse]
  given [F[_] : Concurrent]: EntityDecoder[F, GetTodoResponse] = jsonOf
  given [F[_] : Concurrent]: EntityEncoder[F, GetTodoResponse] = jsonEncoderOf

class InMemoryTodo[F[_]] private(ref: Ref[F, List[TodoItem]]) extends Todo[F]:
  def add(item: TodoItem): F[Unit] = ref.update(_ :+ item)

  def update(item: TodoItem): F[Unit] =
    ref.update(todos =>
      todos.map(i =>
        if (i.description == item.description) item else i))

  def list: F[List[TodoItem]] = ref.get

  def complete(id: UUID, completed: Boolean): F[Unit] =
    ref.update(todos =>
      todos.map(i =>
        if (i.id === id) i.copy(completed = completed) else i))

  def delete(id: UUID): F[Unit] = ref.update(_.filterNot(_.id === id))

object InMemoryTodo:
  def make[F[_] : Sync]: F[Todo[F]] =
    Ref.of[F, List[TodoItem]](List.empty).map(new InMemoryTodo(_))

object LiveTodo:
  // support UUID type in doobie
  given Meta[UUID] = Meta[String].imap[UUID](UUID.fromString)(_.toString)

  def make[F[_] : MonadCancelThrow : GenUUID](transactor: Resource[F, HikariTransactor[F]]): Todo[F] =
    new Todo[F]:
      def add(item: TodoItem): F[Unit] = transactor.use { xa =>
        for {
          uuid <- GenUUID[F].uuid
          createTodo = sql"INSERT INTO todo (id, description, completed) VALUES ($uuid, ${item.description}, ${item.completed})".update.run
          _ <- createTodo.transact(xa)
        } yield ()
      }

      def update(item: TodoItem): F[Unit] = transactor.use { xa =>
        val updateTodo = sql"UPDATE todo SET description = ${item.description}, completed = ${item.completed} WHERE id = ${item.id}".update.run
        for {
          _ <- updateTodo.transact(xa)
        } yield ()
      }

      def list: F[List[TodoItem]] = transactor.use { xa =>
        val selectAll = sql"SELECT id, description, completed FROM todo".query[TodoItem]
        for {
          todos <- selectAll.to[List].transact(xa)
        } yield todos
      }


      def complete(id: UUID, completed: Boolean): F[Unit] = transactor.use { xa =>
        val updateTodo = sql"UPDATE todo SET completed = $completed WHERE id = $id".update.run
        for {
          _ <- updateTodo.transact(xa)
        } yield ()
      }

      def delete(id: UUID): F[Unit] = transactor.use { xa =>
        val deleteTodo = sql"DELETE FROM todo WHERE id = $id".update.run
        for {
          _ <- deleteTodo.transact(xa)
        } yield ()
      }