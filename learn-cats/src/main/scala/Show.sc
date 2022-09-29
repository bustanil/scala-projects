import cats._
import cats.implicits._

case class Account(id: Long, number: String, balance: Double, owner: String)

object Account {
  implicit val toStringShow: Show[Account] = Show.fromToString

  object Instances {
    implicit val byOwnerAndBalance: Show[Account] = Show.show(account => s"${account.owner} - ${account.balance}")

    // exercise write an instance of show which will output something like 'this account belongs to Leandro'
    implicit val prettyByOwner: Show[Account] = Show.show(account => s"This account belongs to ${account.owner}")
  }
}

val account1 = Account(1, "12345", 1000, "Bustanil")

Account.toStringShow.show(account1)

Account.Instances.byOwnerAndBalance.show(account1)
Account.Instances.prettyByOwner.show(account1)

import Account.Instances.prettyByOwner
account1.show