package com.rockthejvm.part1basics

object Expressions {

  // expressions are structures that ca be evaluated to a value
  val meaningOfLife = 40 + 2

  // mathematical expressions: +, -, *, /, bitwise |, &, <<, >>, >>>
  val mathExpression = 2 + 3 * 4

  // comparison expressions: <, <=, >=, ==, !=
  val equalityTest = 1 == 2

  // boolean expressions: !, !!, &&
  val nonEqualityTest = !equalityTest

  // instructions vs expressions
  // expressions are evaluated, instructions are executed
  // we think in terms of expressions

  // ifs are expressions
  val aCondition = true
  val anIfExpression = if (aCondition) 45 else 99

  // code blocks
  val aCodeBlock = {
    // local values
    val localValue = 78
    // expressions...

    // last expression = value of the block
    localValue + 54
  }

  // everything is an expression is Scala

  /**
   * Exercise:
   * Without running the code, what do you think these values will print out?
   */
  // 1
  val someValue = {
    2 < 3
  }

  // 2
  val someOtherValue = {
    if (someValue) 239 else 986
    42
  }

  // 3
  val yetAnotherValue = println("Scala")
  val theUnit: Unit = ()

  def main(args: Array[String]): Unit = {
    println(meaningOfLife)
    println(anIfExpression)
    println(aCodeBlock)
  }

}
