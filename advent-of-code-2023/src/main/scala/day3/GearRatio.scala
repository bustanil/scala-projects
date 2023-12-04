package day3

import scala.io.Source

object GearRatio {

  type Number = Int
  type SymbolMap = Map[Int, List[Int]]
  type NumberMap = Map[Int, List[(Number, Int, Int)]]

  def main(args: Array[String]): Unit = {
    val content = Source.fromFile(args(0)).mkString

    val numberPattern = "\\d+".r
    val symbolPattern = "([^\\d\\.\\s])".r // not digit or dot or whitespace

    val length = content.indexOf("\n") + 1

    given symbolMap: SymbolMap = symbolPattern
      .findAllMatchIn(content)
      .foldLeft[SymbolMap](Map.empty)((acc, m) => {
        val key = m.start / length
        val newValue = acc.get(key) match
          case None        => List(m.start % length)
          case Some(value) => value :+ m.start % length
        acc.updated(key, newValue)
      })

    val sum = numberPattern
      .findAllMatchIn(content)
      .foldLeft[Int](0)((acc, m) => {
        val y = m.start / length
        val number = m.group(0).toInt
        val x1 = m.start % length
        val x2 = m.end % length - 1
        if (adjacentSymbol(y, x1, x2)) (acc + number) else acc
      })

    println(sum)
  }

  def adjacentSymbol(y: Int, x1: Int, x2: Int)(using symbolMap: SymbolMap): Boolean =
    symbolMap.exists((sy, sxs) =>
      sy >= y - 1 && sy <= y + 1 && (sxs.exists(sx =>
        sx >= x1 - 1 && sx <= x2 + 1
      ))
    )
}
