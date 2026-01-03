package service

import domain.{Ship, Position, Orientation}
import game.GameConfig
import scala.util.Random

/**
 * Service de gestion des navires
 * Gère le placement, la collision et la création de navires
 */
object ShipService {

  private val random = new Random()

  /**
   * Vérifie si deux navires entrent en collision
   * (occupent au moins une même case)
   */
  def hasCollision(ship1: Ship, ship2: Ship): Boolean =
    ship1.positions.exists(ship2.positions.contains)

  /**
   * Vérifie si un navire peut être placé sans collision
   * @param newShip le navire à placer
   * @param existingShips les navires déjà placés
   * @return true si le placement est possible
   */
  def canPlaceShip(newShip: Ship, existingShips: Set[Ship]): Boolean =
    !existingShips.exists(ship => hasCollision(newShip, ship))

  /**
   * Vérifie si toutes les positions d'un navire sont dans la grille
   */
  def isInBounds(ship: Ship): Boolean =
    ship.positions.forall { pos =>
      pos.x >= 0 && pos.x < GameConfig.GRID_SIZE &&
        pos.y >= 0 && pos.y < GameConfig.GRID_SIZE
    }

  /**
   * Génère une orientation aléatoire
   */
  private def randomOrientation(): Orientation =
    if (random.nextBoolean()) Orientation.Horizontal
    else Orientation.Vertical

  /**
   * Génère une position de départ aléatoire valide pour un navire
   */
  private def randomStartPosition(size: Int, orientation: Orientation): (Int, Int) = {
    orientation match {
      case Orientation.Horizontal =>
        val x = random.nextInt(GameConfig.GRID_SIZE - size + 1)
        val y = random.nextInt(GameConfig.GRID_SIZE)
        (x, y)
      case Orientation.Vertical =>
        val x = random.nextInt(GameConfig.GRID_SIZE)
        val y = random.nextInt(GameConfig.GRID_SIZE - size + 1)
        (x, y)
    }
  }

  /**
   * Place automatiquement un navire de manière aléatoire
   * @param size taille du navire
   * @param existingShips navires déjà placés
   * @return un nouveau navire placé sans collision
   */
  def placeShipRandomly(size: Int, existingShips: Set[Ship]): Ship = {
    var placed = false
    var ship: Ship = null

    // Essaie de placer le navire jusqu'à trouver une position valide
    while (!placed) {
      val orientation = randomOrientation()
      val (startX, startY) = randomStartPosition(size, orientation)
      val newShip = Ship.create(startX, startY, size, orientation)

      if (isInBounds(newShip) && canPlaceShip(newShip, existingShips)) {
        ship = newShip
        placed = true
      }
    }

    ship
  }

  /**
   * Place automatiquement plusieurs navires
   * @param shipSizes liste des tailles de navires à placer
   * @return ensemble de navires placés
   */
  def placeAllShipsRandomly(shipSizes: List[Int]): Set[Ship] = {
    shipSizes.foldLeft(Set.empty[Ship]) { (ships, size) =>
      val newShip = placeShipRandomly(size, ships)
      ships + newShip
    }
  }
}

