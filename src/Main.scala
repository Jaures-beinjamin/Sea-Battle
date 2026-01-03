package main

import domain.{Player, Grid}
import service.ShipService
import game.{GameEngine, GameConfig}

/**
 * Point d'entrée du jeu de bataille navale
 */
object Main {

  def main(args: Array[String]): Unit = {

    // Création des joueurs avec grille vide
    val player1 = createPlayer("Alice")
    val player2 = createPlayer("Bob")

    // Lancement de la partie
    GameEngine.startGame(player1, player2)
  }

  /**
   * Crée un joueur avec ses navires placés automatiquement
   * @param name nom du joueur
   * @return joueur prêt à jouer
   */
  private def createPlayer(name: String): Player = {
    // Crée une grille vide
    val grid = Grid.empty

    // Place les navires automatiquement
    val ships = ShipService.placeAllShipsRandomly(GameConfig.SHIP_SIZES)

    // Retourne le joueur avec sa grille et ses navires
    Player(name, grid, ships)
  }
}
