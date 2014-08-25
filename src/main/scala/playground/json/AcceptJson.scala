package playground.json

import scala.concurrent._

import play.api.mvc._
import play.api.mvc.BodyParsers.parse
import play.api.libs.json._

import playground.log._
import playground.json.Implicits._
import playground.models._
import playground.models.exceptions._

object AcceptJson extends Results with Logger {
  def async[A](action: A => Future[SimpleResult])(implicit reader: Reads[A], request: Request[JsValue], ec: ExecutionContext) = Action.async(parse.json) { request =>
    reader.reads(request.body) match {
      case JsError(errors) => Future.successful(UnprocessableEntity(Json.toJson(errors)))
      case JsSuccess(value, _) => action(value) recover {
        case x: CodedException => {
          log.debug("Expected error", x)
          BadRequest(Json.toJson(ApiError(x)))
        }
        case x => {
          log.error("Unexpected error", x)
          InternalServerError(Json.toJson(ApiError("internal_server_error", "An unexpected error occured")))
        }
      }
    }
  } (request)

  def apply[A](action: A => SimpleResult)(implicit reader: Reads[A], request: Request[JsValue], ec: ExecutionContext) = async[A](a => Future(action(a)))
}
