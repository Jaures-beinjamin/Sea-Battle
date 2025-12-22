sealed trait CellState
case object Empty extends CellState
case object Miss extends CellState
case object Hit extends CellState
case object Ship extends CellState

sealed trait GamePhase
case object ShipPlacement extends GamePhase
case object Battle extends GamePhase
case object GameOver extends GamePhase

class Game(numPlayers: Int, caseSize: Int = 100, maxShip: Int = 3) {
  private val boards: Array[Array[Array[CellState]]] = Array.fill(numPlayers)(Array.fill(10, 10)(Empty))
  private val grids = new Array[Grid](numPlayers)
  for (i <- 0 until numPlayers) {
    grids(i) = new Grid(s"Player ${i + 1}", caseSize, (x, y) => onPress(i, x, y), (x, y) => onRelease(i, x, y))
  }

  private var phase: GamePhase = _
  private var playerTurn: Int = _

  // Ships placement variable
  private var shipPlaced = 0
  private var startX = 0
  private var startY = 0

  def start(): Unit = {
    for (i <- 0 until numPlayers) {
      boards(i) = Array.fill(10, 10)(Empty)
      grids(i).draw(boards(i))
    }
    phase = ShipPlacement
    playerTurn = 0
    shipPlaced = 0
  }

  def onPress(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case ShipPlacement =>
        if (boardNumber == playerTurn) {
          startX = x
          startY = y
        }
      case Battle =>
        if (boardNumber == playerTurn) {
          if (boards(boardNumber)(y)(x) == Ship) boards(boardNumber)(y)(x) = Hit
          else if (boards(boardNumber)(y)(x) == Empty) boards(boardNumber)(y)(x) = Miss
          else return

          val isStillAlive = boards(boardNumber).exists(row => row.contains(Ship))

          if (!isStillAlive) {
            println(s"Player ${boardNumber + 1} is eliminated!")
            phase = GameOver
          }

          grids(boardNumber).draw(boards(boardNumber))
          playerTurn = (playerTurn + 1) % numPlayers
        }
      case GameOver =>
        start()
      case _ => ()
    }
  }

  def onRelease(boardNumber: Int, x: Int, y: Int): Unit = {
    if (phase == ShipPlacement && boardNumber == playerTurn) {
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
        phase = if (playerTurn == numPlayers - 1) Battle else ShipPlacement
        playerTurn = if (playerTurn == numPlayers - 1) 0 else playerTurn + 1
      }
    }
  }
}