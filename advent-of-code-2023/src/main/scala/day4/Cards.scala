package day4

import scala.io.Source

object Cards {
    
    def main(args: Array[String]): Unit =
        val content = Source.fromFile(args(0))

        val regex = "^Card\\s+\\d+:([\\s\\d+]+)\\|([\\s\\d+]+)$".r

        val points = for {
            line <- content.getLines()
            m <- regex.findAllMatchIn(line)
        } yield m.group(1).trim().split("\\s+").intersect(m.group(2).trim().split("\\s+")).length match {
            case 0 => 0
            case length => Math.pow(2, length - 1).toInt
        }

        println(points.sum)
}
