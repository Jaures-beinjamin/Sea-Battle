package util

import domain.{Player, Position, Ship}

object ShipSunkDetection {

  /** Vérifie si un navire est coulé */
  def isSunk(ship: Ship): Boolean =
    ship.isSunk

  /** Retourne tous les navires coulés d’un joueur */
  def sunkShips(player: Player): Set[Ship] =
    player.ships.filter(_.isSunk)

  /** Vérifie si un tir coule un navire et retourne le navire s’il est coulé */
  def checkShipSunk(player: Player, position: Position): Option[Ship] =
    player.ships.find(ship => ship.occupies(position) && ship.isSunk)
}
