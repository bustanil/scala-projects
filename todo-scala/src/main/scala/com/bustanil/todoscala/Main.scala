package com.bustanil.todoscala

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple:
  val run = TodoscalaServer.run[IO]
