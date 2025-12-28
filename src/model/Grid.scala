package model

import config.GameConfig

final case class Grid(cells: Vector[Vector[CellState]]) {

  private val size: Int = GameConfig.GRID_SIZE

  /** Vérifie si les coordonnées sont dans la grille */
  def isValid(x: Int, y: Int): Boolean =
    x >= 0 && y >= 0 && x < size && y < size

  /** Lecture sécurisée d’une case */
  def cellAt(x: Int, y: Int): Option[CellState] =
    if (isValid(x, y)) Some(cells(y)(x)) else None

  /** Mise à jour immuable d’une case */
  def updateCell(x: Int, y: Int, newState: CellState): Grid =
    if (!isValid(x, y)) this
    else {
      val updatedRow = cells(y).updated(x, newState)
      val updatedCells = cells.updated(y, updatedRow)
      copy(cells = updatedCells)
    }
}
