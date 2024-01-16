package com.rockthejvm.part3async

import scala.collection.mutable
import scala.util.Random


object JVMThreadCommunication {

  def main(args: Array[String]): Unit = {
    ProdConsV4.start(2, 2, 4)
  }

  /*
    large container
    producer -> [ _ _ _ _ ] ---> consumer
    producer --^          +---> consumer

    multi producer, multi consumer
   */
  object ProdConsV4 {
    class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
      override def run(): Unit = {
        val random = new Random(System.nanoTime())

        while (true) {
          buffer.synchronized {
            /*
              we need to constantly check if the buffer is empty - scenario 1:
              one producer, two consumers
              producer produces 1 value in the buffer
              both consumers are waiting
              producer calls notify, awaken one consumer
              consumer dequeues, calls notify, notify the other consumer
              the other consumer awakens, tries dequeuing, CRASH!
             */
            while (buffer.isEmpty) {
              println(s"[consumer $id] buffer empty, waiting...")
              buffer.wait()
            }

            // buffer non empty
            val newValue = buffer.dequeue()
            println(s"[consumer $id] consumed $newValue")

            // notify a producer
            /*
              scenario 2: (we need to use notifyAll() )
              two producer, one consumer, one capacity
              producer1 produces a value then waits
              producer2 sees buffer full, waits
              consumer consumes value, notify one producer (producer1)
              consumer sees buffer empty, wait
              producer1 produces a value, calls notify - signal goes to producer2
              producer1 sees buffer full, producer2 sees buffer full
              DEADLOCK! (all threads are in waiting state)
             */
            buffer.notifyAll() // signal all the waiting threads on the buffer
          }

          Thread.sleep(random.nextInt(500))
        }
      }
    }

    class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
      override def run(): Unit = {
        val random = new Random(System.nanoTime())
        var currentCount = 0

        while (true) {
          buffer.synchronized {
            while (buffer.size == capacity) {
              println(s"[producer $id] buffer full, waiting...")
              buffer.wait()
            }

            // buffer non empty
            println(s"[producer $id] producing $currentCount")
            buffer.enqueue(currentCount)

            // notify a consumer
            // TODO 2
            buffer.notifyAll()

            currentCount += 1
          }

          Thread.sleep(random.nextInt(500))
        }
      }
    }

    def start(nProducer: Int, nConsumer: Int, containerCapacity: Int): Unit = {
      val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
      val producers = (1 to nProducer).map(id => new Producer(id, buffer, containerCapacity))
      val consumers = (1 to nConsumer).map(id => new Consumer(id, buffer))
      producers.foreach(_.start())
      consumers.foreach(_.start())
    }
  }

}
