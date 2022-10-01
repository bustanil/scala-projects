import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import cats._
import cats.implicits._

class Secret[A](val value: A) {
  private def hashed: String = {
    val s = value.toString
    val bytes = s.getBytes(StandardCharsets.UTF_8)
    val d = MessageDigest.getInstance("SHA-1")
    val hashBytes = d.digest(bytes)
    new String(hashBytes, StandardCharsets.UTF_8)
  }

  override def toString: String = hashed
}

object Secret {
  implicit val secretFunctor = new Functor[Secret] {
    override def map[A, B](fa: Secret[A])(f: A => B): Secret[B] =
      new Secret[B](f(fa.value))
  }
}

val bustanilSecret: Secret[String] = new Secret("Bustanil")
bustanilSecret.value

val uppercased = Functor[Secret].map(bustanilSecret)(s => s.toUpperCase())
uppercased.value

val optionFunctor: Functor[Option] = new Functor[Option] {
  override def map[A, B](fa: Option[A])(f: A => B) = {
    fa match {
      case None => None
      case Some(value) => Some(f(value))
    }
  }
}

val listFunctor: Functor[List] = new Functor[List] {
  override def map[A, B](fa: List[A])(f: A => B) = {
    fa match {
      case Nil => Nil
      case head :: tail => List(f(head)) ++ map(tail)(f)
    }
  }
}

listFunctor.map(List(1, 2, 3))(_ + 3)
optionFunctor.map(Some("Bustanil"))(name => s"Hello $name")