package com.rockthejvm.practice

abstract class LList[T] {

  def head: T
  def tail: LList[T]
  def isEmpty: Boolean
  def add(element: T): LList[T] = Cons(element, this)

  override def toString: String = super.toString

}

class Empty[T] extends LList[T] {
  override def head: T = throw new NoSuchElementException
  override def tail: LList[T] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def toString: String = "|"
}

// override val -> define accessors which is also a constructor argument (WOW!)
class Cons[T](override val head: T, override val tail: LList[T]) extends LList[T] {
  override def isEmpty: Boolean = false
  override def toString: String = s"$head ${tail.toString}"
}

object LList {
  def main(args: Array[String]): Unit = {
    val test = Cons(1, Cons(2, Cons(3, Empty())))
    val test2 = Empty().add(1).add(2).add(3)
    println(test)
    println(test2)

    val animals = Cons("cat", Cons("dog", Cons("horse", Empty())))
    println(animals)
  }
}