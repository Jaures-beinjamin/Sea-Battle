package util

import domain.{Player, Position, Ship}

object HitDetection {

  /** Vérifie si un navire est touché et met à jour l’état des navires */
  def applyHit(defender: Player, position: Position): Player = {

    // Mets à jour tous les navires
    val updatedShips = defender.ships.map { ship =>
      ship.hit(position)
    }

    // Retourne un nouveau Player avec les navires mis à jour
    defender.copy(ships = updatedShips)
  }

  /** Retourne le navire touché (Option) */
  def shipHit(defender: Player, position: Position): Option[Ship] =
    defender.ships.find(_.occupies(position))
}
