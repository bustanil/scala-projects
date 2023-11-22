package com.rockthejvm.part1basics

import scala.annotation.tailrec

object Recursion {

  // "repetition" = recursion
  def sumUntil(n: Int): Int =
    if (n < 0) 0
    else n + sumUntil(n - 1) // stack recursive

  def sumUntil_v2(n: Int): Int = {
    @tailrec
    def sumUntilTailRec(x: Int, acc: Int): Int =
      if (x <= 0) acc
      else sumUntilTailRec(x - 1, acc + x)

    sumUntilTailRec(n, 0) // TAIL recursion = recursion call occurs (expression evaluated) LAST in its code path
  }

  def sumNumbersBetween(a: Int, b: Int): Int =
    @tailrec
    def sumNumbersBetweenTailRec(i: Int, acc: Int): Int =
      if (i > b) acc
      else sumNumbersBetweenTailRec(i + 1, acc + i)

    sumNumbersBetweenTailRec(a, 0)

  /**
   * Exercises
   * 1. Concatenate a string n times
   * 2. Fibonacci function, tail recursive
   * 3. Is isPrime function tail recursive
   */

  // 1
  def concatenate(s: String, n: Int): String =
    @tailrec
    def concatenateTailRec(i: Int, acc: String): String =
      if (i == n) acc
      else concatenateTailRec(i + 1, acc + s)

    concatenateTailRec(0, "")

  // 2
  // 0 + 1 + 1 + 2 + 3 + 5 + 8 + ... + (n - 2) + (n - 1)
  def fibonacci_v2(n: Int): Int =
    @tailrec
    def fibonacciTailRec(i: Int, acc1: Int, acc2: Int): Int =
      if (i == 0) 0
      else if (i == n) acc2
      else fibonacciTailRec(i + 1, acc2, acc1 + acc2)

    fibonacciTailRec(1, 0, 1)

  // 3, yes
  def isPrime(n: Int): Boolean = {
    // 2, 3, 5, 7
    @tailrec
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def isPrime_v2(n: Int): Boolean = {
    // 2, 3, 5, 7
    @tailrec
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else if (n % t == 0) false
      else isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def main(args: Array[String]): Unit = {
    println(sumUntil_v2(10))
    println(sumNumbersBetween(1, 10))
    println(concatenate("test", 3))
    println(fibonacci_v2(2))

    println(isPrime_v2(3))
  }

}
