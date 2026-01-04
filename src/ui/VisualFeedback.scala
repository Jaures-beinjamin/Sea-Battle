package ui

import hevs.graphics.FunGraphics
import domain.{Grid, Position, Ship}
import service.ShotResult

/**
 * Gère le feedback visuel pour les actions de jeu
 * Fournit un retour immédiat et clair à l'utilisateur
 */
object VisualFeedback {

  /**
   * Affiche le feedback visuel après un tir
   * Rafraîchit uniquement les cases impactées pour un feedback immédiat
   *
   * @param fg instance FunGraphics
   * @param grid grille mise à jour
   * @param shotPosition position du tir
   * @param result résultat du tir (Miss, Hit, Sunk)
   * @param windowWidth largeur de la fenêtre
   * @param windowHeight hauteur de la fenêtre
   * @param ships ensemble des navires
   * @param showShips indique si les navires doivent être visibles
   */
  def showShotFeedback(
                        fg: FunGraphics,
                        grid: Grid,
                        shotPosition: Position,
                        result: ShotResult,
                        windowWidth: Int,
                        windowHeight: Int,
                        ships: Set[Ship] = Set.empty,
                        showShips: Boolean = false
                      ): Unit = {

    result match {
      case ShotResult.Miss =>
        // Rafraîchit uniquement la case touchée
        GridRenderer.refreshCells(fg, grid, windowWidth, windowHeight,
                                 Set(shotPosition), ships, showShips)

      case ShotResult.Hit =>
        // Rafraîchit uniquement la case touchée
        GridRenderer.refreshCells(fg, grid, windowWidth, windowHeight,
                                 Set(shotPosition), ships, showShips)

      case ShotResult.Sunk =>
        // Trouve le navire coulé et rafraîchit toutes ses cases
        val sunkShip = ships.find(ship => ship.isSunk && ship.occupies(shotPosition))

        sunkShip match {
          case Some(ship) =>
            // Rafraîchit toutes les cases du navire coulé
            GridRenderer.refreshCells(fg, grid, windowWidth, windowHeight,
                                     ship.positions, ships, showShips)
          case None =>
            // Si le navire n'est pas trouvé, rafraîchit au moins la case touchée
            GridRenderer.refreshCells(fg, grid, windowWidth, windowHeight,
                                     Set(shotPosition), ships, showShips)
        }
    }
  }

  /**
   * Affiche un feedback complet de la grille
   * Utilisé pour l'affichage initial ou après des changements majeurs
   *
   * @param fg instance FunGraphics
   * @param grid grille à afficher
   * @param windowWidth largeur de la fenêtre
   * @param windowHeight hauteur de la fenêtre
   * @param ships ensemble des navires
   * @param showShips indique si les navires doivent être visibles
   */
  def showFullGrid(
                    fg: FunGraphics,
                    grid: Grid,
                    windowWidth: Int,
                    windowHeight: Int,
                    ships: Set[Ship] = Set.empty,
                    showShips: Boolean = false
                  ): Unit = {
    GridRenderer.drawGrid(fg, grid, windowWidth, windowHeight, ships, showShips)
  }
}

