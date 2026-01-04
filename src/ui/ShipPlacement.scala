package ui

import domain.{Ship, Position, Orientation, Player}
import service.ShipService
import game.GameConfig
import hevs.graphics.FunGraphics
import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent, KeyAdapter, KeyEvent}

/**
 * Gère le placement interactif des navires par le joueur
 */
object ShipPlacement {

  private val CELL_SIZE = 40
  private val COLOR_VALID_PLACEMENT = new Color(0, 200, 0, 100) // Vert transparent
  private val COLOR_INVALID_PLACEMENT = new Color(200, 0, 0, 100) // Rouge transparent
  private val COLOR_PLACED_SHIP = Color.GRAY

  /**
   * Permet à un joueur de placer ses navires manuellement
   * @param fg instance FunGraphics
   * @param playerName nom du joueur
   * @param shipSizes tailles des navires à placer
   * @param windowWidth largeur de la fenêtre
   * @param windowHeight hauteur de la fenêtre
   * @return ensemble des navires placés
   */
  def placeShipsInteractively(
                                fg: FunGraphics,
                                playerName: String,
                                shipSizes: List[Int],
                                windowWidth: Int,
                                windowHeight: Int
                              ): Set[Ship] = {

    println(s"\n🚢 $playerName : Placez vos navires")
    println(s"   Navires à placer : ${shipSizes.mkString(", ")} cases")
    println("   Cliquez pour placer un navire")
    println("   Appuyez sur R pour changer l'orientation (Horizontal/Vertical)")
    println("   Appuyez sur ENTRÉE quand tous les navires sont placés")
    println("   Appuyez sur BACKSPACE pour annuler le dernier navire\n")

    var placedShips = Set.empty[Ship]
    var currentShipIndex = 0
    var currentOrientation: Orientation = Orientation.Horizontal
    var previewPosition: Option[Position] = None
    var placementComplete = false
    var lastMouseX = 0
    var lastMouseY = 0

    // Fonction pour dessiner la grille et les navires
    def drawPlacementGrid(): Unit = {
      // Efface tout
      fg.clear()

      val grid = domain.Grid.empty
      GridRenderer.drawGrid(fg, grid, windowWidth, windowHeight, placedShips, showShips = true)

      // Dessine l'aperçu du navire à placer
      if (currentShipIndex < shipSizes.size && previewPosition.isDefined) {
        val pos = previewPosition.get
        val size = shipSizes(currentShipIndex)
        val previewShip = Ship.create(pos.x, pos.y, size, currentOrientation)

        val isValid = ShipService.isInBounds(previewShip) &&
                     ShipService.canPlaceShip(previewShip, placedShips)
        val color = if (isValid) COLOR_VALID_PLACEMENT else COLOR_INVALID_PLACEMENT

        drawShipPreview(fg, previewShip, color, windowWidth, windowHeight)
      }

      // Affiche les informations
      fg.setColor(Color.BLACK)
      if (currentShipIndex < shipSizes.size) {
        fg.drawString(20, 30, s"$playerName - Navire ${currentShipIndex + 1}/${shipSizes.size} (taille: ${shipSizes(currentShipIndex)})")
        fg.drawString(20, 50, s"Orientation: ${if (currentOrientation == Orientation.Horizontal) "Horizontale" else "Verticale"}")
      } else {
        fg.drawString(20, 30, s"$playerName - Tous les navires placés ! Appuyez sur ENTRÉE")
      }
    }

    // MouseListener pour le placement
    val mouseListener = new MouseAdapter {
      override def mouseClicked(e: MouseEvent): Unit = {
        if (currentShipIndex < shipSizes.size) {
          GridRenderer.mouseToGridPosition(e.getX, e.getY, windowWidth, windowHeight) match {
            case Some(position) =>
              val size = shipSizes(currentShipIndex)
              val newShip = Ship.create(position.x, position.y, size, currentOrientation)

              if (ShipService.isInBounds(newShip) && ShipService.canPlaceShip(newShip, placedShips)) {
                placedShips = placedShips + newShip
                currentShipIndex += 1
                println(s"✅ Navire ${currentShipIndex}/${shipSizes.size} placé en (${position.x}, ${position.y})")
                drawPlacementGrid()
              } else {
                println(s"❌ Placement invalide ! Le navire sort de la grille ou chevauche un autre navire.")
              }
            case None =>
          }
        }
      }

      override def mouseMoved(e: MouseEvent): Unit = {
        if (currentShipIndex < shipSizes.size) {
          lastMouseX = e.getX
          lastMouseY = e.getY
          GridRenderer.mouseToGridPosition(e.getX, e.getY, windowWidth, windowHeight) match {
            case Some(position) =>
              previewPosition = Some(position)
              drawPlacementGrid()
            case None =>
              previewPosition = None
              drawPlacementGrid()
          }
        }
      }
    }

    // KeyListener pour changer l'orientation et valider
    val keyListener = new KeyAdapter {
      override def keyPressed(e: KeyEvent): Unit = {
        e.getKeyCode match {
          case KeyEvent.VK_R =>
            // Change l'orientation
            currentOrientation = if (currentOrientation == Orientation.Horizontal) {
              println("🔄 Orientation : Verticale")
              Orientation.Vertical
            } else {
              println("🔄 Orientation : Horizontale")
              Orientation.Horizontal
            }
            drawPlacementGrid()

          case KeyEvent.VK_ENTER =>
            // Valide le placement si tous les navires sont placés
            if (currentShipIndex >= shipSizes.size) {
              placementComplete = true
              println(s"✅ $playerName a terminé le placement des navires !\n")
            } else {
              println(s"❌ Vous devez encore placer ${shipSizes.size - currentShipIndex} navire(s)")
            }

          case KeyEvent.VK_BACK_SPACE =>
            // Annule le dernier navire placé
            if (placedShips.nonEmpty) {
              placedShips = placedShips.tail
              currentShipIndex = math.max(0, currentShipIndex - 1)
              println(s"↩️  Navire annulé. Navires restants : ${currentShipIndex}/${shipSizes.size}")
              drawPlacementGrid()
            }

          case _ =>
        }
      }
    }

    // Ajoute les listeners
    fg.addMouseListener(mouseListener)
    fg.addMouseMotionListener(mouseListener)
    fg.setKeyManager(keyListener)

    // Dessine l'écran initial
    drawPlacementGrid()

    // Boucle d'attente jusqu'à ce que le placement soit complet
    while (!placementComplete) {
      Thread.sleep(100)
    }

    // Note: Les listeners ne peuvent pas être retirés avec FunGraphics
    // Ils seront automatiquement remplacés lors de la phase de combat

    placedShips
  }

  /**
   * Dessine l'aperçu d'un navire avec une couleur spécifique
   */
  private def drawShipPreview(
                               fg: FunGraphics,
                               ship: Ship,
                               color: Color,
                               windowWidth: Int,
                               windowHeight: Int
                             ): Unit = {
    val offsetX = GridRenderer.getCenteredOffsetX(windowWidth)
    val offsetY = GridRenderer.getCenteredOffsetY(windowHeight)

    fg.setColor(color)
    ship.positions.foreach { pos =>
      val x = offsetX + pos.x * CELL_SIZE
      val y = offsetY + pos.y * CELL_SIZE
      fg.drawFillRect(x, y, CELL_SIZE, CELL_SIZE)
    }
  }
}

