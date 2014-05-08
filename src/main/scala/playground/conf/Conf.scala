package playground.conf

import play.api.Play._

object Conf {
  def apply(key: String) = configuration.getString(key).getOrElse(sys.error(s"Missing configuration [$key]"))
}
