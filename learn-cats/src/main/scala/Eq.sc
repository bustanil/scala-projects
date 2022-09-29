import cats._
import cats.implicits._

case class Account(id: Long, number: String, balance: Double, owner: String)

object Account {
  implicit val universalEq: Eq[Account] = Eq.fromUniversalEquals // ==

  object Instances {
    implicit def byIdEq(implicit eqLong: Eq[Long]): Eq[Account] = Eq.instance[Account]((a1, a2) => Eq[Long].eqv(a1.id, a2.id))
    implicit def byIdEq2(implicit eqLong: Eq[Long]): Eq[Account] = Eq.by(a => a.id)
    implicit def byNumber(implicit eqString: Eq[String]): Eq[Account] = Eq.by(_.number)
  }

}

val account1 = Account(1, "12345", 100, "Bustanil")
val account2 = Account(2, "12345", 200, "Arifin")
Eq[Account].eqv(account1, account2)
Account.Instances.byIdEq.eqv(account1, account2)
Account.Instances.byNumber.eqv(account1, account2)

//import Account.Instances.byNumber
//account1 === account2

implicit val eqToUse = Account.Instances.byNumber
account1 === account2