package com.rockthejvm

object ContextualAbstraction {

  // 1. context parameters/arguments
  val aList = List(2,1,3,4)
  val anOrderedList = aList.sorted // aList.sorted(descendingOrdering) MAGICAL!!! (contextual argument)

  // Ordering
  given descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _) // (a,b) => a > b

  // analogous to an implicit val (will be deprecated in Scala 3)

  trait Combinator[A] { // monoid
    def combine(x: A, y: A): A
  }


  def combineAll[A](list: List[A])(using combinator: Combinator[A]): A =
    list.reduce(combinator.combine)

  given intCombinator: Combinator[Int] = new Combinator[Int] {
    override def combine(x: Int, y: Int): Int = x + y
  }
  val theSum = combineAll(aList)

  /*
   * Given scope
     - local scope
     - imported scope
     - the companions of all the types involved in the call
       - companion of List
       - companion of Int
   */

  // combineAll(List(1,2,3,4))

  // context bounds
  def combineAll_v2[A](list: List[A])(using Combinator[A]): A = ???
  def combineAll_v3[A: Combinator](list: List[A]): A = ???

  /*
  where context args are useful
  - type classes
  - depedency injection
  - context-dependent functionality
  - type-level programming
  */

  // extension methods
  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name, I love Scala!"
  }

  extension (string: String)
    def greet(): String = new Person(string).greet()

  val danielsGreeting = "Daniel".greet() // type enrichment

  // POWER!!!
  extension [A] (list: List[A])
    def combineAllValues(using combinator: Combinator[A]): A =
      list.reduce(combinator.combine)

  val theSum_v2 = aList.combineAllValues

  def main(args: Array[String]): Unit = {
    println(anOrderedList)
    println(theSum)
    println(theSum_v2)
  }

}
