package model

sealed trait CellState {
  def isEmpty: Boolean = false
  def isHit: Boolean = false
  def isMiss: Boolean = false
}

object CellState {

  case object Empty extends CellState {
    override def isEmpty: Boolean = true
  }

  case object Hit extends CellState {
    override def isHit: Boolean = true
  }

  case object Miss extends CellState {
    override def isMiss: Boolean = true
  }
}
