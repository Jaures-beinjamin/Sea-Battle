package util

import model.Player
import config.GameConfig

object BoardValidator {

  /** Vérifie qu’un joueur a un plateau valide */
  def validatePlayerBoard(player: Player, expectedShipCount: Int): Boolean = {
    val ships = player.ships

    // Vérifie le nombre de navires
    if (ships.size != expectedShipCount) return false

    // Vérifie les collisions entre tous les navires
    val collisionFree = ships.toList.combinations(2).forall {
      case List(ship1, ship2) =>
        !ship1.positions.exists(pos => ship2.positions.contains(pos))
      case _ => true
    }

    if (!collisionFree) return false

    // Vérifie que toutes les positions sont dans la grille
    val inBounds = ships.forall { ship =>
      ship.positions.forall(pos =>
        pos.x >= 0 && pos.x < GameConfig.GRID_SIZE &&
          pos.y >= 0 && pos.y < GameConfig.GRID_SIZE
      )
    }

    inBounds
  }
}
