import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using

trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

trait Channel {
  def write[A](obj: A, enc: ByteEncoder[A]): Unit
}

object FileChannel extends Channel {
  override def write[A](obj: A, enc: ByteEncoder[A]): Unit = {
    val bytes: Array[Byte] = enc.encode(obj)

    Using(new FileOutputStream("/Users/bustanil.arifin/workspace/scala-projects/learn-cats/test.typeclasses")) { os =>
      os.write(bytes)
      os.flush()
    }
  }
}

object IntEncoder extends ByteEncoder[Int] {
  override def encode(a: Int): Array[Byte] = {
    val bb: ByteBuffer = ByteBuffer.allocate(4)
    bb.putInt(a)
    bb.array()
  }
}

object StringEncoder extends ByteEncoder[String] {

  override def encode(a: String): Array[Byte] = {
    a.getBytes
  }
}

FileChannel.write(5, IntEncoder)
FileChannel.write("hello typeclasses", StringEncoder)
