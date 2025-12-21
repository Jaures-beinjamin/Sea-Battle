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
  player1.onClick = (x, y) => {
    println(x, y)
    array(y)(x) = 1
    player1.draw(array)
  }

  player1.draw(array)
}