package com.rockthejvm.practice

abstract class Maybe[A] {
  def map[B](f: A => B): Maybe[B]
  def flatMap[B](f: A => Maybe[B]): Maybe[B]
  def filter(p: A => Boolean): Maybe[A]
}

case class Just[A](value: A) extends Maybe[A] {

  override def map[B](f: A => B): Maybe[B] = Just[B](f.apply(value))

  override def flatMap[B](f: A => Maybe[B]): Maybe[B] = f.apply(value)

  override def filter(p: A => Boolean): Maybe[A] =
    if (p.apply(value)) Just(value)
    else None()
}

case class None[A]() extends Maybe[A] {

  override def map[B](f: A => B): Maybe[B] = None()

  override def flatMap[B](f: A => Maybe[B]): Maybe[B] = None()

  override def filter(p: A => Boolean): Maybe[A] = None()
}

object MaybeTest {
  def main(args: Array[String]): Unit = {
    val maybeInt = Just(2)
    println(maybeInt.map(_ + 1))
  }
}