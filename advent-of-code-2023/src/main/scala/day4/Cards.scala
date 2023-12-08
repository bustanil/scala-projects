package day4

import scala.io.Source

object Cards {
    
    def main(args: Array[String]): Unit =
        val regex = "^Card\\s+\\d+:([\\s\\d+]+)\\|([\\s\\d+]+)$".r
        val test = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 5 3"

        val content = Source.fromFile("/Users/bustanil.arifin/workspace/personal/scala-projects/advent-of-code-2023/src/main/scala/day4/input.txt")

        val part1 = content.getLines()
        .tapEach(println)
        .map(line => regex.findAllMatchIn(line).next())        
        .map(m => m.group(1).trim().split("\\s+").intersect(m.group(2).trim().split("\\s+")).length match {
            case 0 => 0
            case length => Math.pow(2, length - 1).toInt
        })
        .sum

        println(part1)

}
