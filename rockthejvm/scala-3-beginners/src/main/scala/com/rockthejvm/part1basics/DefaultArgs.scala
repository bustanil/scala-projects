package com.rockthejvm.part1basics

object DefaultArgs {

  def sumUntilTailRec(x: Int, accumulator: Int = 0): Int =
    if (x <= 0) accumulator
    else sumUntilTailRec(x - 1, accumulator + x)

  val sumUntil100 = sumUntilTailRec(100)

  def savePicture(dirPath: String, name: String, format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit =
    println(s"Saving picture in format $format in path $dirPath")

  def main(args: Array[String]): Unit = {
    // default args value
    savePicture("/Users/bustanil/photos", "myphoto")

    // named args
    savePicture("/Users/bustanil/photos", "myphoto", width = 800, height = 600)
  }
  
}
