package playground.json

import org.joda.time.DateTime

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data.FormError
import play.api.data.validation.ValidationError

import playground.models._

object Implicits {
  implicit val formErrorWrites = new Writes[FormError] {
    def writes(error: FormError) = Json.obj(
      "key" -> error.key,
      "message" -> error.message,
      "args" -> error.args.map(_.toString)
    )
  }

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

  implicit val apiErrorWrites = Writes[ApiError] { x =>
    Json.obj(
      "error" -> x.error,
      "error_description" -> x.description,
      "error_uri" -> x.uri
    )
  }
}
