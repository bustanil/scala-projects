import cats._
import cats.implicits._


trait MList[+A]

object MList {
  def apply[A](elems: A*): MList[A] = {
    elems.foldRight(mnil[A])((a, b) => mcons(a, b))
  }


  case class MCons[+A](hd: A, tl: MList[A]) extends MList[A]
  case object MNil extends MList[Nothing]

  def mnil[A]: MList[A] = MNil
  def mcons[A](hd: A, tl: MList[A]): MList[A] = MCons(hd, tl)

  implicit val listFoldable: Foldable[MList] = new Foldable[MList] {
    override def foldLeft[A, B](fa: MList[A], b: B)(f: (B, A) => B): B = fa match {
      case MNil => b
      case MCons(h, t) => foldLeft(t, f(b, h))(f)
    }

    override def foldRight[A, B](fa: MList[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = {
      def loop(as: MList[A]): Eval[B] = {
        as match {
          case MNil => lb
          case MCons(h, t) => f(h, Eval.defer(loop(t)))
        }
      }
      Eval.defer(loop(fa))
    }
  }
}

import MList._

MList(1, 2, 3)

def sum(ints: MList[Int]): Int = {
  ints.foldLeft(0)((acc, a) => acc + a)
}

def length(ints: MList[Int]): Int = {
  ints.foldLeft(0)((acc, _) => acc + 1)
}

def filterPositive(ints: MList[Int]): MList[Int] = {
  ints.foldLeft(mnil[Int]) { (b, a) =>
    if(a > 0) MCons(a, b)
    else b
  }
}

sum(MList(2, 2, 2))
length(MList(1, 2, 3, 4))
filterPositive(MList(1, -2, 3, -4))

MList(1, 2, 3).foldMap(_.show)
MList(1, 2, 3).fold