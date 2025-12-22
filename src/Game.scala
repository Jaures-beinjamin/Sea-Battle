class Game (caseSize: Int = 100){
  private val board1 = Array.ofDim[Int](10, 10) // 0: empty, 1: miss, 2: hit, 3: ship
  private val board2 = Array.ofDim[Int](10, 10)

  private val grid1 = new Grid("Player 1", caseSize, (x, y) => onClick(1, x, y))
  private val grid2 = new Grid("Player 2", caseSize, (x, y) => onClick(2, x, y))

  private var phase = 1 // 1: P1 ship placement, 2: P2 ship placement, 3: battle phase
  private val maxBoat = 5
  private var boatPlaced = 0
  private var playerTurn = 1

  def start(): Unit = {
    grid1.draw(board1)
    grid2.draw(board2)
  }

  def onClick(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case 1 =>
        if (boardNumber == 1){
          board1(y)(x) = 3
          boatPlaced += 1
          grid1.draw(board1, showBoat = true)
          if (boatPlaced == maxBoat){
            phase = 2
            boatPlaced = 0
            grid1.draw(board1)
          }
        }
      case 2 =>
        if (boardNumber == 2){
          board2(y)(x) = 3
          boatPlaced += 1
          grid2.draw(board2, showBoat = true)
          if (boatPlaced == maxBoat){
            phase = 3
            boatPlaced = 0
            grid2.draw(board2)
          }
        }
      case 3 =>
        if (boardNumber == playerTurn) return
        if (boardNumber == 1){
          if (board1(y)(x) == 3) board1(y)(x) = 2
          else board1(y)(x) = 1
          grid1.draw(board1)
        } else if (boardNumber == 2){
          if (board2(y)(x) == 3) board2(y)(x) = 2
          else board2(y)(x) = 1
          grid2.draw(board2)
        }
        playerTurn = boardNumber
      }
    }
}