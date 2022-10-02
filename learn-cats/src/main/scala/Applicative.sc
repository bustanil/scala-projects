import cats._
import cats.implicits._

sealed trait Validated[+A]

object Validated {
  case class Valid[+A](a: A) extends Validated[A]
  case class Invalid(error: List[String]) extends Validated[Nothing]

  implicit val applicative: Applicative[Validated] = new Applicative[Validated] {
    override def pure[A](x: A) = Valid(x)

    override def ap[A, B](ff: Validated[A => B])(fa: Validated[A]): Validated[B] = {
      (ff, fa) match {
        case (Valid(f), Valid(a)) => Valid(f(a))
        case (Invalid(e), Valid(_)) => Invalid(e)
        case (Valid(_), Invalid(e)) => Invalid(e)
        case (Invalid(e1), Invalid(e2)) => Invalid(e1 ++ e2)
      }
    }

    override def map2[A, B, Z](va: Validated[A], vb: Validated[B])(f: (A, B) => Z): Validated[Z] = {
      val g = f.curried
      ap(ap(pure(g))(va))(vb)
    }

    def tupled[A, B](va: Validated[A], vb: Validated[B]): Validated[(A, B)] = {
      map2(va, vb)((a, b) => (a, b))
    }
  }
}

val v1: Validated[Int] = Applicative[Validated].pure(1)
val v2: Validated[Int] = Applicative[Validated].pure(2)
val v3: Validated[Int] = Applicative[Validated].pure(3)

(v1, v2, v3).mapN((a, b, c) => a + b + c)