import scala.annotation.tailrec
// exercise 2.1

def fib(n: Int): Int = {
  @tailrec
  def go(n: Int, a: Int, b: Int): Int = {
    if (n == 1) {
      a
    } else if (n == 2){
      b
    } else {
      go(n - 1, b, a + b)
    }
  }

  go(n, 0, 1)
}

print(fib(5))

// exercise 2.2

def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
  @tailrec
  def loop(i: Int): Boolean = {
    if(as.length == 1 || i >= as.length){
      true
    } else if (!ordered(as(i - 1), as(i))){
      false
    } else {
      loop(i + 1)
    }
  }

  loop(1)
}

print(isSorted(Array(1, 2, 12, 1, 12), (n1: Int, n2: Int) => n1 <= n2))

def partial1[A,B,C](a: A, f: (A,B) => C): B => C =
  (b: B) => f(a, b)

// Exercise 2.3
def curry[A,B,C](f: (A, B) => C): A => (B => C) =
  a => partial1(a, f)

val testcurry = curry((a: Int, b: Int) => a + b)(2)

testcurry(2)

val either = Left("lalala")

either.flatMap {
  case "haha" => Right("hoho")
  case "test" => Right("hihi")
  case _ => Left("error")
}

either.map((str: String) => str + "lalala")

