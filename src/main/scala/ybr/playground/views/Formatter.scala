package ybr.playground.views

trait Formatter[A] {
  def apply(a: A): String
}
