import cats.Functor
import cats.instances.all._
import cats.syntax.all._

val intA = Some(1)

Functor[Option].map(intA)(_ + 1)

// composing functors
val listOption = List(Some(1), None, Some(2))
val  listOptionFunctor = Functor[List].compose[Option]
val result: List[Option[Unit]] = listOptionFunctor.map(listOption)(_ => ())