package day1

import scala.io.Source
import scala.quoted.runtime.Patterns
import scala.util.matching.Regex

object Trebuchet {

  def main(args: Array[String]): Unit = 
    val result = Source.fromFile(args(0), "utf-8")
      .getLines()
      .map(calibrationValue)
      .sum

    println(s"Calibration value: $result")

  def calibrationValue(s: String): Int =
    val digitPattern = "^(\\d{1}|one|two|three|four|five|six|seven|eight|nine)".r
    val words = List("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    
    def collectDigit(s: String, digits: List[Int]): List[Int] = 
      if (s.length() == 0) digits
      else
        digitPattern.findFirstIn(s) match
          case Some(token) if token.length() == 1 =>
            collectDigit(s.substring(1), digits :+ token.toInt)
          case Some(token) => collectDigit(s.substring(1), digits :+ words.indexOf(token) + 1)
          case None => collectDigit(s.substring(1), digits)

    collectDigit(s, List.empty) match 
      case n :: Nil    => n * 10 + n // match singleton List
      case n +: _ :+ m => n * 10 + m // match first and last element
      case _           => 0
}
