class Game (caseSize: Int = 100){
  private val board1 = Array.ofDim[Int](10, 10) // 0 not shooted, 1 not touched, 2 touched, 3 boat
  private val board2 = Array.ofDim[Int](10, 10)

  private val grid1 = new Grid("Player 1", caseSize, (x, y) => onClick(1, x, y))
  private val grid2 = new Grid("Player 2", caseSize, (x, y) => onClick(2, x, y))

  private var playerTurn = 1

  def start(): Unit = {
    grid1.draw(board1)
    grid2.draw(board2)
  }

  def onClick(numPlayer: Int, x: Int, y: Int): Unit = {
    if (numPlayer != playerTurn) {
      println("Not your turn !")
      return
    }

    numPlayer match {
      case 1 =>
        if (board1(y)(x) == 3)
          board1(y)(x) = 2
        else
          board1(y)(x) = 1
        grid1.draw(board1)
        playerTurn = 2
      case 2 =>
        if (board2(y)(x) == 3)
          board2(y)(x) = 2
        else
          board2(y)(x) = 1
        grid2.draw(board2)
        playerTurn = 1
    }
  }
}
