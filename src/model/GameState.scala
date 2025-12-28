package model

/** Représente l’état courant d’une partie */
final case class GameState(
                            currentPlayer: Player,
                            otherPlayer: Player
                          ) {

  /** Change le joueur actif pour le tour suivant */
  def switchPlayer: GameState =
    this.copy(currentPlayer = otherPlayer, otherPlayer = currentPlayer)
}
