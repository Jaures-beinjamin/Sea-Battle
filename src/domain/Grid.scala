package domain

import game.GameConfig

/**
 * Grille de jeu représentant l'océan
 * Contient l'état de toutes les cases (vide, touchée, manquée)
 */
final case class Grid(cells: Vector[Vector[CellState]]) {

  private val size: Int = GameConfig.GRID_SIZE

  /**
   * Vérifie si une position est valide (dans la grille)
   */
  def isValid(x: Int, y: Int): Boolean =
    x >= 0 && y >= 0 && x < size && y < size

  /**
   * Récupère l'état d'une case
   * @return Some(état) si la position est valide, None sinon
   */
  def cellAt(x: Int, y: Int): Option[CellState] =
    if (isValid(x, y)) Some(cells(y)(x)) else None

  /**
   * Met à jour l'état d'une case
   * @return Une nouvelle grille avec la case mise à jour
   */
  def updateCell(x: Int, y: Int, newState: CellState): Grid =
    if (!isValid(x, y)) this
    else {
      val updatedRow = cells(y).updated(x, newState)
      val updatedCells = cells.updated(y, updatedRow)
      copy(cells = updatedCells)
    }
}

object Grid {

  /**
   * Crée une grille vide (toutes les cases sont de l'eau)
   */
  def empty: Grid = {
    val size = GameConfig.GRID_SIZE
    val cells = Vector.fill(size, size)(CellState.Empty)
    Grid(cells)
  }
}

