package com.rockthejvm

object PatternMatching extends App {

  // switch "expression"
  val anInteger = 55
  val order = anInteger match {
    case 1 => "First"
    case 2 => "Second"
    case 3 => "Third"
    case _ => anInteger + "th"
  }

  // PM is an EXPRESSION
  println(order)

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 43) // Person.apply("Bob", 43)

  val personGreeting = bob match {
    case Person(name, age) => s"Hi, my name is $name and I am $age years old"
    case _  => "Something else"
  }
  println(personGreeting)

  // deconstructing tuples
  val aTuple = ("Bon Jovi", "Rock")
  val bandDescription = aTuple match {
    case (band, genre) => s"$band belongs to genre $genre"
    case _ => "I don't know what you're talking about"
  }

  // decomposing List
  val aList = List(1,2,3)
  val listDescription = aList match {
    case List(_, 2, _) => "List containing 2 on it's 2nd position"
    case _ => "unknown list"
  }

  // if PM doesn't match anything, it will throw a MatchError
  // PM will try all cases in sequence
}
