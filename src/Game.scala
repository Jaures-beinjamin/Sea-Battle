class Game (caseSize: Int = 100){
  private val board1 = Array.ofDim[Int](10, 10)
  private val board2 = Array.ofDim[Int](10, 10)

  private val grid1 = new Grid(caseSize)
  grid1.onClick = (x, y) => {
    println(x, y)
    board1(y)(x) = 1
    grid1.draw(board1)
  }

  private val grid2 = new Grid(caseSize)
  grid2.onClick = (x, y) => {
    println(x, y)
    board2(y)(x) = 1
    grid2.draw(board2)
  }

  def start(): Unit = {
    grid1.draw(board1)
    grid2.draw(board2)
  }
}
