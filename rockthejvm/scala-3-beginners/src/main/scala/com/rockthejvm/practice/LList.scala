package com.rockthejvm.practice

import scala.annotation.tailrec

abstract class LList[T] {

  def head: T
  def tail: LList[T]
  def isEmpty: Boolean
  def add(element: T): LList[T] = Cons(element, this)
  def map[B](t: T => B): LList[B]
  def filter(p: T => Boolean): LList[T]
  def withFilter(p: T => Boolean): LList[T] = filter(p)
  def flatMap[B](t: T => LList[B]): LList[B]
  def foreach(f: T => Unit): Unit
  def zipWith[U](that: LList[T], f: (T, T) => U): LList[U]
  def foldLeft[U](start: U)(f: (T, U) => U): U
  infix def ++(that: LList[T]): LList[T]
  override def toString: String = super.toString

}

case class Empty[T]() extends LList[T] {
  override def head: T = throw new NoSuchElementException
  override def tail: LList[T] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def toString: String = "|"
  override def map[B](transformer: T => B): LList[B] = Empty()
  override def filter(p: T => Boolean): LList[T] = this
  override def flatMap[B](t: T => LList[B]): LList[B] = Empty()
  override def foreach(f: T => Unit): Unit = ()
  override infix def ++(that: LList[T]): LList[T] = that

  override def zipWith[U](that: LList[T], f: (T, T) => U): LList[U] = Empty[U]()
  override def foldLeft[U](start: U)(f: (T, U) => U): U = start
}

// override val -> define accessors which is also a constructor argument (WOW!)
case class Cons[T](head: T, tail: LList[T]) extends LList[T] {
  override def isEmpty: Boolean = false
  override def toString: String = s"$head ${tail.toString}"
  def map[B](t: T => B): LList[B] = Cons(t.apply(head), tail.map(t))

  override def filter(p: T => Boolean): LList[T] = {
    if (p.apply(head)) Cons(head, tail.filter(p))
    else tail.filter(p)
  }

  override def flatMap[B](t: T => LList[B]): LList[B] = t.apply(head) ++ tail.flatMap(t)

  override infix def ++(that: LList[T]): LList[T] = Cons(head, tail ++ that)

  override def foreach(f: T => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def zipWith[U](that: LList[T], f: (T, T) => U): LList[U] = {
    if (that.isEmpty) Empty[U]()
    else Cons(f(head, that.head), tail.zipWith(that.tail, f))
  }

  override def foldLeft[U](start: U)(f: (T, U) => U): U = {
    tail.foldLeft(f(head, start))(f)
  }
}

trait Predicate[T] {
  def test(value: T): Boolean
}

trait Transformer[A, B] {
  def transform(a: A): B
}

object LList {
  @tailrec
  def find[A](list: LList[A], predicate: A => Boolean): A = {
    if (list.isEmpty) throw new NoSuchElementException
    else if (predicate.apply(list.head)) list.head
    else find(list.tail, predicate)
  }

  def main(args: Array[String]): Unit = {
    val test = Cons(1, Cons(2, Cons(3, Empty())))
    val test2 = Empty().add(6).add(5).add(4).add(3)
//    println(test)
//    println(test2)
//
//    val animals = Cons("cat", Cons("dog", Cons("horse", Empty())))
//    println(animals)
//
//    val testMap = test.map[Int](a => a * 2)
//
//    println(testMap)
//
//    val testFilter = test.filter((value: Int)=> value < 2)
//
//    println(testFilter)
//
//    println(test ++ test2)
//
//
//    val f = find(test, (value: Int) => value == 1)
//
//    println(f)

    test2.foreach(println)
    test2.zipWith(test, _ + _).foreach(println)

    println(test2.foldLeft(0)(_ + _))
    val add: (Int, Int) => Int = _ + _
    println(Empty[Int]().foldLeft(0)(add))
  }
}