import domain.{Player, Grid, Position}
import service.{ShipService, CombatService}
import game.{GameEngine, GameConfig}
import ui.{DualGameWindow, GridRenderer, VisualFeedback, ShipPlacement}
import hevs.graphics.FunGraphics
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
    println("🎨 Feedback visuel activé :")
    println("  🔵 Bleu clair = Eau (vide)")
    println("  ⚪ Blanc cassé = Tir raté (Miss)")
    println("  🟠 Rouge orangé = Navire touché (Hit)")
    println("  🔴 Rouge foncé = Navire coulé (Sunk)")
    println()
    println("👥 Mode 2 joueurs activé")
    println("   Chaque joueur place ses navires avant de commencer")
    println()

    // Ouverture des deux fenêtres du jeu
    DualGameWindow.open("Alice", "Bob")

    (DualGameWindow.getPlayer1Graphics, DualGameWindow.getPlayer2Graphics) match {
      case (Some(fg1), Some(fg2)) =>
        val windowWidth = DualGameWindow.getWindowWidth
        val windowHeight = DualGameWindow.getWindowHeight

        // PHASE 1 : Placement des navires pour Alice
        println("=" * 50)
        println("PHASE 1 : PLACEMENT DES NAVIRES")
        println("=" * 50)

        val player1Ships = ShipPlacement.placeShipsInteractively(
          fg1,
          "Alice",
          GameConfig.SHIP_SIZES,
          windowWidth,
          windowHeight
        )

        var player1 = Player("Alice", Grid.empty, player1Ships)

        // Pause pour que Bob ne voie pas les navires d'Alice
        println("\n⏸️  Prêt pour Bob ? Appuyez sur une touche dans la fenêtre de Bob...")
        waitForKeyPress(fg2)

        // PHASE 2 : Placement des navires pour Bob
        val player2Ships = ShipPlacement.placeShipsInteractively(
          fg2,
          "Bob",
          GameConfig.SHIP_SIZES,
          windowWidth,
          windowHeight
        )

        var player2 = Player("Bob", Grid.empty, player2Ships)

        println("\n" + "=" * 50)
        println("PHASE 2 : COMBAT")
        println("=" * 50)
        println("Les deux joueurs ont placé leurs navires !")
        println("Que le combat commence !\n")

        // Afficher les grilles de combat (navires cachés)
        // Player 1 voit la grille de Player 2 (navires cachés)
        VisualFeedback.showFullGrid(fg1, player2.grid, windowWidth, windowHeight, player2.ships, showShips = false)
        // Player 2 voit la grille de Player 1 (navires cachés)
        VisualFeedback.showFullGrid(fg2, player1.grid, windowWidth, windowHeight, player1.ships, showShips = false)

        println("✅ Grilles de combat affichées pour les deux joueurs")
        println("   Alice (fenêtre gauche) tire sur Bob")
        println("   Bob (fenêtre droite) tire sur Alice")
        println("   Les joueurs alternent après chaque tir")
        println("   Appuyez sur ESC pour terminer.\n")

        // Variables pour le jeu
        var running = true
        var currentPlayer = 1 // 1 pour Alice, 2 pour Bob
        var gameOver = false

        // MouseListener pour Player 1
        val mouseListener1 = new MouseAdapter {
          override def mouseClicked(e: MouseEvent): Unit = {
            if (currentPlayer == 1 && !gameOver) {
              handleShot(e, fg1, player1, player2, "Alice", "Bob", windowWidth, windowHeight) match {
                case Some((updatedAttacker, updatedDefender)) =>
                  player1 = updatedAttacker
                  player2 = updatedDefender
                  if (player2.allShipsSunk) {
                    println("\n🏆 VICTOIRE ! Alice a coulé tous les navires de Bob !")
                    gameOver = true
                  } else {
                    currentPlayer = 2 // Tour de Bob
                    println(s"\n🔄 C'est au tour de Bob\n")
                  }
                case None => // Tir invalide, le joueur rejoue
              }
            }
          }
        }
        fg1.addMouseListener(mouseListener1)

        // MouseListener pour Player 2
        val mouseListener2 = new MouseAdapter {
          override def mouseClicked(e: MouseEvent): Unit = {
            if (currentPlayer == 2 && !gameOver) {
              handleShot(e, fg2, player2, player1, "Bob", "Alice", windowWidth, windowHeight) match {
                case Some((updatedAttacker, updatedDefender)) =>
                  player2 = updatedAttacker
                  player1 = updatedDefender
                  if (player1.allShipsSunk) {
                    println("\n🏆 VICTOIRE ! Bob a coulé tous les navires d'Alice !")
                    gameOver = true
                  } else {
                    currentPlayer = 1 // Tour d'Alice
                    println(s"\n🔄 C'est au tour d'Alice\n")
                  }
                case None => // Tir invalide, le joueur rejoue
              }
            }
          }
        }
        fg2.addMouseListener(mouseListener2)

        // KeyListener pour les deux fenêtres
        val keyListener = new KeyAdapter {
          override def keyPressed(e: KeyEvent): Unit = {
            if (e.getKeyCode == KeyEvent.VK_ESCAPE) {
              running = false
              println("\n🛑 Arrêt demandé par l'utilisateur...")
            }
          }
        }
        fg1.setKeyManager(keyListener)
        fg2.setKeyManager(keyListener)

        // Message du premier joueur
        println(s"▶️  C'est au tour d'Alice\n")

        // Boucle principale
        while (running) {
          Thread.sleep(100)
        }

        println("\n✅ Jeu terminé !")
        println("   ✓ Placement manuel : Chaque joueur a placé ses navires")
        println("   ✓ Feedback visuel : Couleurs distinctes pour Hit/Miss/Sunk")
        println("   ✓ Performance : Rafraîchissement instantané")
        println("   ✓ Cohérence : Couleurs identiques à chaque type")
        println("   ✓ Ergonomie : Résultat immédiatement compréhensible")
        println("   ✓ Mode 2 joueurs : Deux interfaces séparées")

      case _ =>
        println("❌ Erreur : les fenêtres ne sont pas ouvertes")
    }

    // Fermeture propre des fenêtres
    DualGameWindow.close()
  }

  /**
   * Attend qu'une touche soit pressée
   */
  private def waitForKeyPress(fg: FunGraphics): Unit = {
    val keyPressedFlag = Array(false)
    val keyListener = new KeyAdapter {
      override def keyPressed(e: KeyEvent): Unit = {
        keyPressedFlag(0) = true
      }
    }
    fg.setKeyManager(keyListener)

    while (!keyPressedFlag(0)) {
      Thread.sleep(100)
    }
  }

  /**
   * Gère un tir d'un joueur
   * @return Some((attacker, defender)) si le tir est valide, None sinon
   */
  private def handleShot(
                          e: MouseEvent,
                          fg: hevs.graphics.FunGraphics,
                          attacker: Player,
                          defender: Player,
                          attackerName: String,
                          defenderName: String,
                          windowWidth: Int,
                          windowHeight: Int
                        ): Option[(Player, Player)] = {
    val mouseX = e.getX
    val mouseY = e.getY

    GridRenderer.mouseToGridPosition(mouseX, mouseY, windowWidth, windowHeight) match {
      case Some(shotPosition) =>
        try {
          // Effectuer le tir
          val (updatedAttacker, updatedDefender, result) =
            CombatService.fireAt(attacker, defender, shotPosition)

          // Afficher le feedback visuel immédiat
          VisualFeedback.showShotFeedback(
            fg,
            updatedDefender.grid,
            shotPosition,
            result,
            windowWidth, windowHeight,
            updatedDefender.ships,
            showShips = false
          )

          // Afficher le résultat dans la console
          result match {
            case service.ShotResult.Miss =>
              println(s"⚪ $attackerName RATÉ ! Case (${shotPosition.x}, ${shotPosition.y})")
            case service.ShotResult.Hit =>
              println(s"🟠 $attackerName TOUCHÉ ! Case (${shotPosition.x}, ${shotPosition.y})")
            case service.ShotResult.Sunk =>
              println(s"🔴 $attackerName COULÉ ! Case (${shotPosition.x}, ${shotPosition.y})")
              println(s"   🎉 $attackerName a complètement détruit un navire de $defenderName !")
          }

          Some((updatedAttacker, updatedDefender))

        } catch {
          case ex: IllegalArgumentException =>
            println(s"❌ $attackerName : ${ex.getMessage}")
            None
        }

      case None =>
        println(s"❌ $attackerName : Clic hors grille ignoré")
        None
    }
  }

  /**
   * Crée un joueur avec ses navires placés automatiquement
   * @param name nom du joueur
   * @return joueur prêt à jouer
   *
   * Note: Cette fonction n'est plus utilisée car le placement est maintenant manuel,
   * mais elle est conservée pour des tests futurs
   */
  /*
  private def createPlayer(name: String): Player = {
    val grid = Grid.empty
    val ships = ShipService.placeAllShipsRandomly(GameConfig.SHIP_SIZES)
    Player(name, grid, ships)
  }
  */
}

