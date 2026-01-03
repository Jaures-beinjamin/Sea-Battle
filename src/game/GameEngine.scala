package game

import domain.Player
import service.{CombatService, ShotResult, ValidationService}
import ui.ConsoleUI

object GameEngine {

  /**
   * Démarre une partie entre deux joueurs
   * @param player1 premier joueur
   * @param player2 deuxième joueur
   */
  def startGame(player1: Player, player2: Player): Unit = {

    // Affiche le message de bienvenue
    ConsoleUI.showWelcome()

    // Valide les plateaux des joueurs
    if (!validatePlayers(player1, player2)) {
      ConsoleUI.showError("Les plateaux de jeu sont invalides !")
      return
    }

    println("✅ Les plateaux sont valides. La partie commence !\n")

    // Lance la boucle de jeu
    var currentAttacker = player1
    var currentDefender = player2
    var gameOver = false

    while (!gameOver) {
      // Affiche le tour du joueur
      ConsoleUI.showPlayerTurn(currentAttacker.name)

      // Le joueur tire
      val shotPosition = ConsoleUI.askForShot()

      // Vérifie si la position est valide
      if (!ValidationService.validatePosition(shotPosition)) {
        ConsoleUI.showError("Position hors de la grille !")
        // On ne change pas de joueur, il doit retirer
      } else {
        try {
          // Effectue le tir
          val (updatedAttacker, updatedDefender, result) =
            CombatService.fireAt(currentAttacker, currentDefender, shotPosition)

          // Affiche le résultat
          showResult(result)

          // Met à jour les joueurs
          currentAttacker = updatedAttacker
          currentDefender = updatedDefender

          // Vérifie la victoire
          if (currentDefender.allShipsSunk) {
            ConsoleUI.showWinner(currentAttacker.name)
            gameOver = true
          } else {
            // Change de joueur pour le prochain tour
            val temp = currentAttacker
            currentAttacker = currentDefender
            currentDefender = temp
          }

        } catch {
          case e: IllegalArgumentException =>
            ConsoleUI.showError(e.getMessage)
          // On ne change pas de joueur, il doit retirer
        }
      }
    }
  }

  /**
   * Valide les plateaux des deux joueurs
   */
  private def validatePlayers(player1: Player, player2: Player): Boolean = {
    val expectedShipCount = GameConfig.NUMBER_OF_SHIPS
    ValidationService.validatePlayer(player1, expectedShipCount) &&
      ValidationService.validatePlayer(player2, expectedShipCount)
  }

  /**
   * Affiche le résultat d'un tir de manière lisible
   */
  private def showResult(result: ShotResult): Unit = {
    result match {
      case ShotResult.Miss => ConsoleUI.showShotResult("Miss")
      case ShotResult.Hit => ConsoleUI.showShotResult("Hit")
      case ShotResult.Sunk => ConsoleUI.showShotResult("Sunk")
    }
  }
}

