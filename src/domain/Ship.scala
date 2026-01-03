package model

final case class Ship(
                       positions: Set[Position],
                       hits: Set[Position] = Set.empty
                     ) {
  def size: Int = positions.size
  def occupies(position: Position): Boolean = positions.contains(position)
  def hit(position: Position): Ship = if (occupies(position)) copy(hits = hits + position) else this
  def isSunk: Boolean = hits.size == positions.size

  /** Méthode utilitaire pour construire un navire selon orientation */
  def positionsFrom(startX: Int, startY: Int, orientation: Orientation): Set[Position] = {
    orientation match {
      case Orientation.Horizontal => (0 until size).map(i => Position(startX + i, startY)).toSet
      case Orientation.Vertical   => (0 until size).map(i => Position(startX, startY + i)).toSet
    }
  }
}
