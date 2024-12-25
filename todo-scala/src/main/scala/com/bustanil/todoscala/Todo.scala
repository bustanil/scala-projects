package com.bustanil.todoscala

import cats.syntax.all.*
import cats.effect.Ref
import cats.effect.Sync
import io.circe.{Encoder, Decoder}
import org.http4s.EntityDecoder
import cats.effect.Concurrent
import org.http4s.*
import org.http4s.circe.*


trait Todo[F[_]]:
  def add(item: TodoItem): F[Unit]

  def update(item: TodoItem): F[Unit]

  def list: F[List[TodoItem]]

  def complete(id: Int): F[Unit]

  def delete(id: Int): F[Unit]

case class TodoItem(id: Int, description: String, completed: Boolean)

object TodoItem:
  given Encoder[TodoItem] = Encoder.AsObject.derived[TodoItem]

  given Decoder[TodoItem] = Decoder.derived[TodoItem]

  given [F[_] : Concurrent]: EntityEncoder[F, TodoItem] = jsonEncoderOf

  given [F[_] : Concurrent]: EntityDecoder[F, TodoItem] = jsonOf


case class TodoResponse(todos: List[TodoItem])

object TodoResponse:
  given Encoder[TodoResponse] = Encoder.AsObject.derived[TodoResponse]

  given Decoder[TodoResponse] = Decoder.derived[TodoResponse]

  given [F[_] : Concurrent]: EntityDecoder[F, TodoResponse] = jsonOf

  given [F[_] : Concurrent]: EntityEncoder[F, TodoResponse] = jsonEncoderOf

class InMemoryTodo[F[_]] private(ref: Ref[F, List[TodoItem]]) extends Todo[F]:
  def add(item: TodoItem): F[Unit] = ref.update(_ :+ item)

  def update(item: TodoItem): F[Unit] =
    ref.update(todos =>
      todos.map(i =>
        if (i.description == item.description) item else i))

  def list: F[List[TodoItem]] = ref.get

  def complete(id: Int): F[Unit] =
    ref.update(todos =>
      todos.map(i =>
        if (i.id == id) i.copy(completed = true) else i))

  def delete(id: Int): F[Unit] = ref.update(_.filterNot(_.id == id))

object InMemoryTodo:
  def make[F[_] : Sync]: F[Todo[F]] =
    Ref.of[F, List[TodoItem]](List.empty).map(new InMemoryTodo(_))