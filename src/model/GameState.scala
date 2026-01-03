package model

import domain.Player

/** Représente l’état courant d’une partie */
final case class GameState(
                            currentPlayer: Player,
                            otherPlayer: Player
                          ) {

  /** Change le joueur actif pour le tour suivant */
  def switchPlayer: GameState =
    this.copy(currentPlayer = otherPlayer, otherPlayer = currentPlayer)

  /** Vérifie si la partie est terminée et retourne le vainqueur si existant */
  def checkVictory: Option[Player] = {
    if (currentPlayer.allShipsSunk) Some(otherPlayer)
    else if (otherPlayer.allShipsSunk) Some(currentPlayer)
    else None
  }
}
