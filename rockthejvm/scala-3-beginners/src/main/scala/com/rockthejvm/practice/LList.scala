package com.rockthejvm.practice

abstract class LList[T] {

  def head: T
  def tail: LList[T]
  def isEmpty: Boolean
  def add(element: T): LList[T] = Cons(element, this)
  def map[B](t: Transformer[T, B]): LList[B]
  def filter(p: Predicate[T]): LList[T]
  def flatMap[B](t: Transformer[T, LList[B]]): LList[B]
  infix def ++(that: LList[T]): LList[T]
  override def toString: String = super.toString

}

case class Empty[T]() extends LList[T] {
  override def head: T = throw new NoSuchElementException
  override def tail: LList[T] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def toString: String = "|"
  override def map[B](transformer: Transformer[T, B]): LList[B] = Empty()
  override def filter(p: Predicate[T]): LList[T] = this
  override def flatMap[B](t: Transformer[T, LList[B]]): LList[B] = Empty()
  override infix def ++(that: LList[T]): LList[T] = that
}

// override val -> define accessors which is also a constructor argument (WOW!)
case class Cons[T](head: T, tail: LList[T]) extends LList[T] {
  override def isEmpty: Boolean = false
  override def toString: String = s"$head ${tail.toString}"
  def map[B](t: Transformer[T, B]): LList[B] = Cons(t.transform(head), tail.map(t))

  override def filter(p: Predicate[T]): LList[T] = {
    if (p.test(head)) Cons(head, tail.filter(p))
    else tail.filter(p)
  }

  override def flatMap[B](t: Transformer[T, LList[B]]): LList[B] = t.transform(head) ++ tail.flatMap(t)

  override infix def ++(that: LList[T]): LList[T] = Cons(head, tail ++ that)
}

trait Predicate[T] {
  def test(value: T): Boolean
}

trait Transformer[A, B] {
  def transform(a: A): B
}

object LList {
  def main(args: Array[String]): Unit = {
    val test = Cons(1, Cons(2, Cons(3, Empty())))
    val test2 = Empty().add(6).add(5).add(4)
    println(test)
    println(test2)

    val animals = Cons("cat", Cons("dog", Cons("horse", Empty())))
    println(animals)

    val testMap = test.map(new Transformer[Int, Int]{
      override def transform(a: Int): Int = a * 2
    })

    println(testMap)

    val testFilter = test.filter(new Predicate[Int] {
      override def test(value: Int): Boolean = value < 2
    })

    println(testFilter)

    println(test ++ test2)

    def find[A](list: LList[A], predicate: Predicate[A]): A = {
      if (list.isEmpty) throw new NoSuchElementException
      else if (predicate.test(list.head)) list.head
      else find(list.tail, predicate)
    }

    val f = find(test, new Predicate[Int](){
      override def test(value: Int): Boolean = value == 1
    })

    println(f)
  }
}