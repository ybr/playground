package playground.models.exceptions

// * A coded exception has a machine usable code
trait CodedException { self: Exception =>
  def code(): String
  def message(): String = self.getMessage
}
