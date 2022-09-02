package com.bustanil.scala

import cats.effect.{IO, IOApp}
import scala.concurrent.duration._

object StupidFizzBuzz extends IOApp.Simple {

   def run =
    for {
      ctr <- IO.ref(0)

      wait = IO.sleep(1.second)
      poll = wait *> ctr.get

      _ <- poll.flatMap(i => IO.println(i)).foreverM.start
      _ <- poll.map(_ % 3 == 0).ifM(IO.println("fizz"), IO.unit).foreverM.start
      _ <- poll.map(_ % 5 == 0).ifM(IO.println("buzz"), IO.unit).foreverM.start

      _ <- (wait *> ctr.update(_ + 1).foreverM.void)
    } yield ()

}
