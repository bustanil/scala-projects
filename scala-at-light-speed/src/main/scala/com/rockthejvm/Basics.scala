package com.rockthejvm

object Basics extends App {
  // defining a value
  val meaningOfLife: Int = 42

  // type inference
  val aBoolean = false // type is optional

  // regular types: Int, Boolean, Char, Double, Float, String

  // String and String operations
  val aString = "I love scala"
  val aComposedString = "I" + " " + "love" + " " + "Scala"
  val anInterpolatedString = s"The meaning of life is $meaningOfLife"

  // expressions = structures that can be reduced to a value
  val anExpression = 2 + 3

  // if-expression
  val ifExpression = if (meaningOfLife > 43) then 56 else 999 // in other languages: meaningOfLife > 43 ? 56 : 999
  val chainedIfExpression =
    if (meaningOfLife > 43) 56
    else if (meaningOfLife < 0) 2
    else if (meaningOfLife > 999) 78
    else 0

  // code blocks
  val aCodeBlock = {
    // definitions
    val aLocalValue = 67

    // value of block is the value of the last expression
    aLocalValue + 3
  }

  // define a function
  def myFunction(x: Int, y: String): String = {
    y + " " + x
  }

  // recursive functions
  def factorial(n: Int): Int =
    if (n <= 1) 1
    else n * factorial(n - 1)

  // In Scala we don't use loops or iteration, we use RECURSION!

  // the Unit return type = no meaningful value === "void" in other languages
  // type of SIDE EFFECTS
  println("I love Scala") // System.out.println, printf, print, console.log

  def myUnitReturningFunction(): Unit = {
    println("I don't love returning Unit")
  }

  val theUnit = ()
}
