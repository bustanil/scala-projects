trait MList[+A]

case class MCons[+A](hd: A, tl: MList[A]) extends MList[A]
case object MNil extends MList[Nothing]

def sum(ints: MList[Int]): Int = {
  ints match {
    case MCons(head, tail) => head + sum(tail)
    case MNil => 0
  }
}

def length[A](list: MList[A]): Int = {
  list match {
    case MCons(_, tl) => 1 + length(tl)
    case MNil => 0
  }
}

def filterPositive(ints: MList[Int]): MList[Int] = {
  ints match {
    case MCons(hd, tl) => if (hd > 0) MCons(hd, filterPositive(tl))
                          else filterPositive(tl)
    case MNil => MNil
  }
}

def foldRight[A, B](list: MList[A])(z: B)(f: (A, B) => B): B = {
  list match {
    case MNil => z
    case MCons(h, t) => f(h, foldRight(t)(z)(f))
  }
}

def sum2(ints: MList[Int]): Int = {
  foldRight(ints)(0)((a, b) => a + b)
}

def length2[A](list: MList[A]): Int = {
  foldRight(list)(0)((_, b) => 1 + b)
}

def filterPositive2(ints: MList[Int]): MList[Int] = {
  foldRight(ints)(MNil.asInstanceOf[MList[Int]])((a, b) => {
    if(a > 0) {
      MCons(a, b)
    } else {
      b
    }
  })
}

val myList: MList[Int] = MCons(1, MCons(2, MCons(10, MNil)))
sum(myList)

filterPositive2(MCons(1, MCons(2, MNil)))
