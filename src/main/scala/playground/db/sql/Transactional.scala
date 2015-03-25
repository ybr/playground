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

  def withFilter(p: A ⇒ Boolean): Tx[A] = Tx { connection =>
    val a = atomic(connection)
    if(p(a)) a
    else throw new NoSuchElementException("Tx.filter predicate is not satisfied")
  }

  def zip[B](tx: Tx[B]): Tx[(A, B)] = flatMap { a =>
    tx.map { b =>
      (a, b)
    }
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

  def sequence[A](in: Option[Tx[A]]): Tx[Option[A]] = in.map(_.map(Some(_): Option[A])).getOrElse(pure(None))

  def sequence[A, F[X] <: TraversableOnce[X]](tas: F[Tx[A]])(implicit cbf: CanBuildFrom[F[Tx[A]], A, F[A]]): Tx[F[A]] = {
    tas.foldLeft(pure(cbf(tas))) { (tr, ta) =>
      for {
        r <- tr
        a <- ta
      } yield r += a
    } map (_.result())
  }
}
