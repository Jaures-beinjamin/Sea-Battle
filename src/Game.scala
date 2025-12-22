class Game (caseSize: Int = 100){
  private val boards = Array(Array.ofDim[Int](10, 10), Array.ofDim[Int](10, 10)) // 0: empty, 1: miss, 2: hit, 3: ship
  private val grids = Array(
    new Grid("Player 1", caseSize, (x, y) => onClick(0, x, y)),
    new Grid("Player 2", caseSize, (x, y) => onClick(1, x, y))
  )

  private var phase = 0 // 0: P1 ship placement, 1: P2 ship placement, 2: battle phase
  private val maxBoat = 5
  private var boatPlaced = 0
  private var playerTurn = 0

  def start(): Unit = {
    grids(0).draw(boards(0))
    grids(1).draw(boards(1))
  }

  def onClick(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case 0 | 1 =>
        if (boardNumber == phase){
          boards(phase)(y)(x) = 3
          boatPlaced += 1
          grids(phase).draw(boards(phase), showBoat = true)
          if (boatPlaced == maxBoat){
            grids(phase).draw(boards(phase))
            boatPlaced = 0
            phase += 1
          }
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
}