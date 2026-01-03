package ui

import hevs.graphics.FunGraphics
import domain.{Grid, CellState, Position, Ship}
import game.GameConfig
import java.awt.Color

/**
 * Module de rendu graphique de la grille de jeu
 */
object GridRenderer {

  // Constantes pour le dessin de la grille
  private val CELL_SIZE = 40 // Taille d'une case en pixels
  private val GRID_LINE_WIDTH = 2 // Épaisseur des lignes de la grille

  // Couleurs
  private val COLOR_GRID_LINE = Color.BLACK
  private val COLOR_CELL_EMPTY = new Color(100, 149, 237) // Bleu ciel (eau)
  private val COLOR_CELL_SHIP = Color.GRAY
  private val COLOR_CELL_HIT = Color.RED
  private val COLOR_CELL_MISS = Color.WHITE

  /**
   * Dessine une grille de jeu centrée dans la fenêtre
   * @param fg instance FunGraphics
   * @param grid grille à dessiner
   * @param windowWidth largeur de la fenêtre
   * @param windowHeight hauteur de la fenêtre
   * @param ships ensemble des navires (pour afficher leur position)
   * @param showShips indique si les navires doivent être visibles (true pour le joueur, false pour l'adversaire)
   */
  def drawGrid(fg: FunGraphics, grid: Grid, windowWidth: Int, windowHeight: Int,
               ships: Set[Ship] = Set.empty, showShips: Boolean = true): Unit = {
    val gridPixelSize = GameConfig.GRID_SIZE * CELL_SIZE

    // Calcul de la position pour centrer la grille
    val offsetX = (windowWidth - gridPixelSize) / 2
    val offsetY = (windowHeight - gridPixelSize) / 2

    // Dessiner d'abord toutes les cases
    drawCells(fg, grid, offsetX, offsetY, ships, showShips)

    // Dessiner ensuite les lignes de la grille par-dessus
    drawGridLines(fg, offsetX, offsetY, gridPixelSize)
  }

  /**
   * Dessine toutes les cases de la grille
   */
  private def drawCells(fg: FunGraphics, grid: Grid, offsetX: Int, offsetY: Int,
                        ships: Set[Ship], showShips: Boolean): Unit = {
    for {
      row <- 0 until GameConfig.GRID_SIZE
      col <- 0 until GameConfig.GRID_SIZE
    } {
      val x = offsetX + col * CELL_SIZE
      val y = offsetY + row * CELL_SIZE
      val position = Position(col, row)

      grid.cellAt(col, row) match {
        case Some(cellState) =>
          val hasShip = ships.exists(_.occupies(position))
          val color = getCellColor(cellState, hasShip, showShips)
          fg.setColor(color)
          fg.drawFillRect(x, y, CELL_SIZE, CELL_SIZE)
        case None => // Ne devrait pas arriver
      }
    }
  }

  /**
   * Dessine les lignes horizontales et verticales de la grille
   */
  private def drawGridLines(fg: FunGraphics, offsetX: Int, offsetY: Int, gridPixelSize: Int): Unit = {
    fg.setColor(COLOR_GRID_LINE)

    // Lignes horizontales
    for (i <- 0 to GameConfig.GRID_SIZE) {
      val y = offsetY + i * CELL_SIZE
      drawThickLine(fg, offsetX, y, offsetX + gridPixelSize, y)
    }

    // Lignes verticales
    for (i <- 0 to GameConfig.GRID_SIZE) {
      val x = offsetX + i * CELL_SIZE
      drawThickLine(fg, x, offsetY, x, offsetY + gridPixelSize)
    }
  }

  /**
   * Dessine une ligne épaisse
   */
  private def drawThickLine(fg: FunGraphics, x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    for (offset <- 0 until GRID_LINE_WIDTH) {
      if (x1 == x2) {
        // Ligne verticale
        fg.drawLine(x1 + offset, y1, x2 + offset, y2)
      } else {
        // Ligne horizontale
        fg.drawLine(x1, y1 + offset, x2, y2 + offset)
      }
    }
  }

  /**
   * Détermine la couleur d'une case en fonction de son état
   */
  private def getCellColor(cellState: CellState, hasShip: Boolean, showShips: Boolean): Color = {
    cellState match {
      case CellState.Empty =>
        if (hasShip && showShips) COLOR_CELL_SHIP else COLOR_CELL_EMPTY
      case CellState.Hit => COLOR_CELL_HIT
      case CellState.Miss => COLOR_CELL_MISS
    }
  }

  /**
   * Calcule les dimensions totales de la grille en pixels
   */
  def getGridPixelSize: Int = GameConfig.GRID_SIZE * CELL_SIZE

  /**
   * Calcule la position X du coin supérieur gauche d'une grille centrée
   */
  def getCenteredOffsetX(windowWidth: Int): Int = {
    (windowWidth - getGridPixelSize) / 2
  }

  /**
   * Calcule la position Y du coin supérieur gauche d'une grille centrée
   */
  def getCenteredOffsetY(windowHeight: Int): Int = {
    (windowHeight - getGridPixelSize) / 2
  }
}

