package com.rockthejvm.part2oop

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
  }

}

