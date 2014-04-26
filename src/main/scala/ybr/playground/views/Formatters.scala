package ybr.playground.views

import java.util._
import java.text._


import play.api.i18n._

import ybr.playground.models._

/**
 * To make formatting available in templates of your project configure
 * your build.sbt or Build.scala with :
 *
 * settings(
 *   templatesImport += "ybr.playground.views.Formatters._"
 * )
 */
object Formatters {
  object date {
    def apply(pattern: String)(implicit lang: Lang) = new DateFormatter {
      val formatter = new SimpleDateFormat(pattern, lang.toLocale)
    }

    def apply(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.date.custom")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateInstance(DateFormat.SHORT, lang.toLocale))
    }

    def short(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.date.short")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateInstance(DateFormat.SHORT, lang.toLocale))
    }

    def medium(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.date.medium")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateInstance(DateFormat.MEDIUM, lang.toLocale))
    }

    def long(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.date.long")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateInstance(DateFormat.LONG, lang.toLocale))
    }
  }

  object datetime {
    def apply(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.custom")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, lang.toLocale))
    }

    def short(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.short")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, lang.toLocale))
    }

    def medium(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.medium")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, lang.toLocale))
    }

    def long(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.long")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, lang.toLocale))
    }
  }

  object time {
    def apply(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.custom")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getTimeInstance(DateFormat.SHORT, lang.toLocale))
    }

    def short(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.short")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getTimeInstance(DateFormat.SHORT, lang.toLocale))
    }

    def medium(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.medium")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getTimeInstance(DateFormat.MEDIUM, lang.toLocale))
    }

    def long(implicit lang: Lang) = new DateFormatter {
      val formatter = message("formatter.datetime.long")
        .map(new SimpleDateFormat(_, lang.toLocale))
        .getOrElse(DateFormat.getTimeInstance(DateFormat.LONG, lang.toLocale))
    }
  }

  def bool(implicit lang: Lang) = new Formatter[Boolean] {
    def apply(b: Boolean) = (if(b) message("boolean.true") else message("boolean.false")).getOrElse(b.toString)
  }

  def fullname = new Formatter[Nameable] {
    def apply(n: Nameable) = message("nameable.fullname", n.firstName, n.lastName).getOrElse(n.firstName + " " + n.lastName)
  }

  protected trait DateFormatter extends Formatter[Date] {
    protected def formatter: DateFormat
    def apply(date: Date) = formatter.format(date)
  }

  protected def message(key: String)(implicit lang: Lang): Option[String] = {
    if(Messages.isDefinedAt(key)) Some(Messages(key))
    else None
  }
}
