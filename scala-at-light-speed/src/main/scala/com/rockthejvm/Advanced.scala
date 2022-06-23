package com.rockthejvm

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Advanced extends App {

  // lazy evaluation
  lazy val aLazyValue = 2
  lazy val aLazyValueWithSideEffect = {
    println("I am so very lazy!")
    43
  }

  val eagerValue = aLazyValueWithSideEffect + 1
  // useful ini infinite collections

  // pseudo-collections: Option and Try
  def methodWhichCanReturnNull(): String = "Hello, Scala"
  if (methodWhichCanReturnNull() == null) {
    // defensive code against null
  }

  val anOption = Option(methodWhichCanReturnNull()) // Some("Hello, Scala")
  // option = a collection which contains at most one element: Some(value) or None

  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string: $string"
    case None => "I obtained nothing"
  }
  // map, flatMap, filter

  def methodWhichCanThrowException(): String = throw new RuntimeException
  try {
    methodWhichCanThrowException()
  } catch {
    case e: Exception => "defend against this evil exception"
  }

  val aTry = Try(methodWhichCanThrowException())
  // a Try: "collection" with either a value if the code went well, or an exception if the code threw one

  val anotherStringProcessing = aTry match {
    case Success(value) => s"I have obtained ao valid string: $value"
    case Failure(exception) => s"I have obtained an exception: $exception"
  }
  // map, flatMap, filter

  // evaluate something on another thread: Future
  // asynchronous programming
  val aFuture = Future {
    println("loading...")
    Thread.sleep(1000)
    println("I have computed the value")
    67
  }
  // Future is a "collection" which contains a value when it's evaluated
  // Future is composable map, flatMap and filter
  // Future, Try and Option are "monads"

  // Implicits
  // #1: Implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt: Int = 46

  print(aMethodWithImplicitArgs) // aMethodWithImplicitArgs(myImplicitInt)

  // #2: Implicit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  println(23.isEven()) // new MyRichInteger(23).isEven()
  // use implicits with very care
}
