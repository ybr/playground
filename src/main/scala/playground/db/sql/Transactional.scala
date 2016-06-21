package playground.db.sql

import scala.concurrent._
import scala.collection.generic.CanBuildFrom

import java.sql.Connection

import play.api.Play.current
import play.api.db.DB
import play.api.libs.concurrent.Akka

import playground.log._

case class Tx[A](private val atomic: Connection => A) extends Logger { self =>
  def map[B](f: A => B): Tx[B] = Tx(f compose atomic)

  def flatMap[B](f: A => Tx[B]): Tx[B] = Tx(connection => f(atomic(connection)).atomic(connection))

  def zip[B](tx: Tx[B]): Tx[(A, B)] = flatMap { a =>
    tx.map { b =>
      (a, b)
    }
  }

  def withFilter(p: A ⇒ Boolean): Tx[A] = Tx { connection =>
    val a = atomic(connection)
    if (p(a)) a
    else throw new NoSuchElementException("Tx.filter predicate is not satisfied")
  }

  def commit(): Future[A] = {
    implicit val executionContext: ExecutionContext = Akka.system.dispatchers.lookup("transactional-context")

    val result = Future(DB.withTransaction(atomic))
    result.onFailure {
      case ex: Exception => log.error("Transaction failed", ex)
    }
    result
  }

  def mapError(f: Throwable ⇒ Throwable)(implicit executor: ExecutionContext) = new Tx[A](atomic) {
    override def commit(): Future[A] = self.commit.transform(identity, f)
  }
}

object Tx {
  import language.higherKinds

  def pure[A](a: => A) = Tx[A](_ => a)

  def sequence[A](in: Option[Tx[A]]): Tx[Option[A]] = in.map(_.map(Option.apply)).getOrElse(pure(None))

  def sequence[A, B, M[B] <: TraversableOnce[B]](in: M[Tx[A]])(implicit cbf: CanBuildFrom[M[Tx[A]], A, M[A]]) = Tx[M[A]] { connection =>
    in.foldLeft(cbf(in))((builder, tx) => builder += tx.atomic(connection)).result()
  }
}
