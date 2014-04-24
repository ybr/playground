package ybr.playground.log

trait Logger {
  protected val log = play.api.Logger(this.getClass.getName.replaceAll("\\$", ""))
}
