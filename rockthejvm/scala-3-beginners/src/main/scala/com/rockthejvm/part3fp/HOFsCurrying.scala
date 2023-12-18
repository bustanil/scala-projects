package com.rockthejvm.part3fp

object HOFsCurrying {

  // higher order functions
  val aHof: (Int, (Int => Int)) => Int = (x, f) => x + 1
  val anotherHof: Int => (Int => Int) = x => (y => y + 2 * x)

  // quick exercise
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = (a, func) => (f => f + 1)

  // map, flatMap, filter are HOFs

  // more examples
  // f(f(f(f(...(f(x))))
  def nTimes(f: (Int => Int), n: Int, x: Int): Int =
    if (n == 0) x
    else nTimes(f, n - 1, f(x))

  def nTimes_v2(f: (Int => Int), n: Int): (Int => Int) =
    if (n == 0) (x: Int) => x
    else (x: Int) => nTimes_v2(f, n - 1)(f(x))

  // currying
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val add3: Int => Int = superAdder(3)
  val invokeSuperAdder = superAdder(3)(100)

  // curring methods = methods with multiple arg list
  def curriedFormatter(fmt: String)(x: Double): String = fmt.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f") // (x: Double) => "%4.2f".format(x)
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f") // (x: Double) => "%4.2f".format(x)

  def main(args: Array[String]): Unit = {
    println(nTimes(x => x + 1, 2, 2))
    println(nTimes_v2(x => x + 1, 2)(1000000))
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))
  }

}
