import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import scala.util.Using

trait Channel {
  def write(obj: Any): Unit
}

object FileChannel extends Channel {
  override def write(obj: Any): Unit = {
    val bytes: Array[Byte] = obj match {
      case n: Int => {
        val bb = ByteBuffer.allocate(4)
        bb.putInt(n)
        bb.array()
      }
      case s: String => {
        s.getBytes("UTF-8")
      }
      case _ => throw new Exception("unhandled")
    }

    Using(new FileOutputStream("/Users/bustanil.arifin/workspace/scala-projects/learn-cats/test")) { os =>
      os.write(bytes)
      os.flush()
    }
  }
}


FileChannel.write("hello")
