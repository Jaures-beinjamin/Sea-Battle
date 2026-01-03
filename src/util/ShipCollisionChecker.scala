package util

import domain.Ship

object ShipCollisionChecker {

  /** Vérifie si le nouveau navire chevauche un navire existant */
  def isCollision(newShip: Ship, existingShips: Set[Ship]): Boolean = {
    existingShips.exists { ship =>
      ship.positions.exists(pos => newShip.positions.contains(pos))
    }
  }

  /** Vérifie si toutes les positions sont libres pour un navire */
  def canPlaceShip(newShip: Ship, existingShips: Set[Ship]): Boolean =
    !isCollision(newShip, existingShips)
}
