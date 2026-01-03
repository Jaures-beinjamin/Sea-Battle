package domain

sealed trait Orientation
object Orientation {
  case object Horizontal extends Orientation
  case object Vertical extends Orientation
}
