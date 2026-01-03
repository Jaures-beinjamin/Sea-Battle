package service

import domain.Player
import game.GameConfig

/**
 * Service de validation
 * Vérifie la validité des plateaux et des configurations de jeu
 */
object ValidationService {

  /**
   * Vérifie qu'un joueur a un plateau valide
   * - Bon nombre de navires
   * - Pas de collision entre navires
   * - Tous les navires sont dans la grille
   */
  def validatePlayer(player: Player, expectedShipCount: Int): Boolean = {

    // Vérifie le nombre de navires
    if (player.ships.size != expectedShipCount) {
      println(s"⚠️  ${player.name} doit avoir $expectedShipCount navires (actuellement: ${player.ships.size})")
      return false
    }

    // Vérifie qu'il n'y a pas de collision entre navires
    val hasCollisions = player.ships.toList.combinations(2).exists {
      case List(ship1, ship2) => ShipService.hasCollision(ship1, ship2)
      case _ => false
    }

    if (hasCollisions) {
      println(s"⚠️  Les navires de ${player.name} se chevauchent !")
      return false
    }

    // Vérifie que tous les navires sont dans la grille
    val allInBounds = player.ships.forall(ShipService.isInBounds)

    if (!allInBounds) {
      println(s"⚠️  Certains navires de ${player.name} sont hors de la grille !")
      return false
    }

    true
  }

  /**
   * Vérifie qu'une position est valide (dans la grille)
   */
  def validatePosition(position: domain.Position): Boolean = {
    position.x >= 0 && position.x < GameConfig.GRID_SIZE &&
      position.y >= 0 && position.y < GameConfig.GRID_SIZE
  }
}