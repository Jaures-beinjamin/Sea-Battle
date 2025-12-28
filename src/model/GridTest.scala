package model

import org.scalatest.funsuite.AnyFunSuite

class GridTest extends AnyFunSuite {

  test("updateCell should not modify original grid") {
    val grid = Grid.empty
    val updatedGrid = grid.updateCell(3, 4, CellState.Hit)

    assert(grid.cellAt(3, 4).contains(CellState.Empty))
    assert(updatedGrid.cellAt(3, 4).contains(CellState.Hit))
  }

  test("cellAt should return None for invalid coordinates") {
    val grid = Grid.empty
    assert(grid.cellAt(20, 5).isEmpty)
  }
}
