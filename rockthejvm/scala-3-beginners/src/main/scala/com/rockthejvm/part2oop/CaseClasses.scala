package com.rockthejvm.part2oop

object CaseClasses {

  case class Person(name: String, age: Int) {
    // do some other stuff
  }

  // 1. class args are now fields
  val bustanil = new Person("Bustanil", 40)
  val bustanilAge = bustanil.age

  // 2. toString, equals and hashCode
  val bustanilToString = bustanil.toString

  // 3. utility methods
  val copy = bustanil.copy(age = 12)

  // 4. have companion object
  val thePersonSingleton = Person
  val bustanil_v2 = Person("Bustanil", 99)

  // 5. case classes are serializable

  // 6. have extractor patterns for Pattern Matching

  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  def main(args: Array[String]): Unit = {
    println(bustanil)
    println(bustanilToString)
  }

}
