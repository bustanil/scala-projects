package com.rockthejvm.part3fp

object TuplesMaps {

  // tuples = finite ordered "lists" (heterogeneous)
  val aTuple = (2, "rock the jvm") // Tuple2[Int, String] ... Tuple22
  val firstField = aTuple._1
  val aCopiedTuple = aTuple.copy(_1 = 54)

  // tuples of 2 elements
  val aTuple_v2 = 2 -> "rock the jvm"

  // maps: keys -> values
  val aMap = Map()

  val phonebook: Map[String, Int] = Map(
    "Jim" -> 444,
    "Jane" -> 123,
  )

  // core APIs
  val jimsPhoneExists = phonebook.contains("Jim")
  val janePhoneNumber = phonebook("Jane")
  val philPhoneNumber = phonebook("Phil") // crash when key is missing

  // default value if key is missing
  val phonebook_v2: Map[String, Int] = Map(
    "Jim" -> 444,
    "Jane" -> 123,
  ).withDefaultValue(-1)

  // add a pair
  val newPhonebook = phonebook + ("Phil" -> 2334)

  // remove a key
  val updatedPhoneBook = phonebook - "Joe"

  // list -> map
  val phonebookList = List(
    "Jim" -> 444,
    "Jane" -> 123,
  )

  val toMap = phonebookList.toMap

  // filtering keys
  val keys = phonebook.view.filterKeys(!_.startsWith("J"))

  // map values
  val updatedValues = phonebook.view.mapValues(_ + 1)

  // other collection can create map
  val names = List("Jane", "John", "Bob")
  val grouped = names.groupBy(_.charAt(0)) // Map[Char, List[String]]

  def main(args: Array[String]): Unit = {

  }

}
