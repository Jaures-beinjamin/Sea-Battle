package util

import model.{Player, Position, CellState, Grid, Ship}

object FireHandler {

  /** Tire sur une case du joueur adverse */
  def fireAt(attacker: Player, defender: Player, position: Position): (Player, Player) = {

    // Vérifie si le joueur a déjà tiré sur cette position
    if (attacker.shots.contains(position)) {
      throw new IllegalArgumentException(s"Case $position déjà tirée")
    }

    // Enregistre le tir dans l'historique du joueur
    val updatedAttacker = attacker.registerShot(position)

    // Vérifie si un navire est touché
    val updatedShips = defender.ships.map { ship =>
      ship.hit(position)
    }

    // Met à jour la grille du défenseur
    val updatedGrid = updatedShips.foldLeft(defender.grid) { (grid, ship) =>
      if (ship.hits.contains(position)) grid.updateCell(position.x, position.y, CellState.Hit)
      else grid
    }

    // Marque miss si aucune hit
    val finalGrid =
      if (updatedGrid.cellAt(position.x, position.y).contains(CellState.Empty))
        updatedGrid.updateCell(position.x, position.y, CellState.Miss)
      else
        updatedGrid

    val updatedDefender = defender.copy(ships = updatedShips, grid = finalGrid)

    (updatedAttacker, updatedDefender)
  }
}
