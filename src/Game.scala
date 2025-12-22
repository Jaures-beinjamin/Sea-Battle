class Game (caseSize: Int = 100, maxBoat: Int = 3){
  private val boards = Array(Array.ofDim[Int](10, 10), Array.ofDim[Int](10, 10)) // 0: empty, 1: miss, 2: hit, 3: ship
  private val grids = Array(
    new Grid("Player 1", caseSize, (x, y) => onPress(0, x, y), (x, y) => onRelease(0, x, y)),
    new Grid("Player 2", caseSize, (x, y) => onPress(1, x, y), (x, y) => onRelease(1, x, y))
  )

  private var phase = 0 // 0: P1 ship placement, 1: P2 ship placement, 2: battle phase
  private var playerTurn = 0

  // Boats placement variable
  private var boatPlaced = 0
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
        if (boardNumber == playerTurn) return
        if (boardNumber == 0){
          if (boards(0)(y)(x) == 3) boards(0)(y)(x) = 2
          else boards(0)(y)(x) = 1
          grids(0).draw(boards(0))
        } else if (boardNumber == 1){
          if (boards(1)(y)(x) == 3) boards(1)(y)(x) = 2
          else boards(1)(y)(x) = 1
          grids(1).draw(boards(1))
        }
        playerTurn = boardNumber
      }
    }

  def onRelease(boardNumber: Int, x: Int, y: Int): Unit = {
    if (phase == 0 || phase == 1){
      if (boardNumber == phase){
        if (startY == y){
          for (i <- math.min(x, startX) to math.max(x, startX)){
            boards(phase)(y)(i) = 3
          }
        } else if (startX == x){
          for (i <- math.min(y, startY) to math.max(y, startY)){
            boards(phase)(i)(x) = 3
          }
        } else {
          return
        }
        boatPlaced += 1
        grids(phase).draw(boards(phase), showBoat = true)
        if (boatPlaced == maxBoat){
          grids(phase).draw(boards(phase))
          boatPlaced = 0
          phase += 1
        }
      }
    }
  }
}