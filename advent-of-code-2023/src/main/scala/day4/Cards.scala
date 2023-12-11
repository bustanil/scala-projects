package day4

import scala.annotation.tailrec
import scala.io.Source
import scala.util.matching.Regex

object Cards {
    val regex: Regex = "^Card\\s+\\d+:([\\s\\d+]+)\\|([\\s\\d+]+)$".r

    def main(args: Array[String]): Unit = {
        val content = Source.fromFile("/Users/bustanil.arifin/workspace/personal/scala-projects/advent-of-code-2023/src/main/scala/day4/input.txt")
        val lines = content.getLines().toList

        val points = for {
            line <- lines
            m <- regex.findAllMatchIn(line)
        } yield matchingNumbers(m.group(1), m.group(2)).length match {
            case 0 => 0
            case count => Math.pow(2, count - 1).toInt
        }

        val part1 = points.sum
        println(part1)

        val part2 = calculateLines(lines)
        println(part2)
    }

    def matchingNumbers(s1: String, s2: String): Array[String] =
      s1.trim().split("\\s+").intersect(s2.trim().split("\\s+"))

    extension (m: Map[Int, Int])
      def merge(another: Map[Int, Int]): Map[Int, Int] =
        if (another.isEmpty) m
        else 
          m.updatedWith(another.head(0))(_.map(_ + another.head(1)))
            .merge(another.tail) // TODO convert to tailrec

    def calculateLines(lines: List[String]): Int = {
      @tailrec
      def calculateLinesTailRec(cardNumber: Int, lines: List[String], cards: Map[Int, Int]): Map[Int, Int] = {
        if (lines.isEmpty) cards // base line
        else {
            val line = lines.head
            val wins = regex.findFirstMatchIn(line) match
                case Some(m) => matchingNumbers(m.group(1), m.group(2)).length
            val copies = cards.getOrElse(cardNumber, 1)
            val updates = (cardNumber + 1).to(cardNumber + wins).map(_ -> copies).toMap
            calculateLinesTailRec(cardNumber + 1, lines.tail, cards.merge(updates))
        }
      }

      val cards  = 1.to(lines.length).map(_ -> 1).toMap
      calculateLinesTailRec(1, lines, cards).values.sum
    }

}