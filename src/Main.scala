object Main extends App {
  private val array = Array(
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0,0)
  )

  val player1 = new Grid
  val player2 = new Grid

  player1.draw(array)
}