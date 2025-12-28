package main

import model.{Player, Grid}
import util.AutoShipPlacer

object Main {
  def main(args: Array[String]): Unit = {

    // Création des joueurs avec grille vide
    val player1 = Player("Alice", Grid.empty)
    val player2 = Player("Bob", Grid.empty)

    // Placement automatique des navires
    val shipSizes = List(4, 3, 3, 2)
    val player1WithShips = AutoShipPlacer.placeShips(player1, shipSizes)
    val player2WithShips = AutoShipPlacer.placeShips(player2, shipSizes)

    // Lancement de la boucle principale du jeu
    GameLoop.startGame(player1WithShips, player2WithShips)
  }
}
