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

  infix def -(elem: A): FSet[A]
  infix def --(anotherSet: FSet[A]): FSet[A]
  infix def &(anotherSet: FSet[A]): FSet[A]
  def unary_! : FSet[A] = new PBSet[A](!contains(_))
}

object FSet {
  def apply[A](values: A*): FSet[A] = {
    def buildSet(valueSeq: Seq[A], acc: FSet[A]): FSet[A] =
      if(valueSeq.isEmpty) acc
      else buildSet(valueSeq.tail, acc + valueSeq.head)

    buildSet(values, Empty())
  }
}

case class PBSet[A](property: A => Boolean) extends FSet[A] {

  override def contains(elem: A): Boolean = property(elem)

  override infix def +(elem: A): FSet[A] = new PBSet[A](x => x == elem || property(x))

  override infix def ++(anotherSet: FSet[A]): FSet[A] =
    new PBSet[A](x => property(x) || anotherSet(x))

  override def map[B](f: A => B): FSet[B] = throw new RuntimeException("cannot iterate")

  override def flatMap[B](f: A => FSet[B]): FSet[B] = throw new RuntimeException("cannot iterate")

  override def filter(p: A => Boolean): FSet[A] = new PBSet[A](x => property(x) && p(x))

  override def foreach(f: A => Unit): Unit = throw new RuntimeException("cannot iterate")

  override infix def -(elem: A): FSet[A] = filter(_ != elem)

  override infix def --(anotherSet: FSet[A]): FSet[A] = filter(!anotherSet)

  override infix def &(anotherSet: FSet[A]): FSet[A] = filter(anotherSet)
}

case class Empty[A]() extends FSet[A] {

  override def contains(elem: A): Boolean = false

  override infix def +(elem: A): FSet[A] = Cons(elem, Empty())

  override infix def ++(anotherSet: FSet[A]): FSet[A] = anotherSet

  override def map[B](f: A => B): FSet[B] = Empty()

  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()

  override def filter(p: A => Boolean): FSet[A] = Empty()

  override def foreach(f: A => Unit): Unit = ()

  override infix def -(elem: A): FSet[A] = Empty()

  override infix def --(anotherSet: FSet[A]): FSet[A] = anotherSet

  override infix def &(anotherSet: FSet[A]): FSet[A] = Empty()
}

case class Cons[A](head: A, tail: FSet[A]) extends FSet[A] {

  override def contains(elem: A): Boolean =
    head == elem || tail.contains(elem)

  override infix def +(elem: A): FSet[A] =
    if (contains(elem)) this
    else Cons(elem, this)

  override infix def ++(anotherSet: FSet[A]): FSet[A] =
    if (anotherSet.contains(head)) tail ++ anotherSet
    else Cons(head, tail ++ anotherSet)

  override def map[B](f: A => B): FSet[B] =
    Cons(f(head), tail.map(f))

  override def flatMap[B](f: A => FSet[B]): FSet[B] =
    f(head) ++ tail.flatMap(f)

  override def filter(p: A => Boolean): FSet[A] =
    if (p(head)) Cons(head, filter(tail))
    else filter(tail)

  override def foreach(f: A => Unit): Unit =
    f(head)
    tail.foreach(f)

  override infix def -(elem: A): FSet[A] = filter(_ != head)

  override infix def --(anotherSet: FSet[A]): FSet[A] = filter(!anotherSet(_))

  override infix def &(anotherSet: FSet[A]): FSet[A] = filter(anotherSet)
}

object FunctionalSetPlayground {

  def main(args: Array[String]): Unit = {
    val aSet = Cons(2, Cons(2, Cons(1, Empty())))
    aSet.foreach(println)

    val aSet_v2 = FSet(1, 2, 2, 3, 4)
    aSet_v2.foreach(println)
  }


}
