package com.rockthejvm.part1basics

object ValuesAndTypes {

  // values
  val meaningOfLife: Int = 42

  // reassigning is not allowed
  // meaningOfLife = 45

  //type inference
  val anInteger = 67 // :Int is optional

  // common types
  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val aString: String = "Scala"
  val anInt: Int = 78 // 4 bytes
  val aShort: Short = 123 // 2 bytes
  val aLong: Long = 123423423434L // 8 bytes
  val aFloat: Float = 2.4F // 4 bytes
  val aDouble: Double = 3.14 // 8 bytes

  def main(args: Array[String]): Unit = {

  }

}
