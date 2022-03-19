package recfun

import scala.annotation.tailrec

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if c == 0 || c == r then
      1
    else
      pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean =
    @tailrec
    def iter(openParens: List[Char], rest: List[Char]): Boolean =
      if rest.isEmpty then
        openParens.isEmpty
      else if rest.head == '(' then
        iter(rest.head +: openParens, rest.tail)
      else if rest.head == ')' && openParens.nonEmpty then
        iter(openParens.tail, rest.tail)
      else if rest.head == ')' && openParens.isEmpty then
        false
      else
        iter(openParens, rest.tail)

    iter(List(), chars)
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int =
    if(money == 0)
      1
    else if(money > 0 && coins.nonEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else
      0
