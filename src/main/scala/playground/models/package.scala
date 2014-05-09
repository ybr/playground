package playground

package object models {
  implicit val stringIdProvider = new IdProvider[String] {
    def toId(s: String) = new Id {
      val value = s
    }
  }
}
