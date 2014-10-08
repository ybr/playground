package playground.models

import play.api.libs.json._

trait Id {
  def value: String
  override def toString = s"Id(${value})"
  override def equals(that: Any): Boolean = that match {
    case Id(id) => this.value.equals(id)
    case _ => false
  }
  override def hashCode(): Int = this.value.hashCode
}

trait IdProvider[T] {
  def toId(t: T): Id
}

object Id {
  def apply[T](t: T)(implicit provider: IdProvider[T]) = provider toId t
  def unapply(id: Id): Option[String] = Some(id.value)
}
