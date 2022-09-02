package com.bustanil.scala

import cats.effect.{IO, IOApp, Resource}

import java.io.{File, FileInputStream}
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object Main extends IOApp.Simple {

  def run = IO.println("test")

  def inputStream(f: File): Resource[IO, FileInputStream] =
    Resource.make {
      IO.blocking(new FileInputStream(f))                         // build
    } { inStream =>
      IO.blocking(inStream.close()).handleErrorWith(_ => IO.unit) // release
    }

  def outputStream(f: File): Resource[IO, FileOutputStream] =
    Resource.make {
      IO.blocking(new FileOutputStream(f))
    } { outStream => 
      IO.blocking(outStream.close()).handleErrorWith(_ => IO.unit) 
    }
    
  def ioStreams(in: File, out: File): Resource[IO, (InputStream, OutputStream)] =
    for {
      inStream <- inputStream(in)
      outStream <- outputStream(out)
    } yield (inStream, outStream)

  def copy(origin: File, destination: File): IO[Long] =
    ioStreams(origin, destination).use {
      case (in, out) => transfer(in, out) // destructuring syntax
    }

  def transfer(origin: InputStream, destination: OutputStream): IO[Long] = ???

}
