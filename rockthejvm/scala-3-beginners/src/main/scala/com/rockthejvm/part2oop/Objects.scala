package com.rockthejvm.part2oop

object Objects {

    object MySingleton {
        val aField = 45
        def aMethod(x: Int) = x + 1
    }

    val theSingleton = MySingleton
    val anotherSingleton = MySingleton

    class Person(name: String) {
        def sayHi(): String = s"Hi, my name is $name"
    }

    // companion object = class + object with the same name in the same file
    object Person {
        val N_EYES = 2
        def canFly(): Boolean = false
    }

    val mary: Person = new Person("Mary")
    val marysGreeting = mary.sayHi() 

    val humansCanFly = Person.canFly()
    val numberOfEyes = Person.N_EYES

    // object can extend classes
    object BigFoot extends Person("Big Foot")

    // Scala application = object + main method
    def main(args: Array[String]): Unit =
        println(theSingleton == anotherSingleton)

}
