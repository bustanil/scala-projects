package com.rockthejvm.part2oop

object Exceptions {

  val aString: String = null

  // 1. throw exceptions
//  val aWeirdValue: Int = throw new NullPointerException // returns Nothing

  // type Throwable
  //   Error, e.g. StackOverflowError => JVM errors
  //   Exception

  def getInt(withExceptions: Boolean) =
    if (withExceptions) throw new NullPointerException("error")
    else 42

  val potentialFail = try {
    getInt(true)
  } catch {
    case s: NullPointerException => "bustanil"
    case _: RuntimeException => 10
  } finally {
    // execute no matter what
    // used to close resources
    // returns Unit here
  }

  class MyException extends RuntimeException {
    // fields or methods
    override def getMessage: String = "my exception"
  }

  val myException: MyException = new MyException

  def main(args: Array[String]): Unit = {
    println(aString)
    println(potentialFail)
    val throwingMyException = throw MyException()
  }

}
