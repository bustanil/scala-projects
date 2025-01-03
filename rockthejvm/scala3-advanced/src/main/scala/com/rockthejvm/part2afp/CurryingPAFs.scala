package com.rockthejvm.part2afp

object CurryingPAFs {

  // currying the ability to pass an argument at a time
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3: Int => Int = superAdder(3) // y = 3 + y
  val eight = add3(5)
  val eight_v2 = superAdder(3)(5)

  // curried methods
  def curriedAdder(x: Int)(y: Int): Int =
    x + y

  // methods != function values
  // converting methods to functions eta-expansion
  val add4 = curriedAdder(4)
  val nine = add4(5) // 9

  def increment(x: Int): Int = x + 1
  val aList = List(1, 2, 3)
  val anIncrementedList = aList.map(increment) // eta-expansion

  // underscores are powerful
  def concatenate(a: String, b: String, c: String): String = a + b + c
  val insertName = concatenate("Hello, my name is ", _: String, ", I'm going to show you nice Scala trick")

  val danielsGreeting = insertName("Bustanil")

  /**
   * Exercises
   */
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedMethod(x: Int)(y: Int) = x + y

  // 1 - obtain an add7 function: x => x + 7 out of these 3 definitions
  val add7 = simpleAddMethod(_: Int, 7)
  val add7_v2 = simpleAddMethod(_: Int, 7)
  val add7_v3 = curriedMethod(7)
  val add7_v4 = (x: Int) => simpleAddMethod(x , 7)
  val add7_v5 = (x: Int) => simpleAddFunction(x , 7)
  val add7_v6 = simpleAddFunction.curried(7)

  // 2 - process a list of numbers and return their string representations under different formats
  // step 1: create a curried formatting method with a formatting string and a value
  // step 2: process a list of numbers with various formats
  val piWith2Dec = "%4.2f".format(Math.PI)
  def curriedFormatter(format: String)(number: Float) = format.format(number)

  val anotherList = List(1.2f, 2, 3, 4)
  val aFormatter = curriedFormatter("%4.2f")

  val aFormattedList = anotherList.map(aFormatter)


  // method vs functions + by-name vs 0-lambda
  def byName(n: => Int) = n + 1
  def byLambda(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  byName(42) // OK
  byName(method) // 43 eta-expanded? NO - method is INVOKED here
//  byName(parenMethod) // NOT OK
  byName((() => 42)()) // OK
//  byName(() => 42) // NOT OK

//  byLambda(23) // NOT OK
//  byLambda(method) // eta-expansion is NOT possible
  byLambda(parenMethod) // eta-expansion is done
  byLambda(() => 42)
  byLambda(() => parenMethod()) // OK

  def main(args: Array[String]): Unit = {
    println(danielsGreeting)
    println(piWith2Dec)
    println(aFormattedList)
  }

}
