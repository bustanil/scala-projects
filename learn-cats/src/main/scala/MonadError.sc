import cats._
import cats.implicits._

import java.io.IOException
import scala.util.{Failure, Success, Try}

trait HttpMethod

case object GET extends HttpMethod
case class HttpRequest(method: HttpMethod, url: String)
case class HttpResponse(status: Int)

def doRequest(request: HttpRequest): HttpResponse = {
  if(math.random() < 0.5) throw new IOException("boom!")
  else HttpResponse(200)
}

def executeRequest(request: HttpRequest): Option[HttpResponse] = {
  try {
    Some(doRequest(request))
  }
  catch {
    case _: Exception => None
  }
}
def executeRequest2(request: HttpRequest): Either[String, HttpResponse] = {
  try {
    Right(doRequest(request))
  }
  catch {
    case _: Exception => Left("error!")
  }
}

def executeRequest3(request: HttpRequest): Try[HttpResponse] = {
  try {
    Success(doRequest(request))
  }
  catch {
    case e: Exception => Failure(e)
  }
}

val optionME: MonadError[Option, Unit] = new MonadError[Option, Unit] {
  override def pure[A](x: A) = Some(x)

  override def flatMap[A, B](fa: Option[A])(f: A => Option[B]) = fa.flatMap(f)

  override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]) = ???

  override def raiseError[A](e: Unit): Option[A] = None

  override def handleErrorWith[A](fa: Option[A])(f: Unit => Option[A]) = fa.orElse(f(()))
}

def executeRequestME[F[_], E](request: HttpRequest)(f: Exception => E)(implicit ME: MonadError[F, E]): F[HttpResponse] = {
  try {
    ME.pure(doRequest(request))
  } catch {
    case e: Exception => ME.raiseError(f(e))
  }
}

type ErrorOr[A] = Either[Throwable, A]
executeRequestME[Try, Throwable](HttpRequest(GET, "www.example.com"))(e => e)
executeRequestME[Option, Unit](HttpRequest(GET, "www.example.com"))(_ => ())
executeRequestME[ErrorOr, Throwable](HttpRequest(GET, "www.example.com"))(e => e)

type ErrorOr2[A] = Either[String, A]
executeRequestME[ErrorOr2, String](HttpRequest(GET, "www.example.com"))((e: Exception) => e.toString)

// additional functions
MonadError[Option, Unit].attempt(Some(5))
MonadError[Option, Unit].attempt(None)
MonadError[Try, Throwable].attempt(Success(5))
MonadError[Try, Throwable].attempt(Failure(new Exception("boom")))

MonadError[Option, Unit].ensure(Some(2))(())(_ % 2 == 0)