package ui

import hevs.graphics.FunGraphics

/**
 * Fenêtre principale du jeu Sea Battle
 */
object GameWindow {

  // Constantes de la fenêtre
  private val WINDOW_WIDTH = 800
  private val WINDOW_HEIGHT = 600
  private val WINDOW_TITLE = "Sea Battle"

  // Instance de FunGraphics
  private var graphics: Option[FunGraphics] = None

  /**
   * Ouvre la fenêtre du jeu
   */
  def open(): Unit = {
    if (graphics.isEmpty) {
      val fg = new FunGraphics(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE)
      graphics = Some(fg)
    }
  }

  /**
   * Ferme proprement la fenêtre du jeu
   * Note: FunGraphics se ferme automatiquement à la fin du programme
   */
  def close(): Unit = {
    // La fenêtre se fermera automatiquement
    graphics = None
  }

  /**
   * Vérifie si la fenêtre est ouverte
   * @return true si la fenêtre est ouverte, false sinon
   */
  def isOpen: Boolean = graphics.isDefined

  /**
   * Obtient l'instance FunGraphics (si la fenêtre est ouverte)
   * @return Option contenant l'instance FunGraphics
   */
  def getGraphics: Option[FunGraphics] = graphics
}

