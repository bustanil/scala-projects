package item

import java.util.UUID

type ItemId = UUID
type SellerId = UUID

case class Item(
    id: ItemId,
    name: String,
    price: Int,
    sellerId: SellerId
)

def affordable(item: Item): Boolean =
  item.price < 500

trait Lift[F[_]]:
    extension [A, B](f: A => B)
        def lift: F[A] => F[B]
    extension [A](fa: F[A])
        def map[B](f: A => B): F[B] = f.lift.apply(fa)

def affordableF[F[_]: Lift](fitem: F[Item]): F[Boolean] =
    fitem.map(affordable)

// Lift is usually called Functor in cat theory
trait Functor[F[_]]:
    extension [A, B](f: A => B)
        def lift: F[A] => F[B]
    extension [A](fa: F[A])
        def map[B](f: A => B): F[B] = f.lift.apply(fa)

trait Apply[F[_]] extends Functor[F]:
    extension [A, B](ff: F[A => B])
        def ap: F[A] => F[B]
    extension [A, B, C](f: (A, B) => C)
        def lift2: (F[A], F[B]) => F[C] =
            Function.uncurried(
                f.curried.lift andThen (_.ap)
            )
    extension [A, B, C](fab: (F[A], F[B]))
        def map2(f: (A, B) => C): F[C] =
            f.lift2.apply(fab._1, fab._2)

trait Applicative[F[_]] extends Apply[F]:
    extension [A](a: A)
        def pure: F[A]

def sequence[F[_]: Applicative, A](fas: List[F[A]]): F[List[A]] =
    fas match
        case head :: tail => (head, sequence(tail)).map2(_ :: _)
        case Nil =>  Nil.pure
    

def cheapest(item1: Item, item2: Item): Item =
    if item1.price < item2.price then item1 else item2

def cheapestF[F[_]: Apply](fitem1: F[Item], fitem2: F[Item]): F[Item] =
    (fitem1, fitem2).map2(cheapest)

def totalCostF[F[_]: Applicative](fitems: List[F[Item]]): F[Int] =
    fitems match
        case head :: tail => (head, totalCostF(tail)).map2(_.price + _)
        case Nil => 0.pure
    