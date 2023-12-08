package com.rockthejvm.part2oop

object AbstractDataTypes {
    
    abstract class Animal {
        val creatureType: String
        def eat(): Unit
        // non-abstract fields/methods allowed
        def preferredMeal: String = "anything" // accessor methods
    }

    class Dog extends Animal {

      override val creatureType: String = "domestic"

      override def eat(): Unit = println("crunching this bone")
      // overriding val preferred
        override def preferredMeal: String = "bones" // only allowed for methods without arguments (accessors)
    }

    // abstract class cannot be instantiated
    // val anAnimal: Animal = new Animal

    val aDog: Animal = new Dog

    // traits
    // traits can have constructor arguments (Scala 3)
    trait Carnivore {
        def eat(animal: Animal): Unit
    }

    class TRex extends Carnivore {
      override def eat(animal: Animal): Unit = println("I'm a t-rex. I eat animal")
    }

    // practical differences
    // one class inheritance
    // multiple traits inheritance
    trait ColdBlooded
    class Crocodile extends Animal with Carnivore with ColdBlooded {
      override val creatureType: String = "croc"
      override def eat(animal: Animal): Unit = println("croc eating animal")
      override def eat(): Unit = println("I'm a croc, just crunching stuff")
    }

    // phylosophical difference abstract classes vs traits
    // - abstract classes are THINGS
    // - traits are BEHAVIORS

    /*
      Any
        AnyRef
            All classes we write
                scala.Null (the null reference)
        AnyVal 
            Int, Boolean, Char, ...
     */


    def main(args: Array[String]): Unit =
        ???
}
