package util

import model.{Player, Position, ShotResult, CellState}

object FireHandler {

  /** Tire sur une case et limite à un tir par tour */
  def fireAt(attacker: Player, defender: Player, position: Position): (Player, Player, ShotResult) = {

    // Vérifie si déjà tiré sur cette case
    if (attacker.shots.contains(position))
      throw new IllegalArgumentException(s"Case $position déjà tirée dans ce tour")

    // Met à jour l'historique des tirs de l'attaquant
    val updatedAttacker = attacker.registerShot(position)

    // Mise à jour des navires du défenseur
    val updatedDefenderShips = defender.ships.map(_.hit(position))

    // Mise à jour de la grille du défenseur
    val updatedGrid = updatedDefenderShips.foldLeft(defender.grid) { (grid, ship) =>
      if (ship.hits.contains(position)) grid.updateCell(position.x, position.y, CellState.Hit)
      else grid
    }

    val finalGrid =
      if (updatedGrid.cellAt(position.x, position.y).contains(CellState.Empty))
        updatedGrid.updateCell(position.x, position.y, CellState.Miss)
      else updatedGrid

    val updatedDefender = defender.copy(ships = updatedDefenderShips, grid = finalGrid)

    // Déterminer le résultat du tir
    val result =
      if (updatedDefenderShips.exists(ship => ship.occupies(position) && ship.isSunk)) ShotResult.Sunk
      else if (updatedDefenderShips.exists(ship => ship.occupies(position))) ShotResult.Hit
      else ShotResult.Miss

    (updatedAttacker, updatedDefender, result)
  }
}
