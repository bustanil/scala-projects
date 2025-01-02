import cats.Semigroup
import cats.syntax.all._

given m: Semigroup[Int] = _ * _

val x = 2
val y = 3

val result = x |+| y

