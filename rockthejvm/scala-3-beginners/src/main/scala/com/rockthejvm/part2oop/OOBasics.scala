package com.rockthejvm.part2oop

import com.rockthejvm.part1basics.StringOps.name

object OOBasics {

  // classes
  class Person(val name: String, age: Int) { // constructor signature
    // fields
    val allCaps = name.toUpperCase()

    // methods
    def greet(name: String): String =
      s"${this.name} says: Hi, $name"

    // signature differs
    // method overloading
    def greet(): String =
      s"Hi, everyone, my name is $name"

    // aux constructor, it has to call another constructor
    // NOTE: this is not very useful because we can use default args in the main constructor
    def this(name: String) =
      this(name, 0)

    def this() =
      this("Jane Doe")
  }

  val aPerson: Person = new Person("John", 26)
  val john = aPerson.name // class parameter != field
  val johnYelling = aPerson.allCaps
  val hello = aPerson.greet("Daniel")

  def main(args: Array[String]): Unit = {
    println(hello)
    println(aPerson.greet())

    val charlesDickens = new Writer("Charles", "Dickens", 1812)
    val novel = new Novel("Great Expectations", 1816, charlesDickens)
    println(charlesDickens.fullName())
    println(novel.isWrittenBy(charlesDickens))
    val newRelease = novel.copy(2013)

    val c: Counter = new Counter(10)
    val updated = c.inc().dec(3).print()
  }

}

/**
  * Exercise
  */

 class Writer(firstName: String, lastName: String, val year: Int) {
   def fullName(): String =
     s"$firstName $lastName"
 }

 class Novel(name: String, yearOfRelease: Int, author: Writer) {
   def authorAge(): Int = yearOfRelease - author.year

   def isWrittenBy(author: Writer): Boolean = this.author == author

   def copy(newYearOfRelease: Int): Novel =
     new Novel(this.name, newYearOfRelease, this.author)
 }

 class Counter(val value: Int = 0) {
   def inc(): Counter = inc(1)
   def dec(): Counter = dec(1)
   def inc(n: Int): Counter = new Counter(value + n)
   def dec(n: Int): Counter = new Counter(value - n)
   def print(): Unit = println(value)
 }
