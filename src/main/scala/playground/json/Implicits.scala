package playground.json

import org.joda.time.DateTime

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data.{FormError => PFormError}
import play.api.data.validation.ValidationError

import playground.models.Id
import playground.models.Id._

object Implicits {
  implicit val formErrorWrites: Writes[PFormError] = (
    (__ \ "key").write[String] and
    (__ \ "message").write[String] and
    (__ \ "args").write[Seq[String]]
  )(unlift(PFormError.unapply _ andThen(_.map {
    case (key, message, args) => (key, message, args.map(_.toString))
  })))

  implicit val idFormat = Format[Id](
    (__).read[String].map(Id(_)),
    Writes[Id](id => JsString(id.value))
  )

  implicit val joDateTimeFormat = Format[DateTime](
    (__).read[Long].map(new DateTime(_)),
    Writes[DateTime] { dt =>
      Json.obj(
        "millis" -> dt.getMillis,
        "human" -> dt.toString()
      )
    }
  )

  implicit val validationErrorWrites = Writes[ValidationError] { error =>
    Json.obj(
      "message" -> error.message,
      "args" -> error.args.map(_.toString)
    )
  }
  implicit val jsErrorWrites = Writes[(JsPath, Seq[ValidationError])] {
    case (path, errors) => Json.obj(
      "path" -> JsString(path.toString),
      "errors" -> Json.toJson(errors)
    )
  }
}
