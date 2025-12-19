import hevs.graphics.FunGraphics

class Grid() {
  val fg: FunGraphics = new FunGraphics(1200, 1200)

  def draw(grid: Array[Array[Int]]): Unit = {
    // Horizontal
    fg.drawLine(100, 100, 1100, 100)
    fg.drawLine(100, 200, 1100, 200)
    fg.drawLine(100, 300, 1100, 300)
    fg.drawLine(100, 400, 1100, 400)
    fg.drawLine(100, 500, 1100, 500)
    fg.drawLine(100, 600, 1100, 600)
    fg.drawLine(100, 700, 1100, 700)
    fg.drawLine(100, 800, 1100, 800)
    fg.drawLine(100, 900, 1100, 900)
    fg.drawLine(100, 1000, 1100, 1000)
    fg.drawLine(100, 1100, 1100, 1100)

    // Vertical
    fg.drawLine(100, 100, 100, 1100)
    fg.drawLine(200, 100, 200, 1100)
    fg.drawLine(300, 100, 300, 1100)
    fg.drawLine(400, 100, 400, 1100)
    fg.drawLine(500, 100, 500, 1100)
    fg.drawLine(600, 100, 600, 1100)
    fg.drawLine(700, 100, 700, 1100)
    fg.drawLine(800, 100, 800, 1100)
    fg.drawLine(900, 100, 900, 1100)
    fg.drawLine(1000, 100, 1000, 1100)
    fg.drawLine(1100, 100, 1100, 1100)
  }
}