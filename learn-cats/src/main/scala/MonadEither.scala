import cats._
import cats.implicits._


object MonadEither {
  implicit def eitherMonad[E]: Monad[Either[E, *]] =  new Monad[Either[E, *]] {
    override def pure[A](x: A): Either[E, A] = Right(x)

    override def flatMap[A, B](fa: Either[E, A])(f: A => Either[E, B]): Either[E, B] = {
      fa match {
        case Left(e) => Left(e)
        case Right(value) => f(value)
      }
    }

    override def tailRecM[A, B](a: A)(f: A => Either[E, Either[A, B]]): Either[E, B] = ???
  }

  def main(args: Array[String]): Unit = {
    val x = 5.asRight[String].flatMap(i => (i + 1).asRight[String])
    val y = 5.asRight[String].flatMap(i => "boom".asLeft[Int]).flatMap(i => "boom again".asLeft[Int])

    println(x)
    println(y)
  }

}

