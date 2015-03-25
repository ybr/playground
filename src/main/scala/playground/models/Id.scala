package playground.models

import play.api.libs.json._

trait Id {
  def value: String
  override def toString = s"Id(${value})"
  override def hashCode: Int = value.hashCode
  override def equals(that: Any): Boolean = if(that.isInstanceOf[Id]) value.equals(that.asInstanceOf[Id].value) else false
}

trait IdProvider[T] {
  def toId(t: T): Id
}

object Id {
  def apply[T](t: T)(implicit provider: IdProvider[T]) = provider toId t
}
