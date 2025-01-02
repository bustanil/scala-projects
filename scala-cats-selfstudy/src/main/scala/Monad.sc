import cats.Monad

val opt1: Option[Int] = Some(1)

def timesTwo(i: Int): Option[Int] = Some(i * 2)
def addsTen(i: Int): Option[Int] = Some(i + 10)

opt1.flatMap(timesTwo).flatMap(addsTen)