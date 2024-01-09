package com.rockthejvm.practice

abstract class LzList[A] {
  def isEmpty: Boolean
  def head: A
  def tail: LzList[A]

  // utilities
  def #::(element: A): LzList[A] // prepending
  def ++(another: => LzList[A]): LzList[A]
  // classics
  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): LzList[B]
  def flatMap[B](f: A => LzList[B]): LzList[B]
  def filter(p: A => Boolean): LzList[A]
  def withFilter(p: A => Boolean): LzList[A] = filter(p)

  def take(n: Int): LzList[A]
  def takeAsList(n: Int): List[A]
  def toList: List[A] // use this carefully on large instance
}

case class LzEmpty[A]() extends LzList[A] {

  override def isEmpty: Boolean = true

  override def head: A = throw new NoSuchElementException

  override def tail: LzList[A] = throw new NoSuchElementException

  override def #::(element: A): LzList[A] = LzCons[A](element, this)

  override def ++(another: => LzList[A]): LzList[A] = another

  override def foreach(f: A => Unit): Unit = ()

  override def map[B](f: A => B): LzList[B] = LzEmpty[B]()

  override def flatMap[B](f: A => LzList[B]): LzList[B] = LzEmpty[B]()

  override def take(n: Int): LzList[A] =
    if (n == 0) this
    else throw new RuntimeException(s"Cannot take $n elements from an empty lazy list")

  override def takeAsList(n: Int): List[A] = take(n).toList

  override def toList: List[A] = List.empty

  override def filter(p: A => Boolean): LzList[A] = this
}

class LzCons[A](hd: => A, tl: => LzList[A]) extends LzList[A] {

  override lazy val head: A = hd
  override lazy val tail: LzList[A] = tl

  override def isEmpty: Boolean = false

  override def #::(element: A): LzList[A] = LzCons(element, this)

  override def ++(another: => LzList[A]): LzList[A] = LzCons[A](head, tail ++ another)

  override def foreach(f: A => Unit): Unit =
    f(head)
    tail.foreach(f)

  override def map[B](f: A => B): LzList[B] = LzCons[B](f(head), tail.map(f))

  override def flatMap[B](f: A => LzList[B]): LzList[B] = f(head) ++ tail.flatMap(f)
  override def take(n: Int): LzList[A] = {
    if (n == 0) LzEmpty[A]()
    else LzCons[A](head, tail.take(n - 1))
  }

  override def takeAsList(n: Int): List[A] = {
    if (n < 0) List.empty
    else head +: tail.takeAsList(n - 1)
  }

  override def toList: List[A] = head +: tail.toList

  override def filter(p: A => Boolean): LzList[A] =
    if (p(head)) LzCons[A](head, tail.filter(p))
    else tail.filter(p)
}

object LzList {
  def empty[A]: LzList[A] = LzEmpty()
  def generate[A](start: A)(generator: A => A): LzList[A] =
    LzCons[A](start, LzList.generate(generator(start))(generator))

  def from[A](list: List[A]): LzList[A] =
    if (list.isEmpty) LzEmpty[A]()
    else LzCons[A](list.head, LzList.from(list.tail))

  def apply[A](values: A*): LzList[A] = {
    LzList.from(values.toList)
  }

  def infiniteFibo: LzList[BigInt] = {
    def fibo(first: BigInt, second: BigInt): LzList[BigInt] =
      new LzCons[BigInt](first, fibo(second, first + second))

    fibo(1, 2)
  }

}

object LzListPlayground {

  def main(args: Array[String]): Unit = {
    val naturals = LzList.generate(1)(n => n + 1)
    println(naturals.head)
    println(naturals.tail.head)
    println(naturals.tail.tail.head)

    val first100 = naturals.take(100)
    first100.foreach(println)

    val combinationsList = for {
      number <- LzList(1, 2, 3)
      string <- LzList("black", "white")
    } yield s"$number-$string"

    combinationsList.foreach(println)

    // fibonacci
    val fibos = LzList.infiniteFibo
    println(fibos.take(5).toList)
    
  }

}
