package day1

import scala.io.Source

object Trebuchet {

  def main(args: Array[String]): Unit = {
    val result = Source.fromFile("/home/bustanil/aoc2023/day1/part1/input.txt", "utf-8")
      .getLines()
      .map(calibrationValue)
      .sum

    println(s"Calibration value: $result")
  }

  def calibrationValue(s: String): Int =
    // collect all digits into a List of Int
    val digits: List[Int] = 
      s.foldLeft(List.empty)((acc, c) => if (c.isDigit) acc :+ c.asDigit else acc)

    digits match {
      case n :: Nil => n * 10 + n // match singleton List
      case n +: _ :+ m => n * 10 + m // match first and last element
      case _ => 0
    }
}
