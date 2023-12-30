package com.rockthejvm.part1as

object PartialFunctions {

  val aFunction: Int => Int = x => x + 1
  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 54
    else if (x == 5) 5423
    else throw new RuntimeException("something went wrong")

  val aFussyFunction_v2 = (x: Int) => x match {
    case 1 => 42
    case 2 => 54
    case 3 => 999
  } // a partial function only PARTIALLY APPLICABLE to a subset of input

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 54
    case 3 => 999
  }

  val canCallOn37 = aPartialFunction.isDefinedAt(37) // false
  val liftedPF = aPartialFunction.lift // partial function to total function Int => Option[Int]

  val anotherPF: PartialFunction[Int, Int] = {
    case 45 => 79
  }
  val pfChain = aPartialFunction.orElse(anotherPF)

  // HOFs accept partial functions as arguments
  val aList = List(1, 2, 3, 4, 5)
  val anIncrementedList = aList.map(pfChain)

  case class Person(name: String, age: Int)

  val someKids = List(
    Person("Alice", 3),
    Person("Bob", 5),
    Person("Jane", 4),
  )

  val kidsGrowingUp = someKids.map { // use partial function to deconstruct/destructure
    case Person(name, age) => Person(name, age + 1)
  }

  def main(args: Array[String]): Unit = {
    println(aPartialFunction(2))
//    println(aPartialFunction(10))  throws MatchError
    println(anIncrementedList)
  }

}
