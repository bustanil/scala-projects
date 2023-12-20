package com.rockthejvm.part3fp

import com.rockthejvm.practice.{Cons, Empty}

object MapFlatmapFilterFor {

  // standard list
  val aList = List(1, 2, 3)

  val firstElement = aList.head
  val rest = aList.tail

  // map
  val anIncrementedList = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  val flatmapped = aList.flatMap(toPair)

  // all the possible combinations of all the elements of those lists
  val numbers = List(1, 2, 3, 4)
  val chars = List("a", "b", "c", "d")
  val colors = List("black", "white", "red")

  val combinations = numbers.filter(_ % 2 == 0).flatMap(number => chars.flatMap(char => colors.map(color => s"$number$char - $color")))

  // for-comprehension = IDENTICAL to flatMap + map chains
  val combinations_v2 = for {
    number <- numbers if number % 2 == 0// generator
    char <- chars
    color <- colors
  } yield s"$number$char - $color"

  // for-comprehensions with Unit
  // if foreach

  /**
   * Exercises
   * 1. LList supports for comprehensions
   * 2. A small collection of AT MOST ONE element - Maybe[A]
   *    - map
   *    - flatMap
   *    - filter
   */


  def main(args: Array[String]): Unit = {
//    numbers.foreach(println)
//    for {
//      num <- numbers
//    } println(num)

    val someList = Cons(1, Cons(2, Cons(3, Cons(4, Empty[Int]()))))
    val anotherList = Cons("a", Cons("b", Cons("c", Cons("d", Empty[String]()))))

    val sumList = for {
      a <- someList if a < 3
      b <- anotherList
    } yield a + b

    sumList.foreach(println)

//    println(combinations)
//    println(combinations_v2)
  }

}
