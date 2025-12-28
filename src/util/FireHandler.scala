package util

import model.{Player, Position, CellState, ShotResult}

object FireHandler {

  /** Détermine le résultat d’un tir sur une position donnée */
  private def determineShotResult(defenderShips: Set[model.Ship], position: Position): ShotResult = {
    if (defenderShips.exists(ship => ship.occupies(position) && ship.isSunk)) ShotResult.Sunk
    else if (defenderShips.exists(ship => ship.occupies(position))) ShotResult.Hit
    else ShotResult.Miss
  }

  /** Tire sur une case du joueur adverse et retourne le résultat */
  def fireAt(attacker: Player, defender: Player, position: Position): (Player, Player, ShotResult) = {

    // Vérifie si la case a déjà été tirée
    if (attacker.shots.contains(position))
      throw new IllegalArgumentException(s"Case $position déjà tirée")

    // Met à jour l'historique des tirs de l'attaquant
    val updatedAttacker = attacker.registerShot(position)

    // Met à jour les navires du défenseur
    val updatedDefenderShips = defender.ships.map(_.hit(position))

    // Met à jour la grille du défenseur
    val updatedGrid = updatedDefenderShips.foldLeft(defender.grid) { (grid, ship) =>
      if (ship.hits.contains(position)) grid.updateCell(position.x, position.y, CellState.Hit)
      else grid
    }

    val finalGrid =
      if (updatedGrid.cellAt(position.x, position.y).contains(CellState.Empty))
        updatedGrid.updateCell(position.x, position.y, CellState.Miss)
      else updatedGrid

    val updatedDefender = defender.copy(ships = updatedDefenderShips, grid = finalGrid)

    // Détermine le résultat du tir
    val result = determineShotResult(updatedDefenderShips, position)

    (updatedAttacker, updated
