

trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

object ByteEncoder {
  implicit object StringEncoder extends ByteEncoder[String] {
    override def encode(a: String): Array[Byte] = {
      a.getBytes
    }
  }

  def summon[A](implicit byteEncoder: ByteEncoder[A]): ByteEncoder[A] = byteEncoder
  def apply[A](implicit byteEncoder: ByteEncoder[A]): ByteEncoder[A] = byteEncoder
}

trait Channel {
  def write[A](obj: A, enc: ByteEncoder[A]): Unit
}

implicit object AnotherStringEncoder extends ByteEncoder[String] {
  override def encode(a: String): Array[Byte] = {
    "test".getBytes ++  a.getBytes
  }
}

// not very flexible, always use StringEncoder
ByteEncoder.StringEncoder.encode("wololo")
// use the current encoder in scope
implicitly[ByteEncoder[String]]
// this will summon AnotherStringEncoder
implicitly[ByteEncoder[String]].encode("haha")
// using the summon method
ByteEncoder.summon[String].encode("haha")
// one step further!
ByteEncoder[String].encode("wow!") // ByteEncoder.apply[String].encode("wow!")
