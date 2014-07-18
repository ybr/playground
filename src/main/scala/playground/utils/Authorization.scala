package playground.utils

import org.apache.commons.codec.binary.Base64

import play.api.mvc._

import playground.models._

object Authorization {
  def basicAuth(request: RequestHeader): Option[(String, Password)] = request.headers.get("Authorization") flatMap { authorization =>
    val basic = "Basic (.*)".r
    authorization match {
      case basic(base64) => new String(Base64.decodeBase64(base64.getBytes), "UTF-8").split(":").toList match {
        case login::password::_ => Some(login -> Password(password))
        case _ => None
      }
      case _ => None
    }
  }
}
