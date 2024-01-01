package com.rockthejvm.part2afp

object FunctionalCollections {

  // sets are functions A => Boolean
  val aSet: Set[String] = Set("I", "Love", "Scala")
  val setContainsScala = aSet("Scala")

  // Seq extends PartialFunction[Int, A] index to value of A
  val aSeq: Seq[Int] = Seq(1, 2, 3, 4)
  val anElement = aSeq(2)

  // Map[K, V] "extends" PartialFunction[K, V]
  val aPhonebook: Map[String, Int] = Map(
    "Alice" -> 1234,
    "Bob" -> 3245,
  )
  val alicesPhone = aPhonebook("Alice")
  val bobsPhone = aPhonebook("Bob")


  def main(args: Array[String]): Unit = {

  }

}
