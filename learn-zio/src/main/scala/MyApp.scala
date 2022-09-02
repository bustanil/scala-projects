import zio.Console.{printLine, readLine}
import zio.{ZIO, ZIOAppDefault}

object MyApp extends ZIOAppDefault {

  def run = myAppLogic

  val myAppLogic = {
    for {
      _ <- printLine("Hello! What is your name?")
      name <- readLine
      _ <- printLine(s"Hello ${name}, welcome to ZIO!")
    } yield ()
  }
}
