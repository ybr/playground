package playground.models

case class Password(private val password: String) {
  def neverLog[A](f: String => A): A = f(password)

  // intended to be final so you can never log a Password by inadvertance but by passing by neverLog
  final override def toString() = "[PROTECTED]"
}
