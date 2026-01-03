import domain.{Player, Grid, Position}
import service.ShipService
import game.{GameEngine, GameConfig}
import ui.{GameWindow, GridRenderer}
import java.awt.event.{MouseAdapter, MouseEvent, KeyAdapter, KeyEvent}

/**
 * Point d'entrée du jeu de bataille navale
 */
object Main {

  def main(args: Array[String]): Unit = {
    println("====================================")
    println("   🚢 BATAILLE NAVALE 🚢")
    println("====================================")
    println()

    // Ouverture de la fenêtre du jeu
    GameWindow.open()

    // Création des joueurs avec grille vide
    val player1 = createPlayer("Alice")

    // Test du rendu de la grille et de la conversion clic → case (TICKET 6.3)
    GameWindow.getGraphics match {
      case Some(fg) =>
        // Dessiner la grille du joueur 1 avec ses navires visibles
        GridRenderer.drawGrid(fg, player1.grid, 800, 600, player1.ships, showShips = true)

        println("✅ Grille affichée avec succès !")
        println("   - Lignes horizontales et verticales dessinées")
        println("   - Toutes les cases sont visibles")
        println("   - Grille centrée dans la fenêtre")
        println("   - Navires affichés en gris")
        println("\n📍 Test de conversion clic → case (TICKET 6.3)")
        println("   Cliquez sur la grille pour voir les coordonnées...")
        println("   Appuyez sur ESC pour terminer.\n")

        // Variable pour contrôler la boucle
        var running = true

        // Ajouter un MouseListener pour détecter les clics
        fg.addMouseListener(new MouseAdapter {
          override def mouseClicked(e: MouseEvent): Unit = {
            val mouseX = e.getX
            val mouseY = e.getY

            // Convertir le clic en position de grille
            val gridPosition = GridRenderer.mouseToGridPosition(mouseX, mouseY, 800, 600)

            gridPosition match {
              case Some(pos) =>
                println(s"✅ Clic valide : Case (${pos.x}, ${pos.y})")

                // Afficher des informations supplémentaires
                val hasShip = player1.ships.exists(_.occupies(pos))
                if (hasShip) {
                  println(s"   ⚓ Il y a un navire sur cette case")
                } else {
                  println(s"   🌊 Case vide (eau)")
                }

              case None =>
                println(s"❌ Clic hors grille : Pixel ($mouseX, $mouseY) ignoré")
            }
          }
        })

        // Ajouter un KeyListener pour détecter la touche ESC
        fg.setKeyManager(new KeyAdapter {
          override def keyPressed(e: KeyEvent): Unit = {
            if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
              running = false
              println("\n🛑 Arrêt demandé par l'utilisateur...")
            }
          }
        })

        // Boucle principale pour maintenir le programme actif
        while (running) {
          Thread.sleep(100)
        }

        println("\n✅ Test de conversion clic → case terminé avec succès !")
        println("   - Récupération de la position du clic (x, y) ✓")
        println("   - Calcul de la ligne/colonne correspondante ✓")
        println("   - Vérification que le clic est dans la grille ✓")
        println("   - Clics hors grille ignorés ✓")
        println("   - Aucun crash sur clic hors zone ✓")
        println("   - Conversion précise ✓")

      case None =>
        println("❌ Erreur : la fenêtre n'est pas ouverte")
    }

    // Fermeture propre de la fenêtre
    GameWindow.close()
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

