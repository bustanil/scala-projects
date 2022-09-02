import java.io.FileOutputStream
import scala.util.Using

trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

trait Channel {
  def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit
}

object FileChannel extends Channel {
  override def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit = {
    val bytes: Array[Byte] = enc.encode(obj)

    Using(new FileOutputStream("/Users/bustanil.arifin/workspace/scala-projects/learn-cats/test.switch")) { os =>
      os.write(bytes)
      os.flush()
    }
  }
}

case class Switch(isOn: Boolean)

// the companion object of our custom type is often a good place
// to place the implicit object
object Switch {
  implicit object SwitchByteEncoder extends ByteEncoder[Switch] {
    override def encode(a: Switch): Array[Byte] =
      Array(if(a.isOn) '1'.toByte else '0'.toByte)
  }
}


FileChannel.write(Switch(true))