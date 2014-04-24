package ybr.playground.db.sql

import org.joda.time._

import anorm.SqlParser

object SqlParsers {
  object joda {
    def date(columnName: String) = SqlParser.date(columnName).map(new DateTime(_))
  }
}
