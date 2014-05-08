package playground.views

import java.util.Date

import org.joda.time.DateTime

import playground.models._

/**
 * To make formatting available in templates of your project configure
 * your build.sbt or Build.scala with :
 *
 * settings(
 *   templatesImport += "playground.views.Formattable._"
 * )
 */
object Formattable {
  implicit class DateFormattable(d: Date) {
    def |(formatter: Formatter[Date]) = formatter(d)
  }

  implicit class DateTimeFormattable(d: DateTime) {
    def |(formatter: Formatter[Date]) = formatter(d.toDate)
  }

  implicit class StringFormattable(str: String) {
    def |(formatter: Formatter[String]) = formatter(str)
  }

  implicit class IntFormattable(i: Int) {
    def |(formatter: Formatter[Int]) = formatter(i)
  }

  implicit class LongFormattable(l: Long) {
    def |(formatter: Formatter[Long]) = formatter(l)
  }

  implicit class FloatFormattable(f: Float) {
    def |(formatter: Formatter[Float]) = formatter(f)
  }

  implicit class DoubleFormattable(d: Double) {
    def |(formatter: Formatter[Double]) = formatter(d)
  }

  implicit class BooleanFormattable(b: Boolean) {
    def |(formatter: Formatter[Boolean]) = formatter(b)
  }

  implicit class NameableFormattable(nameable: Nameable) {
    def |(formatter: Formatter[Nameable]) = formatter(nameable)
  }

  implicit class IdFormattable(id: Id) {
    def |(formatter: Formatter[Id]) = formatter(id)
  }
}
