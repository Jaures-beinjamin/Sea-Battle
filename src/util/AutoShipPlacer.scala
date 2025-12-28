package util

import model.{Player, Ship, Position, Orientation, GameConfig}
import scala.util.Random

object AutoShipPlacer {

  private val random = new Random()

  /** Retourne une orientation aléatoire */
  private def randomOrientation(): Orientation =
    if (random.nextBoolean()) Orientation.Horizontal else Orientation.Vertical

  /** Retourne un point de départ aléatoire pour un navire */
  private def randomStart(size: Int, orientation: Orientation): (Int, Int) = orientation match {
    case Orientation.Horizontal =>
      (random.nextInt(GameConfig.GRID_SIZE - size + 1), random.nextInt(GameConfig.GRID_SIZE))
    case Orientation.Vertical =>
      (random.nextInt(GameConfig.GRID_SIZE), random.nextInt(GameConfig.GRID_SIZE - size + 1))
  }

  /** Place automatiquement une liste de navires */
  def placeShips(player: Player, shipSizes: List[Int]): Player = {
    shipSizes.foldLeft(player) { (currentPlayer, size) =>
      var placed = false
      var newPlayer = currentPlayer

      while (!placed) {
        val orientation = randomOrientation()
        val (startX, startY) = randomStart(size, orientation)
        val maybePositions = ShipPlacement.generatePositions(startX, startY, size, orientation)

        maybePositions match {
          case Some(positions) =>
            val newShip = Ship(positions)
            if (ShipCollisionChecker.canPlaceShip(newShip, newPlayer.ships)) {
              newPlayer = newPlayer.addShip(newShip)
              placed = true
            }
          case None => // Génération invalide, réessayer
        }
      }

      newPlayer
    }
  }
}
