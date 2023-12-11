package com.rockthejvm.practice

abstract class LList {

  def head: Int
  def tail: LList
  def isEmpty: Boolean
  def add(element: Int): LList = Cons(element, this)

  override def toString: String = super.toString

}

class Empty extends LList {
  override def head: Int = throw new NoSuchElementException
  override def tail: LList = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def toString: String = "|"
}

// override val -> define a method which is also a constructor argument (WOW!)
class Cons(override val head: Int, override val tail: LList) extends LList {
  override def isEmpty: Boolean = false
  override def toString: String = s"$head ${tail.toString}"
}

object LList {
  def main(args: Array[String]): Unit = {
    val test = Cons(1, Cons(2, Cons(3, Empty())))
    val test2 = Empty().add(1).add(2).add(3)
    println(test)
    println(test2)
  }
}