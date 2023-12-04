package day3

import scala.io.Source

object GearRatio { 

    val symbolMap = Map((1 -> 3))

    def main(args: Array[String]): Unit = {
        // Source.fromFile("/home/bustanil/workspace/scala-projects/advent-of-code-2023/src/main/scala/day3/test.txt")
        // val test = "467..114.."

        val numberPattern = "\\d+".r

        println(adjacentSymbol(0, 5, 7))
    }

    def adjacentSymbol(y: Int, x1: Int, x2: Int): Boolean =
        symbolMap.exists((sy, sx) => sy >= y-1 && sy <= y+1 && sx >= x1-1 && sx <= x2+1)
}
