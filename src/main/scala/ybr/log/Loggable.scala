package ybr.log

trait Loggable {
  protected val log = play.api.Logger(this.getClass.getCanonicalName.replace("$", ""))
}