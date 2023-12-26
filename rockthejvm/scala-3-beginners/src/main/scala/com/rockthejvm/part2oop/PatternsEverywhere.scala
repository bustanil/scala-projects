package com.rockthejvm.part2oop

object PatternsEverywhere {

  // catches are actually matchers
  val potentialFailure = try {
    // code
  } catch { // catch expression is actually pattern matching
    case e: RuntimeException => "runtime ex"
    case npe: NullPointerException => "npe"
    case _ => "some other exception"
  }
  
  // for comprehensions (generators) are based on PM
  val aList = List(1, 2, 3, 4)
  val evenNumbers = for {
    n <- aList if n % 2 == 0
  } yield 10 * n
  
  val tuples = List((1 -> 2), (3 -> 4))
  
  val filterTuples = for {
    (first, second) <- tuples if first < 3
  } yield second * 100
  
  // new syntax
  val aTuple = (1, 2, 3)
  val (a, b, c) = aTuple
  
  val head :: tail = tuples
  
  def main(args: Array[String]): Unit = {
    
  }

}
