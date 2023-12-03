package com.rockthejvm.part2oop

object Inheritance {

    class Animal {
        val creatureType =  "wild"
        def eat(): Unit = println("hahah")
    }

    class Cat extends Animal {
        def crunch() = {
            eat()
            println("crunch")
        }
    }

    val cat = new Cat

    class Person(val name: String, age: Int) {
        def this(name: String) = this(name, 0)
    }

    class Adult(name: String, age: Int, idCard: String) extends Person(name) // must specify super-constructor
    
    // overriding
    class Dog extends Animal {
        override val creatureType: String = "domestic"
        override def eat(): Unit = println("dog eating")
    }

    // subtype polymorphism
    val dog: Animal = new Dog
    dog.eat() // the most specific method will be called

    class Crocodile extends Animal {
        override val creatureType: String = "very wild"
        override def eat(): Unit = println("I can eat anything")

        def eat(food: String): Unit = println(s"crocodile eating $food")
    }

    val croc = new Crocodile

    def main(args: Array[String]): Unit = {
        cat.eat()
        cat.crunch()
        croc.eat()
        croc.eat("fish")
    }
}
