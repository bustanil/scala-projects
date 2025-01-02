import cats.Applicative
import cats.syntax.all._

val opt1: Option[Int] = Some(1)
val opt2: Option[Int] = Some(2)

Applicative[Option].map2(opt1, opt2)(_ + _)

// using the syntax
(opt1, opt2).mapN[Int](_ + _)

// Apply
(opt1, opt2).tupled