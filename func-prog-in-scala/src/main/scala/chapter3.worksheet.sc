
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  // exercise 3.2
  def tail[A](l: List[A]): List[A] = 
    drop(l, 1)
    

  // exercise 3.3
  def setHead[A](l: List[A], newVal: A): List[A] = l match {
      case Cons(head, tail) => Cons(newVal, tail)
      case _ => Nil
    }

  // exercise 3.4
  def drop[A](l: List[A], n: Int): List[A] = 
      def iter(temp: List[A], i: Int): List[A] = 
        if i < n then
          val result: List[A] = temp match {
            case Cons(head, tail) => tail
            case _ => Nil
          }
          iter(result, i + 1)
        else
          temp

      iter(l, 0)

  // exercise 3.5
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = 
    def iter(temp: List[A]): List[A] = 
      val result: (List[A], Boolean) = temp match {
          case Cons(head, tail) => (tail, f(head))
          case _ => (Nil, false)
        }
      
      if result(1) then iter(result(0))
      else temp

    iter(l)

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h,t) => Cons(h, append(t, a2))
    }

  // exercise 3.6
  def init[A](l: List[A]): List[A] = 
    def iter(acc: List[A], tail: List[A]): List[A] = 
      tail match {
        case Cons(h, Nil) => acc
        case Cons(h, t) => iter(append(acc, Cons(h, Nil)), t)
        case Nil => Nil
      }

    iter(Nil, l)
}

// test run
  
import List.sum


// val x = List(1,2,3,4,5) match {
//   case Cons(x, Cons(2, Cons(4, _))) => x
//   case Nil => 42
//   case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
//   case Cons(h, t) => h + sum(t)
//   case _ => 101
// }

val myList: List[Int] = Cons(1, Cons(2, Cons(3, Nil)))

List.tail(myList)

List.setHead(myList, 6)

List.drop(myList, 2)

List.dropWhile(myList, n => n < 5)

List.init(myList)

val xs: List[Int] = List(1,2,3,4,5)
val ex1 = List.dropWhile(xs, x => x < 4)