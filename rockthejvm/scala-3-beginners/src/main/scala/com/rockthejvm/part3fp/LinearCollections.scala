package com.rockthejvm.part3fp

object LinearCollections {

  // Seq = well-defined ordering and indexing
  def testSeq(): Unit = {
    val aSeq = Seq(1, 2, 3, 4)

    val thirdElement = aSeq.apply(3)
    // map/flatMap/filter/for
    val incrementedSeq = aSeq.map(_ + 1)
    val flatmapped = aSeq.flatMap(x => Seq(x, x + 1))
    val filtered = aSeq.filter(_ % 2 == 0)

    val reversed = aSeq.reverse
    val concatenated = aSeq ++ Seq(6, 7, 8)
    val sorted = aSeq.sorted
    val sum = aSeq.foldLeft(0)(_ + _)
    val stringRep = aSeq.mkString

    println(aSeq)
    println(reversed)
    println(concatenated)
    println(sorted)
    println(sum)
    println(stringRep)
  }

  // lists
  def testLists(): Unit = {
    val aList = List(1, 2, 3, 4)
    // same API as Seq
    val first = aList.head
    val rest = aList.tail
    // appending and prepending
    val aBiggerList = 0 +: aList :+ 4
    val prepending = 0 :: aList // :: is equivalent to Cons in our LList
    // utility methods
    val scalax5 = List.fill(5)("Scala")
  }

  // ranges
  def testRanges(): Unit = {
    val aRange: Seq[Int] = 1 to 10 // inclusive
    val nonInclusive: Seq[Int] = 1 until 10 // non-inclusive
    // same Seq API
    (1 to 10).foreach(println)
  }

  // arrays
  def testArray(): Unit = {
    val anArray = Array(1, 2, 3, 4) // int[] on the JVM
    // has access most Seq API
    // Arrays are not Seqs
    val aSeq: Seq[Int] = anArray.toIndexedSeq

    // arrays are mutable
    anArray.update(1, 99)

    println(anArray.mkString("Array(", ", ", ")"))
  }

  // vectors = fast sequences for a large amount of data
  def testVector(): Unit = {
    val aVector = Vector(1, 2, 3, 4, 5)
    // the same Seq API
  }

  // set - no duplicates
  def testSets(): Unit = {
    val aSet = Set(1, 2, 3, 4, 5)
    // equals + hashcode = hash set
    // main API test if in set
    val contains3 = aSet.contains(3)
    val contains3_v2 = aSet.apply(3)

    println(contains3)

    // adding/removing
    val aBiggerSet = aSet + 44
    val anotherSet = aSet - 4
    println(aBiggerSet.mkString(","))
    println(anotherSet.mkString(","))
    println((aBiggerSet ++ anotherSet).mkString(","))
    println((aBiggerSet union anotherSet).mkString(","))
    println((aBiggerSet | anotherSet).mkString(",")) // '&' alias for union

    // difference
    val aDiffSet = aSet.diff(anotherSet)
    val aDiffSet_v2 = aSet -- anotherSet

    // intersection
    val aIntersectSet = aSet.intersect(anotherSet)
    val aIntersectSet_v2 = aSet & anotherSet
  }

  def main(args: Array[String]): Unit = {
    testSeq()
    testRanges()
    testArray()
    testSets()
  }
}