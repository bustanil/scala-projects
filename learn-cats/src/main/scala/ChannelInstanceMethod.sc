trait ByteEncoder[A] {
  def encode(a: A): Array[Byte]
}

object ByteEncoder {
  implicit val stringEncoder: ByteEncoder[String] = instance[String](_.getBytes)

  def instance[A](f: A => Array[Byte]): ByteEncoder[A] =
    new ByteEncoder[A] {
      override def encode(a: A):Array[Byte] = f(a)
    }
}

implicit val anotherStringEncoder : ByteEncoder[String] = ByteEncoder.instance[String]("test".getBytes ++  _.getBytes)

