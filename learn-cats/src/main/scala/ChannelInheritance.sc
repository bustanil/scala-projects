import java.io.FileOutputStream
import scala.util.Using

trait ByteEncodable {
  def encode(): Array[Byte]
}

trait Channel {
  def write(obj: ByteEncodable): Unit
}

case class FullName(firstName: String, lastName: String) extends ByteEncodable {
  override def encode() : Array[Byte] = {
    firstName.getBytes ++ lastName.getBytes
  }
}

object FileChannel extends Channel {
  override def write(obj: ByteEncodable): Unit = {
    val bytes: Array[Byte] = obj.encode()

    Using(new FileOutputStream("/Users/bustanil.arifin/workspace/scala-projects/learn-cats/test.inheritance")) { os =>
      os.write(bytes)
      os.flush()
    }
  }

}


FileChannel.write(FullName("Bustanil", "Arifin"))
