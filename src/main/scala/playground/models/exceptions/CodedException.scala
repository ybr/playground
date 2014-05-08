package playground.models.exceptions

trait CodedException { self: Exception =>
  def code: String
}
