package day3

import scala.io.Source

object GearRatio {

  type SymbolMap = Map[Int, List[Int]]  // y => list of xs
  type NumberMap = Map[Int, List[(Int, Int, Int)]] // y => List of value, x1, x2

  def main(args: Array[String]): Unit = {
    val content = Source.fromFile(args(0)).mkString

    val numberPattern = "\\d+".r
    val symbolPattern = "([^\\d\\.\\s])".r // not digit or dot or whitespace

    // length to help determine the actual coordinate
    // because we're using one dimensional array (string)
    val length = content.indexOf("\n") + 1 

    // create symbol map to be used later when deciding number adjacency
    val symbolMap = symbolPattern.findAllMatchIn(content)
      .foldLeft[SymbolMap](Map.empty)((acc, m) => {
        val key = m.start / length
        val newValue = acc.get(key) match
          case None        => List(m.start % length)
          case Some(value) => value :+ m.start % length

        acc.updated(key, newValue)
      })

    val sum = numberPattern.findAllMatchIn(content)
      .foldLeft[Int](0)((acc, m) => {
        val y = m.start / length
        val number = m.group(0).toInt
        val x1 = m.start % length
        val x2 = m.end % length - 1
        if (adjacentSymbol(y, x1, x2)(symbolMap)) (acc + number) else acc
      })

    println(sum)
  }

  // determine whether a number is adjacent to any symbols according
  // to the symbol map
  def adjacentSymbol(y: Int, x1: Int, x2: Int)(symbolMap: SymbolMap): Boolean =
    symbolMap.exists((sy, sxs) =>
      sy >= y - 1 && sy <= y + 1 && (sxs.exists(sx =>
        sx >= x1 - 1 && sx <= x2 + 1
      ))
    )
}
