package com.rockthejvm.part1basics

object StringOps {

  val aString: String = "Hello world"

  // string functions
  val secondChar = aString.charAt(1)
  val firstWord = aString.substring(0, 5)
  val words = aString.split(" ")
  val startsWithHello = aString.startsWith("Hello")
  val allDashes = aString.replace(' ', '-')
  val allUppercase = aString.toUpperCase()
  val allLowercase = aString.toLowerCase()
  val nChars = aString.length

  // other functions
  val reversed = aString.reverse
  val aBunchOfChars = aString.take(10)

  // parse to number
  val numberAsString = "2"
  val number = "2".toInt

  // interpolation
  val name = "Alice"
  val age = 12
  val greeting = s"Hello, I'm $name and I am $age years old"
  val greeting_v2 = s"Hello, I'm $name and I am ${age + 1} years old"

  // f-interpolation
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.2f burgers per minute"

  // raw interpolation
  val escapes = raw"This is a \n newline"
  def main(args: Array[String]): Unit = {
    println(allDashes)
    println(myth)
    println(escapes)
  }

}
