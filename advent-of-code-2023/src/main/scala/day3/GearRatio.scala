package day3

import scala.io.Source

object GearRatio {

  type SymbolMap = Map[Int, List[Int]] // y => list of xs
  type NumberMap = Map[Int, List[(Int, Int, Int)]] // y => List of value, x1, x2

  def main(args: Array[String]): Unit = {
    val content = Source.fromFile(args(0)).mkString

    val numberPattern = "\\d+".r
    val symbolPattern = "([^\\d\\.\\s])".r // not digit or dot or whitespace

    // length to help determine the actual coordinate
    // because we're using one dimensional array (string)
    val length = content.indexOf("\n") + 1

    // create symbol map to be used later when deciding number adjacency
    val symbolMap = symbolPattern
      .findAllMatchIn(content)
      .foldLeft[SymbolMap](Map.empty)((acc, m) => {
        val key = m.start / length
        val newValue = acc.get(key) match
          case None        => List(m.start % length)
          case Some(value) => value :+ m.start % length

        acc.updated(key, newValue)
      })

    val numberMap = numberPattern
      .findAllMatchIn(content)
      .foldLeft[NumberMap](Map.empty)((acc, m) => {
        val key = m.start / length
        val newValue = acc.get(key) match
          case None =>
            List((m.group(0).toInt, m.start % length, m.end % length - 1))
          case Some(value) =>
            value :+ (m.group(0).toInt, m.start % length, m.end % length - 1)
        acc.updated(key, newValue)
      })

    val part1 = numberPattern
      .findAllMatchIn(content)
      .foldLeft[Int](0)((acc, m) => {
        val y = m.start / length
        val number = m.group(0).toInt
        val x1 = m.start % length
        val x2 = m.end % length - 1
        if (adjacentSymbol(y, x1, x2)(symbolMap)) (acc + number) else acc
      })

    println(part1)

    val part2 = symbolMap.toList
      .flatMap((row, cols) => cols.map(col => (row -> col)))
      .map { (r, c) =>
        adjacentNumbers(r, c)(numberMap) match
          case (a, _, _) :: (b, _, _) :: Nil => a * b
          case _                             => 0
      }
      .sum

    println(part2)
  }

  // determine whether a number is adjacent to any symbols according
  // to the symbol map
  def adjacentSymbol(r: Int, c1: Int, c2: Int)(symbolMap: SymbolMap): Boolean =
    symbolMap.exists((row, columns) =>
      row >= r - 1 && row <= r + 1 && (columns.exists(col =>
        col >= c1 - 1 && col <= c2 + 1
      ))
    )

  def adjacentNumbers(r: Int, c: Int)(numberMap: NumberMap): List[(Int, Int, Int)] =
    numberMap
      .filter { (row, _) => row >= r - 1 && row <= r + 1 }
      .map { (_, col) => col }
      .flatMap(_.filter { 
        case (_, start, finish) if (c >= start - 1 && c <= finish + 1) => true
        case _                                                         => false
      })
      .toList

}
