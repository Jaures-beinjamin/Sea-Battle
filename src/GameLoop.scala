package main

import model.{GameState, Grid, Player, Position, ShotResult}
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
    while (gameState.currentPlayer.ships.exists(!_.isSunk) &&
      gameState.otherPlayer.ships.exists(!_.isSunk)) {

      println(s"Tour du joueur: ${gameState.currentPlayer.name}")

      // Exemple: lire une position de tir depuis l'entrée console
      println("Entrez les coordonnées x y: ")
      val input = scala.io.StdIn.readLine().split(" ")
      val x = input(0).toInt
      val y = input(1).toInt
      val position = Position(x, y)

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

    // Déterminer le vainqueur
    val winner = if (gameState.currentPlayer.ships.exists(!_.isSunk))
      gameState.currentPlayer.name
    else
      gameState.otherPlayer.name

    println(s"Partie terminée ! Le vainqueur est : $winner")
  }
}
