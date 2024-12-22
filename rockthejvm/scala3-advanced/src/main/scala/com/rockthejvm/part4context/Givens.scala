package com.rockthejvm.part4context

object Givens {
    val aList = List(3, 4, 2, 1)
    val anOrderedList = aList.sorted

    val descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
    val anInverseOrderingList = aList.sorted(descendingOrdering)

    def main(args: Array[String]): Unit = {
        println(anOrderedList)
        println(anInverseOrderingList)
    }
}
