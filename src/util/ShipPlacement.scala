package util

import model.{Position, Orientation, GameConfig}

object ShipPlacement {

  /** Génère les positions possibles d’un navire */
  def generatePositions(startX: Int, startY: Int, size: Int, orientation: Orientation): Option[Set[Position]] = {
    val positions = orientation match {
      case Orientation.Horizontal => (0 until size).map(i => Position(startX + i, startY))
      case Orientation.Vertical   => (0 until size).map(i => Position(startX, startY + i))
    }

    if (positions.forall(p => p.x >= 0 && p.x < GameConfig.GRID_SIZE && p.y >= 0 && p.y < GameConfig.GRID_SIZE))
      Some(positions.toSet)
    else
      None
  }
}
