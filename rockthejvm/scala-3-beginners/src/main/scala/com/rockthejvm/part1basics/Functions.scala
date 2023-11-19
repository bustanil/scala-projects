package com.rockthejvm.part1basics

import scala.annotation.tailrec

object Functions {
  
  def aFunction(a: String, b: Int): String = {
    return a + " " + b
  }

  val aFunctionInvocation = aFunction("Scala", 9999999)

  def aNoArgumentFunction(): Int = 45

  def aParameterLessFunction: Int = 45

  def stringConcatenation(str: String, n: Int): String =
    if (n == 0) ""
    else if (n == 1) str
    else str + stringConcatenation(str, n - 1)

  val scalax3 = stringConcatenation("Scala", 3)
  // when you need loops, use RECURSION.

  // "void" function
  def aVoidFunction(aString: String): Unit =
    println(aString)

  def computeDoubleStringWithSideEffect(aString: String) = {
    aVoidFunction(aString) // Unit
    aString + aString // meaningful value
  } // discouraging side effects

  def aBigFunction(n: Int) = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n + 1)
  }

  /**
   * Exercises
   * 1. A greeting function (name, age) => "Hi my name is $name and I am $age years old"
   * 2. Factorial function n => 1 * 2 * 3 * 4 * ... * n
   * 3. Fibonacci function
   *    fib(1) = 1
   *    fib(2) = 1
   *    fib(3) = 1 + 1
   *    fib(n) = fib(n-1) + fib(n-2)
   * 4. Tests if a number is prime
   */

  // 1
  def greeting(name: String, age: Int): String = s"Hi my name is $name and I am $age years old"

  // 2
  def factorial(n: Int): Int = {
    if (n == 1) 1
    else n * factorial(n - 1)
  }

  // 3
  def fibonacci(n: Int): Int = {
    if (n == 0) 0
    else if (n == 1) 1
    else fibonacci(n - 1) + fibonacci(n - 2)
  }

  // 4
  def isPrime(n: Int): Boolean = {
    // 2, 3, 5, 7
    @tailrec
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def main(args: Array[String]): Unit = {
    println(scalax3)

    println(greeting("Bustanil", 40))
    println(factorial(1))

    println(fibonacci(2))

    println(isPrime(4))
  }
  
}
