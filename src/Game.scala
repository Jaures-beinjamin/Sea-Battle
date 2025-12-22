object CellState {
  val Empty = 0
  val Miss = 1
  val Hit = 2
  val Ship = 3
}
class Game (caseSize: Int = 100, maxShip: Int = 3){
  private val boards = Array.fill(2)(Array.fill(10, 10)(CellState.Empty))
  private val grids = Array(
    new Grid("Player 1", caseSize, (x, y) => onPress(0, x, y), (x, y) => onRelease(0, x, y)),
    new Grid("Player 2", caseSize, (x, y) => onPress(1, x, y), (x, y) => onRelease(1, x, y))
  )

  private var phase = 0 // 0: P1 ship placement, 1: P2 ship placement, 2: battle phase
  private var playerTurn = 0

  // Ships placement variable
  private var shipPlaced = 0
  private var startX = 0
  private var startY = 0

  def start(): Unit = {
    grids(0).draw(boards(0))
    grids(1).draw(boards(1))
  }

  def onPress(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case 0 | 1 =>
        if (boardNumber == phase){
          startX = x
          startY = y
        }
      case 2 =>
        if (boardNumber != playerTurn){
          if (boards(boardNumber)(y)(x) == CellState.Ship) boards(boardNumber)(y)(x) = CellState.Hit
          else if (boards(boardNumber)(y)(x) == CellState.Empty) boards(boardNumber)(y)(x) = CellState.Miss
          else return

          val isStillAlive = boards(boardNumber).exists(row => row.contains(CellState.Ship))

          if (!isStillAlive){
            val winner = if (boardNumber == 0) 2 else 1
            println(s"Player $winner wins!")
          }

          grids(boardNumber).draw(boards(boardNumber))
          playerTurn = boardNumber
        }
      }
    }

  def onRelease(boardNumber: Int, x: Int, y: Int): Unit = {
    if (phase == 0 || phase == 1){
      if (boardNumber == phase){
        if (startY == y){
          for (i <- math.min(x, startX) to math.max(x, startX)){
            boards(phase)(y)(i) = CellState.Ship
          }
        } else if (startX == x){
          for (i <- math.min(y, startY) to math.max(y, startY)){
            boards(phase)(i)(x) = CellState.Ship
          }
        } else {
          return
        }
        shipPlaced += 1
        grids(phase).draw(boards(phase), showShip = true)
        if (shipPlaced == maxShip){
          grids(phase).draw(boards(phase))
          shipPlaced = 0
          phase += 1
        }
      }
    }
  }
}