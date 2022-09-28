import java.nio.ByteBuffer
import scala.util.Try

trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

object ByteEncoder {
  def apply[A](implicit ev: ByteEncoder[A]): ByteEncoder[A] = ev
}

implicit object StringByteEncoder extends ByteEncoder[String] {
  override def encode(a: String): Array[Byte] = a.getBytes
}

implicit object IntByteEncoder extends ByteEncoder[Int] {
  override def encode(a: Int): Array[Byte] = {
    val bb = ByteBuffer.allocate(4)
    bb.putInt(a)
    bb.array()
  }
}

//implicit object OptionStringByteEncoder extends ByteEncoder[Option[String]] {
//  override def encode(a: Option[String]): Array[Byte] = {
//    a match {
//      case Some(s) => ByteEncoder[String].encode(s)
//      case None => Array[Byte]()
//    }
//  }
//}

implicit def optionEncoder[A](implicit enc: ByteEncoder[A]): ByteEncoder[Option[A]] = new ByteEncoder[Option[A]] {
  override def encode(a: Option[A]) = {
    a match {
      case Some(value) => enc.encode(value)
      case None => Array[Byte]()
    }
  }
}
ByteEncoder[String].encode("hello")
ByteEncoder[Int].encode(1000)
ByteEncoder[Option[String]].encode(Some("hello"))
ByteEncoder[Option[String]].encode(None)