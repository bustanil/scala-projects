package com.rockthejvm.part2oop

import scala.language.postfixOps

object MethodNotation {
  
    class Person(val name: String, val age: Int, favoriteMovie: String) {
        infix def likes(movie: String): Boolean =
            movie == favoriteMovie
        
        infix def +(person: Person): String = 
            s"${this.name} is hanging out with ${person.name}"

        infix def !!(progLanguage: String): String =
            s"$name wonders how can $progLanguage be so cool!"

        // prefix position
        // unary ops: -, +, ~, !
        def unary_- : String =
            s"$name's alter ego"

        def isAlive: Boolean = true

        // special method to call this method using instance() as if we're calling a function
        def apply(): String =
            s"Hi, my name is $name and I really enjoyed my favorite movie"

        // exercises
        infix def +(nickname: String): Person = new Person(s"$name $nickname", age, favoriteMovie)

        def unary_+ : Person = new Person(name, age + 1, favoriteMovie)

        def apply(n: Int): String = s"$name watched $favoriteMovie $n times"
    }

    val mary = new Person("Mary", 34, "Inception")
    val john = new Person("John", 36, "Fight Club")

    class Point(val x: Int, val y: Int) {
        infix def translate(p: Point): Point = 
            new Point(x + p.x, y + p.y)

        def show(): String = s"($x, $y)"
    }

    /**
     *
     * 
     */

    def main(args: Array[String]): Unit = {
        println(mary.likes("Fight Club"))

        // infix notation - for methods with ONE argument
        println(mary likes "Fight Club")

        // operator = plain method
        println(2 + 3)
        println(2.+(3))
        println(mary + john)
        println(mary !! "Scala")
        println(-mary)
        println(mary.isAlive)
        println(mary isAlive) // discouraged
        println(mary.apply())
        println(mary())

        // exercises
        val maryWithNickname = mary + "the rockstar"
        println(maryWithNickname.name)

        val maryOlder = +mary
        println(maryOlder.age)

        println(mary(3))

        val p1 = new Point(5, 10)
        val p2 = new Point(1, 5)

        val p3 = p1 translate p2

        println(p3.show())
    }
    
}
