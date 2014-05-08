package playground.json

import scala.concurrent._

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json._

import playground.log._
import playground.json.Implicits._
import playground.models.exceptions._

object AcceptJson extends Results with JsonWriteable with Logger {
  def async[A](action: A => Future[SimpleResult])(implicit reader: Reads[A], request: Request[JsValue], ec: ExecutionContext) = Action.async(parse.json) { request =>
    reader.reads(request.body) match {
      case JsError(errors) => Future.successful(UnprocessableEntity(errors))
      case JsSuccess(value, _) => action(value) recover {
        case x: CodedException => {
          log.debug("Expected error", x)
          BadRequest(Json.obj(
            "code" -> x.code,
            "message" -> x.getMessage
          ))
        }
        case x => {
          log.error("Unexpected error", x)
          InternalServerError(Json.obj("message" -> "An unexpected error occured"))
        }
      }
    }
  } (request)

  def apply[A](action: A => SimpleResult)(implicit reader: Reads[A], request: Request[JsValue], ec: ExecutionContext) = async[A](a => Future(action(a)))
}
