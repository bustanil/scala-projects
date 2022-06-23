package com.rockthejvm

object FunctionalProgramming extends App {

  class Person(name: String) {
    def apply(age: Int) = println(s"I have aged $age years")
  }

  val bob = new Person("Bob")
  bob(42) // INVOKING bob as a function === bob.apply(42)

  /**
   * Scala runs on the JVM
   * Functional programming:
   * - compose functions
   * - pass functions as args
   * - return functions as results
   *
   * Conclusion: FunctionX = Function1, Function2, ..., Function22
   */

  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  simpleIncrementer.apply(23)
  simpleIncrementer(23) // simpleIncrementer.apply(23)
  // define a function!

  // ALL SCALA FUNCTIONS ARE INSTANCES OF THESE FUNCTION_X TYPES

  // function with 2 arguments and a String return type
  val stringContatenator = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  stringContatenator("I love", " Scala") // "I love Scala"

  // syntax sugars
  val doubler: Int => Int = (x: Int) => 2 * x
  doubler(4)  // 8

  /**
   * Equivalent to the much longer:
   * new Function1[Int, Int] {
   *   override def apply(x: Int) = 2 * x
   * }
   */

  // Higher order functions: take functions as arguments or returns functions as result
  val aMappedList: List[Int] = List(1, 2, 3).map(x => x + 1) // HOF
  println(aMappedList)

  val aFlatMappedList = List(1, 2, 3).flatMap(x => List(x, 2 * x))
  println(aFlatMappedList)

  // similar like above
  val aFlatMappedList2 = List(1, 2, 3).flatMap { x =>
    List(x, 2 * x)
  }

  val aFilteredList = List(1, 2, 3, 4, 5).filter(x => x <= 3)
  println(aFilteredList)

  // shorter syntax
  val aFilteredList2 = List(1, 2, 3, 4, 5).filter(_ <= 3) // equivalent to x => x <= 3

  // all pair between the numbers 1,2,3 and the letters a,b,c
  val allPairs = List(1, 2, 3).flatMap(number => List("a", "b", "c").map(letter => s"$number-$letter"))
  println(allPairs)

  // for comprehension
  val allPairs2 = for {
    number <- List(1, 2, 3)
    letter <- List("a", "b", "c")
  } yield s"$number-$letter"

  println(allPairs2)

  /**
   * Collections
   */

  // list
  val aList = List(1, 2, 3, 4, 5)
  val firstElement = aList.head
  val rest = aList.tail
  val aPrependedList = 0 :: aList
  val anExtendedList = 0 +: aList :+ 6 // List(0,1,2,3,4,5,6)

  // sequences
  val aSequence: Seq[Int] = Seq(1,2,3) // Seq.apply(1,2,3)
  val accessedElement = aSequence(1) // the element at index 1: 2

  // vector: fast Seq implementation
  val aVector = Vector(1,2,3,4,5)

  // sets
  val aSet = Set(1,2,3,4,1,2,3) // Set(1,2,3,4)
  val setHasFive = aSet.contains(5) // false
  val anAddedSet = aSet + 5 // Set(1,2,3,4,5) order may vary
  val aRemovedSet = aSet - 3  // Set(1,2,4)

  // ranges
  val aRange = 1 to 1000
  val twoByTwo = aRange.map(x => 2 * x).toList // List(2,4,6,...,2000)

  // tuples
  val aTuple = ("Bon Jovi", "Rock", 1982)

  // maps
  val aMap: Map[String, Int] = Map(
    ("Daniel", 2342343),
    ("Jane", 345345345),
    "Dave" -> 23453455
  )
}
