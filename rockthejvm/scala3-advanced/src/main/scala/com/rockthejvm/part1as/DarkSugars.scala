package com.rockthejvm.part1as

import scala.annotation.targetName
import scala.util.Try

object DarkSugars {


  // 1 - single arg method
  def singleArgMethod(arg: Int): Int = arg + 1

  val aMethodCall = singleArgMethod({
    // long code
    42
  })

  val aMethodCall_v2 = singleArgMethod {
    // long code
    42
  }

  // example: Try, Future
  val aTryInstance = Try {
    throw new RuntimeException
  }

  val anIncrementedList = List(1,2,3).map { x =>
    // code block
    x + 1
  }

  // 2 - single abstract method pattern (since Scala 2.12)
  trait Action {
    // can also have other implemented fields/methods here
    def act(x: Int): Int
  }

  val anAction = new Action {
    override def act(x: Int): Int = x + 1
  }

  val anAction_v2: Action = (x: Int) => x + 1
  anAction_v2.act(2)

  // 3 - methods ending in a : (colon) - RIGHT ASSOCIATIVE
  val aList = List(1,2,3)
  val aPrependedList = 0 :: aList // aList.::(0) NOT 0.::(.aList)
  val aBigList = 0 :: 1 :: 2 :: List(3, 4) // List(3, 4).::(2).::(1).::(0)

  class MyStream[T] {
    infix def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]()

  // 4 - multi word identifiers
  class Talker(name: String) {
    infix def `and the said`(gossip: String): Unit = println(gossip)
  }

  val daniel = new Talker("Daniel")
  daniel `and the said` "I love Scala"

  // example: HTTP libraries
  object `Content-Type` {
    val `application/json` = "application/json"
  }

  // 5 - infix types
  @targetName("Arrow")
  infix class -->[A, B]
  val compositeType: -->[Int, String] = new -->[Int, String]()
  val compositeType_v2: Int --> String = new-->[Int, String]()

  // 6 - update
  val anArray: Array[Int] = Array(1, 2, 3, 4)
  anArray.update(2, 45)
  anArray(2) = 45 // similar

  // 7 - mutable fields
  class Mutable {
    private var internalMember: Int = 0
    def member = internalMember // getter
    def member_=(value: Int): Unit = // setter
      internalMember = value
  }

  // 8 - vargars
  def methodWithVarargs(args: Int*) = {
    // return the number of arguments supplied
    args.length
  }
  val callWithZeroArgs = methodWithVarargs()
  val callWithOneArgs = methodWithVarargs(78)

  val aCollection = List(1, 2, 3)
  val callWithDynamicArgs = methodWithVarargs(aCollection*)

  val aMutable = Mutable()
  aMutable.member = 3
  println(aMutable.member)




  def main(args: Array[String]): Unit = {

  }

}
