import cats._
import cats.implicits._

case class Account(id: Long, number: String, balance: Double, owner: String)

object Account {
  implicit val orderById: Order[Account] = Order.from((a1, a2) => Order[Long].compare(a1.id, a2.id))
  object Instances {
    implicit val orderByNumber: Order[Account] = Order.by(_.number)
    // exercise, provide an instance of Order[Account] that orders by balance
    implicit  val orderByBalance: Order[Account] = Order.by(_.balance)
  }
}

def sort[A](list: List[A])(implicit orderA: Order[A]): List[A] = {
  list.sorted(orderA.toOrdering)
}

val account1 = Account(1, "12345", 1000, "Bustanil")
val account2 = Account(2, "12345", 200, "Arifin")

//import Account.Instances.orderByBalance
//sort[Account](List(account1, account2))
//
//account1 compare account2
//account1 min account2
//account1 max account2

implicit val orderByIdDesc: Order[Account] = Order.reverse(Account.orderById)
sort[Account](List(account1, account2))