import cats.effect.{ExitCode, IO, IOApp}
import doobie.ConnectionIO
import doobie.implicits._
import doobie.util.transactor.Transactor

object App extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _       <- IO(println("Hello Doobie"))
      country <- find("Indonesia").transact(xa)
      _       <- country match {
                  case Country(code, _, population) => IO(println(s"Country Code $code, Country population $population"))
                }
    } yield ExitCode.Success
  }

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "postgres"
  )

  case class Country(code: String, name: String, population: Long)

  def find(n: String): ConnectionIO[Country] =

    sql"select code, name, population from country where name = $n".query[Country].unique
}
