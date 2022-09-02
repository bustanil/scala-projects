import java.io.FileOutputStream
import java.nio.ByteBuffer
import scala.util.Using

// this is a type class
trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

object ByteEncoder {
  // adhoc polymorphism, we define polymorphism by defining
  // implicit objects implementation within our scope
  implicit object StringEncoder extends ByteEncoder[String] {
    override def encode(a: String): Array[Byte] = {
      a.getBytes
    }
  }
}


trait Channel {
  def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit
}

object FileChannel extends Channel {
  override def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit = {
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

implicit object AnotherStringEncoder extends ByteEncoder[String] {
  override def encode(a: String): Array[Byte] = {
    "test".getBytes ++  a.getBytes
  }
}

// explicit
//FileChannel.write("hello world")(AnotherStringEncoder)

// implicitly use the closest instance in scope
FileChannel.write("hello world") // uses the default encoder
