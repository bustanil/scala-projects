package com.rockthejvm.practice

abstract class FSet[A] extends (A => Boolean) {
  // main API
  def contains(elem: A): Boolean
  def apply(elem: A): Boolean = contains(elem)

  infix def +(elem: A): FSet[A]
  infix def ++(anotherSet: FSet[A]): FSet[A]

  // "classics"
  def map[B](f: A => B): FSet[B]
  def flatMap[B](f: A => FSet[B]): FSet[B]
  def filter(p: A => Boolean): FSet[A]
  def foreach(f: A => Unit): Unit
}

object FSet {
  def apply[A](values: A*): FSet[A] = {
    def buildSet(valueSeq: Seq[A], acc: FSet[A]): FSet[A] =
      if(valueSeq.isEmpty) acc
      else buildSet(valueSeq.tail, acc + valueSeq.head)

    buildSet(values, Empty())
  }
}

case class Empty[A]() extends FSet[A] {

  override def contains(elem: A): Boolean = false

  override infix def +(elem: A): FSet[A] = Cons(elem, Empty())

  override infix def ++(anotherSet: FSet[A]): FSet[A] = anotherSet

  override def map[B](f: A => B): FSet[B] = Empty()

  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()

  override def filter(p: A => Boolean): FSet[A] = Empty()

  override def foreach(f: A => Unit): Unit = ()
}

case class Cons[A](a: A, tail: FSet[A]) extends FSet[A] {

  override def contains(elem: A): Boolean =
    a == elem || tail.contains(elem)

  override infix def +(elem: A): FSet[A] =
    if (contains(elem)) this
    else Cons(elem, this)

  override infix def ++(anotherSet: FSet[A]): FSet[A] =
    if (anotherSet.contains(a)) tail ++ anotherSet
    else Cons(a, tail ++ anotherSet)

  override def map[B](f: A => B): FSet[B] =
    Cons(f(a), tail.map(f))

  override def flatMap[B](f: A => FSet[B]): FSet[B] =
    f(a) ++ tail.flatMap(f)

  override def filter(p: A => Boolean): FSet[A] =
    if (p(a)) Cons(a, filter(tail))
    else filter(tail)

  override def foreach(f: A => Unit): Unit =
    f(a)
    tail.foreach(f)
}

object FunctionalSetPlayground {

  def main(args: Array[String]): Unit = {
    val aSet = Cons(2, Cons(2, Cons(1, Empty())))
    aSet.foreach(println)

    val aSet_v2 = FSet(1, 2, 2, 3, 4)
    aSet_v2.foreach(println)
  }


}
