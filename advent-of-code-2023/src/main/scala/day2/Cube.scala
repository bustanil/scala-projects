package day2

import scala.io.Source

object Cube {

  type Game = List[Map[String , Int]] 

  def main(args: Array[String]): Unit = {
    val config = Map(("red" -> 12), ("green" -> 13), ("blue" -> 14))

    val sum = Source.fromFile(args(0))
    .getLines()
    .map(parseLine)
    .map(maybeGame => maybeGame match
      case Some(gameTuple) if isPossible(gameTuple(1), config) => gameTuple(0)
      case _ => 0 
    )
    .sum

    println(sum)
  }

  def parseLine(s: String): Option[(Int, Game)] =
    val gamePattern = "Game (\\d+): (.+)".r
    gamePattern.findFirstMatchIn(s) match
      case None => None
      case Some(m) => Some((m.group(1).toInt -> parseGame(m.group(2))))

  def parseGame(s: String): Game =
    s.split(";").toList
      .map(set =>
        set.split(",")
          .flatMap(cubes => {
            val tokens = cubes.trim().split(" ")
            Map((tokens(1) -> tokens(0).toInt))
          })
          .toMap
      )

  def isPossible(game: Game, config: Map[String, Int]): Boolean =
    game.forall(cubes =>
      cubes.forall((color, count) => 
        config.get(color) match
          case Some(max) => (count <= max)
          case None      => false
      )
    )
}
