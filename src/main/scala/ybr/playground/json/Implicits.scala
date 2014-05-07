package ybr.playground.json

import play.api.data.{FormError => PFormError}

import play.api.libs.json._
import play.api.libs.functional.syntax._

object Implicits {
  implicit val formErrorWrites: Writes[PFormError] = (
    (__ \ "key").write[String] and
    (__ \ "message").write[String] and
    (__ \ "args").write[Seq[String]]
  )(unlift(PFormError.unapply _ andThen(_.map {
    case (key, message, args) => (key, message, args.map(_.toString))
  })))
}
