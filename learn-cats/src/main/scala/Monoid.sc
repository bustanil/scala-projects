import cats._
import cats.implicits._

case class Speed(metersPerSecond: Double) {
  def kilometersPerSec: Double = metersPerSecond / 1000.0
  def milesPerSec: Double = metersPerSecond / 1609.34
}

object Speed {
  def addSpeeds(s1: Speed, s2: Speed): Speed = {
    Speed(s1.metersPerSecond + s2.metersPerSecond)
  }

  implicit val eqSpeed: Eq[Speed] = Eq.fromUniversalEquals
  implicit val monoidSpeed: Monoid[Speed] = Monoid.instance(Speed(0), addSpeeds)
}

val s1 = new Speed(100)
val s2 = new Speed(200)
Monoid[Speed].combine(s2, s2)
Monoid[Speed].empty

s1 |+| s2

Monoid[Speed].combineAll(List(s1, s2))

List(s1, s2, s1).combineAll
Monoid[Speed].isEmpty(s1)

// exercise
val sumMonoid: Monoid[Int] = Monoid.instance[Int](0, _ + _)
val minMonoid: Monoid[Int] = Monoid.instance[Int](Int.MaxValue, (i1, i2) => i1 min i2)
def listMonoid[A]: Monoid[List[A]] = Monoid.instance[List[A]](Nil, (a1, a2) => a1 ++ a2)
def stringMonoid: Monoid[String] = Monoid.instance("", _ + _)

sumMonoid.combine(1, 100)
minMonoid.combine(100, 200)
listMonoid[Int].combine(List(1), List(2, 3))
stringMonoid.combine("Hello ", "world")