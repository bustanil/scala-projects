package funsets

object Main extends App:
  import FunSets.*
//  println(contains(singletonSet(1), 1))

  val s = union(singletonSet(1), union(singletonSet(2), union(singletonSet(3), singletonSet(4))))

  print(forall(s, n => n < 5))
