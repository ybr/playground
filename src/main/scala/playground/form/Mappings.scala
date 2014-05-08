package playground.form

import play.api.data.Mapping

import playground.models.Password

object Mappings {
  implicit class PasswordMapping(mapping: Mapping[String]) {
    def password = mapping.transform[Password](Password.apply, _.neverLog(identity))
  }
}
