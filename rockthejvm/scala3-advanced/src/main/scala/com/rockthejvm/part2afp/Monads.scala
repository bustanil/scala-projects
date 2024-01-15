package com.rockthejvm.part2afp

import scala.annotation.targetName

object Monads {

  def listStory(): Unit = {
    val aList = List(1, 2, 3)
    val listMultiply = for {
      x <- List(1, 2, 3)
      y <- List(4, 5, 6)
    } yield x * y

    List(1, 2, 3).flatMap(x => List(4, 5, 6).map(y => x * y))

    val f = (x: Int) => List(x, x + 1)
    val g = (x: Int) => List(x, 2 * x)
    val pure = (x: Int) => List(x) // same as the list "constructor"

    // prop 1: left identity
    val leftIdentity = pure(42).flatMap(f) == f(42) // for every x, for every f

    // prop: right identity
    val rightIdentity = aList.flatMap(pure) == aList // for every list

    // prop 3: associativity
    /*
      [1, 2, 3].flatMap(x => [x, x + 1]) = [1,2,2,3,3,4]
      [1,2,2,3,3,4].flatMap(x => [x, 2 * x] = [1,2,2,4,    2,4,3,6     3,6,4,8]
      [1,2,3].flatMap(f).flatMap(g) = [1,2,2,4,2,4,3,6,3,6,4,8]

      [1,2,2,4] = f(1).flatMap(g)
      [2,4,3,6] = f(2).flatMap(g)
      [3,6,4,8] = f(3).flatMap(g)
      [1,2,2,4,2,4,3,6,3,6,4,8] = f(1).flatMap(g) ++ f(2).flatMap(g) ++ f(3).flatMap(g) = [1,2,3].flatMap(x => f(x).flatMap(g))
     */
    val associativity = aList.flatMap(f).flatMap(g) == aList.flatMap(x => f(x).flatMap(g))
  }

  def optionStory(): Unit = {
    val anOption = Option(42)
    val optionString = for {
      lang <- Option("Scala")
      ver <- Option(3)
    } yield s"$lang-$ver"

    val optionString_v2 = Option("Scala").flatMap(lang => Option(3).map(ver => s"$lang-$ver"))
    val f = (x: Int) => Option(x + 1)
    val g = (x: Int) => Option(2 * x)
    val pure = (x: Int) => Option(x)
    
    // prop 1: left identity
    val leftIdentity = pure(42).flatMap(f) == f(42)// for any x, for any f
    
    // prop 2: right identity
    val rightIdentity = anOption.flatMap(pure) == anOption // for any Option
    
    // prop 3: associativity
    /*
      anOption.flatMap(f).flatMap(g) = Option(42).flatMap(x => Option(x + 1)).flatMap(x => Option(2 * x))
      = Option(43).flatMap(x => Option(2 * x))
      = Option(86)
    
      anOption.flatMap(x => f(x).flatMap(g)) = Option(42).flatMap(x => Option(x + 1).flatMap(x => Option(2 * x)))
      = Option(42).flatMap(x => 2 * x + 2)
      = Option(86)
     */
    val associativity = anOption.flatMap(f).flatMap(g) == anOption.flatMap(x => f(x).flatMap(g)) // for any option, f and g
    
    // MONADS = chain dependent computations
    
  }

  // interpretation: ANY computation that might produce side effect
  case class IO[A](unsafeRun: () => A) {
    def map[B](f: A => B): IO[B] =
      IO(() => f(unsafeRun()))

    def flatMap[B](f: A => IO[B]): IO[B] =
      IO(() => f(unsafeRun()).unsafeRun())
  }

  object IO {
    @targetName("pure")
    def apply[A](value: => A): IO[A] =
      new IO(() => value)
  }

  def possibleMonadStory(): Unit = {
    val aPossibleMonad = IO(42)
    val f = (x: Int) => IO(x + 1)
    val g = (x: Int) => IO(2 * x)
    val pure = (x: Int) => IO(x)

    // left identity
    // pure(42).flatMap(f)
    // PM( () => 42 ).flatMap(f)
    // PM( () => f(42).unsafeRun() )
    // PM( () => PM(() => 43).unsafeRun()
    // PM( () => 43 )

    // f(42)
    // PM( () => 42 + 1 )
    // PM( () => 43 )
    val leftIdentity = pure(42).flatMap(f).unsafeRun() == f(42).unsafeRun()

    // right identity
    // aPossibleMonad.flatMap(pure)
    // PM( () => 42 ).flatMap(pure)
    // PM( () => pure(42).unsafeRun() )
    // PM( () => PM(() => 42).unsafeRun() )
    // PM( () => 42 )

    // aPossibleMonad
    // PM(42)
    // PM( () => 42 )
    val rightIdentity = aPossibleMonad.flatMap(pure).unsafeRun() == aPossibleMonad.unsafeRun()

    // associativity
    // aPossibleMonad.flatMap(f).flatMap(g)
    // PM( () => 42 ).flatMap(f).flatMap(g)
    // PM( () => f(42).unsafeRun() ).flatMap(g)
    // PM( () => PM(() => 42 + 1).unsafeRun() ).flatMap(g)
    // PM( () => PM(() => 43).unsafeRun() ).flatMap(g)
    // PM( () => 43 ).flatMap(g)
    // PM( () => g(43).unsafeRun() )
    // PM( () => PM(() => 2 * 43).unsafeRun() )
    // PM( () => PM(() => 86).unsafeRun() )
    // PM( () => 86 )

    // aPossibleMonad.flatMap(x => f(x).flatMap(g))
    // PM( () => 42 ).flatMap(x => f(x).flatMap(g))
    // PM( () => f(42).flatMap(g).unsafeRun() )
    // PM( () => PM(() => 42 + 1).flatMap(g).unsafeRun() )
    // PM( () => PM(() => 43).flatMap(g).unsafeRun() )
    // PM( () => PM(() => g(43).unsafeRun()).unsafeRun() )
    // PM( () => PM(() => PM(() => 86).unsafeRun()).unsafeRun() )
    // PM( () => PM(() => 86).unsafeRun() )
    // PM( () => 86 )
    val associativity = aPossibleMonad.flatMap(f).flatMap(g).unsafeRun() == aPossibleMonad.flatMap(x => f(x).flatMap(g)).unsafeRun()

    println(leftIdentity)
    println(rightIdentity)
    println(associativity)
  }

  def possiblyMonadExample(): Unit = {
    val aPossiblyMonad = IO {
      println("printing my first possibly monad")
      42
    }

    val mySecondPM = IO {
      println("my second possibly monad")
      "Scala"
    }

    val aForComprehension = for {
      num <- aPossiblyMonad
      lang <- mySecondPM
    } yield s"$num-$lang"

    aForComprehension.unsafeRun()
  }

  def main(args: Array[String]): Unit = {
    possiblyMonadExample()
  }

}
