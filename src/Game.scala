sealed trait CellState
case object Empty extends CellState
case object Miss extends CellState
case object Hit extends CellState
case object Ship extends CellState

sealed trait GamePhase
case object ShipP1 extends GamePhase
case object ShipP2 extends GamePhase
case object Battle extends GamePhase
case object GameOver extends GamePhase

class Game(caseSize: Int = 100, maxShip: Int = 3) {
  private val boards: Array[Array[Array[CellState]]] = Array.fill(2)(Array.fill(10, 10)(Empty))
  private val grids = Array(
    new Grid("Player 1", caseSize, (x, y) => onPress(0, x, y), (x, y) => onRelease(0, x, y)),
    new Grid("Player 2", caseSize, (x, y) => onPress(1, x, y), (x, y) => onRelease(1, x, y))
  )

  private var phase: GamePhase = _
  private var playerTurn: Int = _

  // Ships placement variable
  private var shipPlaced = 0
  private var startX = 0
  private var startY = 0

  def start(): Unit = {
    boards(0) = Array.fill(10, 10)(Empty)
    boards(1) = Array.fill(10, 10)(Empty)
    phase = ShipP1
    playerTurn = 0
    shipPlaced = 0
    grids(0).draw(boards(0))
    grids(1).draw(boards(1))
  }

  def onPress(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case ShipP1 | ShipP2 =>
        if (boardNumber == playerTurn) {
          startX = x
          startY = y
        }
      case Battle =>
        if (boardNumber != playerTurn) {
          if (boards(boardNumber)(y)(x) == Ship) boards(boardNumber)(y)(x) = Hit
          else if (boards(boardNumber)(y)(x) == Empty) boards(boardNumber)(y)(x) = Miss
          else return

          val isStillAlive = boards(boardNumber).exists(row => row.contains(Ship))

          if (!isStillAlive) {
            val winner = if (boardNumber == 0) 2 else 1
            println(s"Player $winner wins!")
            phase = GameOver
          }

          grids(boardNumber).draw(boards(boardNumber))
          playerTurn = boardNumber
        }
      case GameOver =>
        start()
      case _ =>
    }
  }

  def onRelease(boardNumber: Int, x: Int, y: Int): Unit = {
    if (phase == ShipP1 || phase == ShipP2) {
      if (boardNumber == playerTurn) {
        if (startY == y) {
          for (i <- math.min(x, startX) to math.max(x, startX)) {
            boards(boardNumber)(y)(i) = Ship
          }
        } else if (startX == x) {
          for (i <- math.min(y, startY) to math.max(y, startY)) {
            boards(boardNumber)(i)(x) = Ship
          }
        } else {
          return
        }
        shipPlaced += 1
        grids(boardNumber).draw(boards(boardNumber), showShip = true)
        if (shipPlaced == maxShip) {
          grids(boardNumber).draw(boards(boardNumber))
          shipPlaced = 0
          phase = if (phase == ShipP1) ShipP2 else Battle
          playerTurn = if (playerTurn == 0) 1 else 0
        }
      }
    }
  }
}