package main

import model.{GameState, Player, Position, ShotResult}
import util.{AutoShipPlacer, FireHandler, BoardValidator}

object GameLoop {

  def startGame(player1: Player, player2: Player): Unit = {
    var gameState = GameState(player1, player2)
    val expectedShipCount = player1.ships.size

    // Validation initiale des plateaux
    if (!BoardValidator.validatePlayerBoard(player1, expectedShipCount) ||
      !BoardValidator.validatePlayerBoard(player2, expectedShipCount)) {
      println("Erreur: plateaux invalides")
      return
    }

    // Boucle principale
    while (gameState.checkVictory.isEmpty) { // tant qu'aucun vainqueur

      println(s"Tour du joueur: ${gameState.currentPlayer.name}")

      // Lecture des coordonnées de tir
      println("Entrez les coordonnées x y: ")
      val input = scala.io.StdIn.readLine().split(" ")
      val position = Position(input(0).toInt, input(1).toInt)

      try {
        val (updatedAttacker, updatedDefender, result: ShotResult) =
          FireHandler.fireAt(gameState.currentPlayer, gameState.otherPlayer, position)

        println(s"Résultat du tir: $result")

        // Mettre à jour le GameState
        gameState = GameState(updatedAttacker, updatedDefender).switchPlayer

      } catch {
        case e: IllegalArgumentException =>
          println(e.getMessage) // Tir déjà effectué
      }
    }

    // Partie terminée
    val winner = gameState.checkVictory.get
    println(s"Partie terminée ! Le vainqueur est : ${winner.name}")
  }
}
