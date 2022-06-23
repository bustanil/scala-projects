package com.rockthejvm

import java.time.LocalDate

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Try, Failure, Success}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

object Main {

  import slick.jdbc.PostgresProfile.api._
  import PrivateExecutionContext._

  val shawshankRedemption = Movie(1, "The Shawshank Redemption", LocalDate.of(1994, 9, 23), 162)
  val theMatrix = Movie(2, "The Matrix", LocalDate.of(1999, 3, 31), 134)

  def demoInsertMovie(): Unit = {
    val queryDescription = SlickTables.movieTable += theMatrix
    val futureId: Future[Int] = Connection.db.run(queryDescription)

    futureId.onComplete {
      case Success(newMovieId) => println(s"Query was successful, new id is $newMovieId")
      case Failure(e) => println(s"Query failed, reason: $e")
    }

    Thread.sleep(3000)
  }

  def demoReadAllMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(SlickTables.movieTable.result) // "select * from ..."
    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    Thread.sleep(3000)
  }

  def demoReadSomeMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(SlickTables.movieTable.filter(_.name.like("%Matrix")).result) // "select * from ... where name like 'Matrix'"
    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(ex) => println(s"Fetching failed: $ex")
    }
    Thread.sleep(3000)
  }

  def demoUpdate(): Unit = {
    val queryDescriptor = SlickTables.movieTable.filter(_.id === 1L).update(shawshankRedemption.copy(lengthInMin = 150))
    val futureId: Future[Int] = Connection.db.run(queryDescriptor)
    futureId.onComplete {
      case Success(newMovieId) => println(s"Query was successful, new id is $newMovieId")
      case Failure(e) => println(s"Query failed, reason: $e")
    }
    Thread.sleep(3000)
  }

  def demoDelete(): Unit = {
    val queryDescriptor = SlickTables.movieTable.filter(_.id === 1L).delete
    Connection.db.run(queryDescriptor)
    Thread.sleep(3000)
  }

  def main(args: Array[String]): Unit = {
//    demoInsertMovie()
//    demoReadAllMovies()
//    demoReadSomeMovies()
//    demoUpdate()
    demoDelete()
  }

}
