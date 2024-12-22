import java.util.Date
import chapter1.Printable
import chapter1.PrintableSyntax._
import cats._
import cats.implicits._
import cats.syntax._

final case class Cat(name: String, age: Int, color: String)

object Cat {
  given catPrintable: Printable[Cat] = new Printable[Cat] {
    def format(a: Cat): String = s"${a.name} is a ${a.age} year-old ${a.color} cat."
  }
}

@main def hello(): Unit =
  val cat = Cat("test", 10, "blue")
  Printable.print(cat)
  cat.print()
  val showInt = Show.apply[Int]
  println(showInt.show(100))
  println(100.show)
  given dateShow: Show[Date] = Show.show(date => s"${date.getTime()}ms since the epoch.")

  val date = new Date()
  println(date.show)

def msg = "I was compiled by Scala 3. :)"
