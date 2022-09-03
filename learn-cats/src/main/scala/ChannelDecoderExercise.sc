import scala.util.Try

trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

trait ByteDecoder[A] {
  def decode(bytes: Array[Byte]): Option[A]
}

object ByteDecoder {
  implicit val stringDecoder: ByteDecoder[String] = instance(bytes => Try(new String(bytes)).toOption)

  def apply[A](implicit dec: ByteDecoder[A]): ByteDecoder[A] = dec
  def instance[A](f: Array[Byte] => Option[A]): ByteDecoder[A] = new ByteDecoder[A] {
    override def decode(bytes: Array[Byte]): Option[A] = f(bytes)
  }
}

trait Channel {
  def write[A](obj: A)(implicit enc: ByteEncoder[A]): Unit
  def read[A](implicit dec: ByteDecoder[A]): A
}

ByteDecoder[String].decode(Array(98, 105, 101, 110, 32, 58, 41))
