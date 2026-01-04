package ui

import hevs.graphics.FunGraphics

/**
 * Gère deux fenêtres de jeu, une pour chaque joueur
 */
object DualGameWindow {

  // Constantes des fenêtres
  private val WINDOW_WIDTH = 800
  private val WINDOW_HEIGHT = 600
  private val WINDOW_SPACING = 50 // Espacement entre les fenêtres

  // Instances de FunGraphics pour les deux joueurs
  private var player1Graphics: Option[FunGraphics] = None
  private var player2Graphics: Option[FunGraphics] = None

  /**
   * Ouvre les deux fenêtres du jeu
   * @param player1Name nom du joueur 1
   * @param player2Name nom du joueur 2
   */
  def open(player1Name: String, player2Name: String): Unit = {
    if (player1Graphics.isEmpty) {
      // Fenêtre du joueur 1
      val fg1 = new FunGraphics(WINDOW_WIDTH, WINDOW_HEIGHT, s"$player1Name - Grille de l'adversaire")
      player1Graphics = Some(fg1)
    }

    if (player2Graphics.isEmpty) {
      // Fenêtre du joueur 2
      val fg2 = new FunGraphics(WINDOW_WIDTH, WINDOW_HEIGHT, s"$player2Name - Grille de l'adversaire")
      player2Graphics = Some(fg2)
    }
  }

  /**
   * Ferme proprement les deux fenêtres
   */
  def close(): Unit = {
    player1Graphics = None
    player2Graphics = None
  }

  /**
   * Vérifie si les fenêtres sont ouvertes
   */
  def isOpen: Boolean = player1Graphics.isDefined && player2Graphics.isDefined

  /**
   * Obtient l'instance FunGraphics du joueur 1
   */
  def getPlayer1Graphics: Option[FunGraphics] = player1Graphics

  /**
   * Obtient l'instance FunGraphics du joueur 2
   */
  def getPlayer2Graphics: Option[FunGraphics] = player2Graphics

  /**
   * Obtient la largeur de la fenêtre
   */
  def getWindowWidth: Int = WINDOW_WIDTH

  /**
   * Obtient la hauteur de la fenêtre
   */
  def getWindowHeight: Int = WINDOW_HEIGHT
}

