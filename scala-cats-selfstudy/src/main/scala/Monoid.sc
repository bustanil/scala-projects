import cats.Monoid
import cats.data.NonEmptyList

def combineAll[A: Monoid](as: List[A]): A =
  as.foldLeft(Monoid[A].empty)(Monoid[A].combine)

combineAll(List(1, 2, 3, 4, 5))
combineAll(List("a", "b", "c", "d", "e"))

// OptionMonoid for Semigroups that are not Monoid
import cats.syntax.all._

val myList: List[NonEmptyList[Int]] = List(NonEmptyList[Int](1, List(2, 3, 4, 5)), NonEmptyList(100, List(200, 300, 400, 500)))

val lifted: List[Option[NonEmptyList[Int]]] = myList.map(nel => Option(nel))

val combined: Option[NonEmptyList[Int]] = Monoid.combineAll(lifted)