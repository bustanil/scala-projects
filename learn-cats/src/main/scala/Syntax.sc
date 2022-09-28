import java.nio.ByteBuffer

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

implicit class ByteEncoderOps[A](a: A) {
  def encode(implicit enc: ByteEncoder[A]): Array[Byte] = enc.encode(a)
}

 5.encode
"hello".encode

trait ByteDecoder[A] {
  def decode(bytes: Array[Byte]): Option[A]
}

implicit object IntByteDecoder extends ByteDecoder[Int] {
  override def decode(bytes: Array[Byte]): Option[Int] = {
    if (bytes.length != 4) None
    else {
      val bb = ByteBuffer.allocate(4)
      bb.put(bytes)
      bb.flip
      Some(bb.getInt)
    }
  }
}

implicit class ByteDecoderOps[A](bytes: Array[Byte]) {
  def decode(implicit dec: ByteDecoder[A]): Option[A] =
    dec.decode(bytes)
}

val result: Option[Int] = 5.encode.decode
