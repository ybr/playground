package playground.models

import exceptions._

/*
 * Inspired from OAuth2 errors specification http://tools.ietf.org/html/rfc6749#page-45
 */
trait ApiError {
  // A single ASCII error code
  def error: String

  /*
   * Human-readable ASCII [USASCII] text providing
   * additional information, used to assist the client developer in
   * understanding the error that occurred.
   */
  def description: Option[String]

  /*
   * A URI identifying a human-readable web page with
   * information about the error, used to provide the client
   * developer with additional information about the error.
   */
  def uri: Option[String]
}

object ApiError {
  def apply(x: CodedException): ApiError = new ApiError {
    val error = x.code
    val description = Some(x.message)
    val uri = None
  }

  def apply(code: String, message: String) = new ApiError {
    val error = code
    val description = Some(message)
    val uri = None
  }
}
