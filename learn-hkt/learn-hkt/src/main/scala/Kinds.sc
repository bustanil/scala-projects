object Kinds {
  // kind => type of types

  val aNumber: Int = 42 // level-0 types
  case class Person(name: String, age: Int)
  val bob: Person = Person("Bob", 35)

  // generic/level-1 types
  class LinkedList[T] { // takes type argument in the level-0 type
    // code
  }

  val aList: LinkedList[Int] = ???
  //         ^^^^^^^^^^^^^^^ level-0 type

  // level-2 types
  class Functor[F[_]]
  val functorList = new Functor[List]
  //                    ^^^^^^^^^^^^^ level-0 type
  // Functor is also a type constructor: [F[_]] = Functor[F]

  // examples
  class HashMap[K, V]  // level-1
  val anAddressBook = new HashMap[String, String]

  class ComposedFunctor[F[_], G[_]] // level-2
  val aComposedFunctor = new ComposedFunctor[List, Option]

  def main(args: Array[String]): Unit = {

  }
}