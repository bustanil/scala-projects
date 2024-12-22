package chapter1

trait Printable[A] {
    def format(a: A): String
}

object PrintableInstances {
    given stringPrintable: Printable[String] = new Printable[String] {
        def format(input: String): String = input
    }
    given intPrintable: Printable[Int] = new Printable[Int] {
        def format(input: Int): String = input.toString() 
    }
}

object PrintableSyntax {
    extension [A](a: A)(using p: Printable[A])
        def format(): String = Printable.format(a)
        def print() = Printable.print(a)
}

object Printable {
    def format[A](a: A)(using printable: Printable[A]) = printable.format(a)
    def print[A](a: A)(using printable: Printable[A]) = println(printable.format(a))
}