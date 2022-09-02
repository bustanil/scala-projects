package com.bustanil.scala

import cats.effect.{IO, IOApp}

object SideEffects extends IOApp.Simple {

  def run = {
    IO(System.out.println("Hello side effect!")) >> IO(System.out.println("Hello world"))
  }
}
