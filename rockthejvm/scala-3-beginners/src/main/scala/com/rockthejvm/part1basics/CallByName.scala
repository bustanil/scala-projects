package com.rockthejvm.part1basics

object CallByName {

  // call by value => arguments are evaluated before function invocation
  def aFunction(arg: Int): Int = arg + 1
  val aComputation = aFunction(23 + 67)

  // call by name => arguments are passed LITERALLY, evaluated a every reference
  def aByNameFunction(arg: => Int): Int = arg + 1
  val anotherComputation = aByNameFunction(23 + 67)

  def printTwiceByValue(x: Long): Unit = {
    println(s"By value: $x")
    println(s"By value: $x")
  }

  /**
   * delayed evaluation of the argument
   * argument is evaluated every time it is used
   * if the argument is not used, the argument is not called at all
   */
  def printTwiceByName(x: Long): Unit = {
    println(s"By name: $x")
    println(s"By name: $x")
  }

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  def main(args: Array[String]): Unit = {
//    println(aComputation);
//    println(anotherComputation);
    printTwiceByValue(System.nanoTime())
    printTwiceByName(System.nanoTime())
    printFirst(42, infinite())
  }

}
