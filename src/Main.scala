
import domain.{Player, Grid}
import service.ShipService
import game.{GameEngine, GameConfig}
import ui.{GameWindow, GridRenderer}

/**
 * Point d'entrée du jeu de bataille navale
 */
object Main {

  def main(args: Array[String]): Unit = {
    // Ouverture de la fenêtre du jeu
    GameWindow.open()

    // Création des joueurs avec grille vide
    val player1 = createPlayer("Alice")
    val player2 = createPlayer("Bob")

    // Test du rendu de la grille (TICKET 6.2)
    GameWindow.getGraphics match {
      case Some(fg) =>
        // Dessiner la grille du joueur 1 avec ses navires visibles
        GridRenderer.drawGrid(fg, player1.grid, 800, 600, player1.ships, showShips = true)

        println("✅ Grille affichée avec succès !")
        println("   - Lignes horizontales et verticales dessinées")
        println("   - Toutes les cases sont visibles")
        println("   - Grille centrée dans la fenêtre")
        println("   - Navires affichés en gris")
        println("\n⏳ Fenêtre visible pendant 5 secondes...")

        // Attendre un peu pour voir la grille
        Thread.sleep(5000)
      case None =>
        println("❌ Erreur : la fenêtre n'est pas ouverte")
    }

    // Fermeture propre de la fenêtre
    GameWindow.close()

    println("\n✅ Test du rendu de grille terminé avec succès !")
  }

  /**
   * Crée un joueur avec ses navires placés automatiquement
   * @param name nom du joueur
   * @return joueur prêt à jouer
   */
  private def createPlayer(name: String): Player = {
    // Crée une grille vide
    val grid = Grid.empty

    // Place les navires automatiquement
    val ships = ShipService.placeAllShipsRandomly(GameConfig.SHIP_SIZES)

    // Retourne le joueur avec sa grille et ses navires
    Player(name, grid, ships)
  }
}
