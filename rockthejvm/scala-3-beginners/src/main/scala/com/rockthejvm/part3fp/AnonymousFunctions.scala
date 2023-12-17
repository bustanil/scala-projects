package com.rockthejvm.part3fp

object AnonymousFunctions {

  // instances of FunctionN
  val doubler: Int => Int = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }

  // lambdas = anonymous function instances
  val doubler_v2: Int => Int = (x: Int) => x * 2
  val adder: (Int, Int) => Int = (x: Int, y: Int) => x + y // new Function2[Int, Int, Int] ...
  // zero arg function
  val justDoSomething: () => Int = () => 45

  // alt syntax with curly braces
  val stringToInt = { (str: String) =>
    str.toInt
  }
  // alt syntax with type inference, useful when passing function to another function
  val stringToInt_v2: String => Int = { str =>
    str.toInt
  }

  // shortest lambda
  val doubler_v4 : Int => Int = _ * 2
  val adder_v3: (Int, Int) => Int = _ + _

  /**
   * 1. Replace all FunctionN instantiations with lambda in LList implementation.
   * @param args
   */


  def main(args: Array[String]): Unit = {

  }

}
