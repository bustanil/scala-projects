package hello

@main def main (args: String*): Unit =
  println(s"Hello ${args.mkString}")