package day2

import scala.io.Source

object Cube {

  type Cubes = Map[String , Int]
  type Game = List[Cubes] 

  def main(args: Array[String]): Unit = {
    // part 1
    val config = Map(("red" -> 12), ("green" -> 13), ("blue" -> 14))

    val sum = Source.fromFile(args(0))
    .getLines()
    .map(parseLine)
    .map(maybeGame => maybeGame match
      case Some((id, game)) if isPossible(game, config) => id
      case _ => 0 
    )
    .sum

    println(sum)

    // part 2
    val power = Source.fromFile(args(0))
    .getLines()
    .map(parseLine)
    .map(maybeGame => maybeGame match
      case Some((id, game)) => powerOfMinimumCubes(game)
      case _ => 0 
    )
    .sum

    println(power)
  }

  extension (m: Cubes) {
    def combineWith(key: String)(f: (Int, Int) => Int)(that: Cubes) =
      m.updatedWith(key)(valueOpt => 
        (valueOpt, that.get(key)) match
          case (None, _) => None
          case (Some(value), None) => Some(value)
          case (Some(a), Some(b)) => Some(f(a, b))
        )
  }

  def powerOfMinimumCubes(game: Game): Int =
    val init = Map(("red" -> 0), ("blue" -> 0), ("green" -> 0))
    game.foldLeft(init)((acc, cubes) => {
      acc.combineWith("red")(Math.max)(cubes)
        .combineWith("blue")(Math.max)(cubes)
        .combineWith("green")(Math.max)(cubes)
    })
    .values
    .reduce((a, b) => a * b)

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

  def isPossible(game: Game, config: Cubes): Boolean =
    game.forall(cubes =>
      cubes.forall((color, count) => 
        config.get(color) match
          case Some(max) => (count <= max)
          case None      => false
      )
    )
}
