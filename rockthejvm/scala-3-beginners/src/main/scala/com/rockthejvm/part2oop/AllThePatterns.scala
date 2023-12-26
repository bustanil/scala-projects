package com.rockthejvm.part2oop

import com.rockthejvm.practice.{Cons, LList, Empty}

object AllThePatterns {

  object MySingleton

  // 1 - constants (switch on steroid)
  val someValue: Any = "Scala"
  val constants = someValue match {
    case 42 => "a number"
    case "Scala" => "The Scala"
    case true => true
    case MySingleton => "A singleton object"
  }

  // 2 - match anything
  val matchAnythingVar = someValue match { // why? because we can use expression in pattern matching
    case something => s"I've matched anything, it's $something"
  }
  val matchAnything = someValue match {
    case _ => "I can match anything at all"
  }

  // 3 - tuples
  val aTuple = 1 -> 4
  val matchTuple = aTuple match {
    case (1, somethingElse) => s"A tuple with 1 and $somethingElse"
    case (something, 2) => "A tuple with 2 as its second field"
  }

  // PM structures can be nested
  val nestedTuple = (1 -> (2 -> 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) => "A nested tuple... "
  }

  // 4 - case classes
  val aList: LList[Int] = Cons(1, Cons(2, Empty()))
  val matchList = aList match {
    case Empty() => "an empty list"
    case Cons(head, Cons(_, tail)) => s"a non-empty list starting with $head"
  }

  val anOption: Option[Int] = Option(2)
  val matchOption = anOption match {
    case None => "an empty option"
    case Some(value) => s"non-empty, got $value"
  }

  // 5 - standard list
  val aStandardList = List(1, 2, 3, 4, 5)
  val matchStandardList = aStandardList match {
    case List(1, _, _, _) => "list with 4 element, first with 1"
    case List(1, _*) => "list starting with 1"
    case List(1, 2, _) :+ 42 => "list ending in 42"
    case head :: tail => "deconstructed list"
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val matchTyped = unknown match {
    case anInt: Int => s"I match an int, I can add 2 to it ${anInt + 2}"
    case aString: String => "I match a string"
    case _: Double => "I matched a double"
  }

  // 7 - name binding
  val bindingNames = aList match {
    case Cons(head, rest @ Cons(_, tail)) => s"can use $rest"
  }

  // 8 - chained patterns / like falls through in Java switch statement
  val multiMatch = aList match {
    case Empty() | Cons(0, _) => "an empty list to me"
  }

  // if guards
  val secondElementSpecial => aList match {
    case Cons(_, Cons(specialEment, _)) if specialEment == 5 => "second element is big enough"
  }

  def main(args: Array[String]): Unit = {

  }

}
