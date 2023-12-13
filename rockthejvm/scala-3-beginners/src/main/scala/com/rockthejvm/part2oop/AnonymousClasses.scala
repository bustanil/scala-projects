package com.rockthejvm.part2oop

object AnonymousClasses {

  abstract class Animal(val name: String) {
    def eat(): Unit
  }

  val aWeirdAnimal = new Animal("dog") {
    override def eat(): Unit = println("a weird animal")
  }

  def main(args: Array[String]): Unit = {
    println(aWeirdAnimal.name)
    aWeirdAnimal.eat()
  }

}
