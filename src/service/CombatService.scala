package service

import domain.{Player, Position, CellState}

/**
 * Résultat d'un tir
 */
sealed trait ShotResult

object ShotResult {
  /** Tir dans l'eau */
  case object Miss extends ShotResult

  /** Navire touché */
  case object Hit extends ShotResult

  /** Navire coulé */
  case object Sunk extends ShotResult
}

/**
 * Service de gestion des combats
 * Gère les tirs, les touches et la détection des navires coulés
 */
object CombatService {

  /**
   * Effectue un tir sur la grille d'un adversaire
   * @param attacker le joueur qui tire
   * @param defender le joueur qui subit le tir
   * @param position la position visée
   * @return (attaquant mis à jour, défenseur mis à jour, résultat du tir)
   */
  def fireAt(
              attacker: Player,
              defender: Player,
              position: Position
            ): (Player, Player, ShotResult) = {

    // Vérifie si le tir a déjà été effectué
    if (attacker.shots.contains(position)) {
      throw new IllegalArgumentException(
        s"La position $position a déjà été visée !"
      )
    }

    // Enregistre le tir pour l'attaquant
    val updatedAttacker = attacker.registerShot(position)

    // Vérifie si un navire est touché
    val hitShip = defender.shipAt(position)

    hitShip match {
      case Some(ship) =>
        // Navire touché !
        handleHit(updatedAttacker, defender, position, ship)

      case None =>
        // Tir dans l'eau
        handleMiss(updatedAttacker, defender, position)
    }
  }

  /**
   * Gère le cas où un navire est touché
   */
  private def handleHit(
                         attacker: Player,
                         defender: Player,
                         position: Position,
                         hitShip: domain.Ship
                       ): (Player, Player, ShotResult) = {

    // Met à jour tous les navires du défenseur
    val updatedShips = defender.ships.map { ship =>
      if (ship == hitShip) ship.hit(position) else ship
    }

    // Vérifie si le navire est coulé
    val updatedHitShip = updatedShips.find(_.positions == hitShip.positions).get
    val isSunk = updatedHitShip.isSunk

    // Met à jour la grille du défenseur
    val updatedGrid = if (isSunk) {
      // Si le navire est coulé, marque toutes ses cases comme "Sunk"
      updatedHitShip.positions.foldLeft(defender.grid) { (grid, pos) =>
        grid.updateCell(pos.x, pos.y, CellState.Sunk)
      }
    } else {
      // Sinon, marque uniquement la case touchée comme "Hit"
      defender.grid.updateCell(position.x, position.y, CellState.Hit)
    }

    val updatedDefender = defender.copy(
      ships = updatedShips,
      grid = updatedGrid
    )

    val result = if (isSunk) ShotResult.Sunk else ShotResult.Hit

    (attacker, updatedDefender, result)
  }

  /**
   * Gère le cas où le tir rate (dans l'eau)
   */
  private def handleMiss(
                          attacker: Player,
                          defender: Player,
                          position: Position
                        ): (Player, Player, ShotResult) = {

    // Met à jour la grille du défenseur
    val updatedGrid = defender.grid.updateCell(position.x, position.y, CellState.Miss)
    val updatedDefender = defender.copy(grid = updatedGrid)

    (attacker, updatedDefender, ShotResult.Miss)
  }
}

