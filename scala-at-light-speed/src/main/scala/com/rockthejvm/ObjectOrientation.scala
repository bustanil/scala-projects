package com.rockthejvm

import scala.annotation.targetName

object ObjectOrientation extends App {

  // class and instance
  class Animal {
    // define fields
    val age: Int = 0
    // define methods
    def eat(): Unit = println("I'm eating")
  }
  val anAnimal = new Animal

  // inheritance
  class Dog(val name: String) extends Animal { // constructor definition

  }

  val aDog = new Dog("Lassie")

  // constructor arguments are NOT fields: need to put 'val' before the constructor argument

  // subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog("Hachi")
  aDeclaredAnimal.eat() // the most derived method whill be called a runtime

  // abstract class
  abstract class WalkingAnimal {
    val hasLegs = true // by default public, can restrict by adding 'private' or 'protected'
    def walk(): Unit
  }

  // interface = ultimate abstract type
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  // single class inheritance, multi-trait "mixing"
  class Crocodile extends Animal with Carnivore with Philosopher {
    override def eat(animal: Animal): Unit = println("I am eating you, animal!")
    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }

  trait Philosopher {
    def ?!(thought: String): Unit
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // infix notation = object method argument, only available for method with one argument

  aCroc ?! "What if we could fly?"

  // operators in Scala are actually methods
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2) // equivalent

  // anonymous classes
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am dinosaur so I can eat pretty much anything")
  }

  // singleton object
  object MySingleton  { // define the type and also a singleton instance
    val mySpecialValue = 12345
    def mySpecialMethod(): Int = 2345
    def apply(x: Int): Int = x + 1
  }

  MySingleton.mySpecialMethod()
  MySingleton.apply(234)
  MySingleton(234) // equivalent to MySingleton.apply(65)

  object Animal { // companion object (has the same name of an existing class/trait
    // companions can access each other's private fields/methods
    // singleton Animal and instances of Animal are different things
    val canLiveIndefinitely = false
  }

  val animalsCanLiveForever = Animal.canLiveIndefinitely // like "static" fields/methods

  // case classes = lightweight data structures with some boilerplate
  // sensible equals and hashcode
  // serialization
  // companion with apply
  case class Person(name: String, age: Int)

  // maybe constructed without new
  val bob = Person("bob", 44) // Person.apply("bob", 44)

  // exceptions
  try {
    // code that can throw
    val x: String = null
    x.length
  } catch {
    case e: Exception => "Some faulty error message"
  } finally {
    // execute some code no matter what
  }

  // generics
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  // using a generic with a concrete type
  val aList: List[Int] = List(1, 2, 3) // List.apply(1, 2, 3)
  val first = aList.head
  val rest = aList.tail

  val aStringList: List[String] = List("hello", "Scala")
  val firstString = aStringList.head
  val restString = aStringList.tail

  // Point #1: in Scala we operate with IMMUTABLE values/objects
  // any modification to an object must return ANOTHER object
  /**
   * Benefits:
   * 1) works miracles in multithreaded/distributed env
   * 2) helps making sense of the code ("reasoning about")
   */
  val reverseList = aList.reverse // returns a NEW list

  // Point #2: Scala is the closest to the Object Oriented ideal
}
