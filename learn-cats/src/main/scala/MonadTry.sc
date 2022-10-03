import cats._
import cats.implicits._
import scala.util._

implicit val tryMonad: Monad[Try] = new Monad[Try] {
  override def pure[A](x: A) = Success(x)

  override def flatMap[A, B](fa: Try[A])(f: A => Try[B]) = {
    fa match {
      case Success(value) => f(value)
      case Failure(exception) => Failure(exception)
    }
  }

  override def tailRecM[A, B](a: A)(f: A => Try[Either[A, B]]) = ???
}

tryMonad.pure(5)
tryMonad.pure(5).flatMap(i => tryMonad.pure(i + 1))
tryMonad.pure(5).flatMap(i => Failure(new Exception("Boom")))
tryMonad.pure(5)
  .flatMap((i: Int) => Failure(new Exception("boom")))
  .flatMap((j: Int) => Failure(new Exception("Boom 2")))


// law: pure(x).flatMap(f) === f(x)
val f: Int => Try[Int] = i => Success(i + 1)
Success(10).flatMap(f)
f(10)

// inconsistency
val g: Int => Try[Int] = i => throw new Exception("boom")
Success(10).flatMap(g)
g(10)