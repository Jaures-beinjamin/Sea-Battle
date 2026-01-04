package domain

/**
 * État d'une case de la grille
 */
sealed trait CellState {
  def isEmpty: Boolean = false
  def isHit: Boolean = false
  def isMiss: Boolean = false
  def isSunk: Boolean = false
}

object CellState {

  /** Case vide (eau) */
  case object Empty extends CellState {
    override def isEmpty: Boolean = true
  }

  /** Case touchée (navire touché) */
  case object Hit extends CellState {
    override def isHit: Boolean = true
  }

  /** Case manquée (tir dans l'eau) */
  case object Miss extends CellState {
    override def isMiss: Boolean = true
  }

  /** Case d'un navire coulé */
  case object Sunk extends CellState {
    override def isSunk: Boolean = true
  }
}

