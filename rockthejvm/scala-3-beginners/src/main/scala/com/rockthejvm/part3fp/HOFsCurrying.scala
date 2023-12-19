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

  /**
   * 1. LList exercises
   *    - foreach(A => Unit): Unit
   *    - sort((A, A) => Int): LList[A]
   *    - zipWith[B](LList[A], (A, A) => B): LList[B]
   *    - foldLeft[B](start: B)((A, B) => B): B
   * 2. toCurry(f: (Int, Int) => Int: Int => Int => Int
   *    fromCurry(f: (Int => Int => Int): (Int, Int) => Int
   *    compose(f, g) => x => f(g(x))
   *    andThen(f, g) => x => g(f(x))
   */

  // 2
  def toCurry[A, B, C](f: (A, B) => C): A => B => C = {
    a => b => f(a, b)
  }

  def fromCurry[A, B, C](f: (A => B => C)): (A, B) => C = {
    (a, b) => f(a)(b)
  }

  def compose[A, B, C](f: B => C, g: A => B): A => C = x => f(g(x))
  def andThen[A, B, C](f: A => B, g: B => C): A => C = x => g(f(x))


  def main(args: Array[String]): Unit = {
    println(nTimes(x => x + 1, 2, 2))
    println(nTimes_v2(x => x + 1, 2)(1000000))
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))

    val add: (Int, Int) => Int = _ + _
    val multiply: (Int, Int) => Int = _ * _
    val curriedAdd = toCurry(add)
    val curriedMultiply = toCurry(multiply)
    val uncurriedAdd = fromCurry(curriedAdd)

    println(curriedAdd(1)(2))
    println(uncurriedAdd(1, 2))

    println(compose(curriedAdd(1), curriedMultiply(3))(3))
    println(andThen(curriedAdd(1), curriedMultiply(3))(3))
  }

}
