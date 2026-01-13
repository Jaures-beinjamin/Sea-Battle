import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}
import javax.swing.SwingConstants

class Grid(title: String, caseSize: Int = 100, onPress: (Int, Int) => Unit, onRelease: (Int, Int) => Unit) {
  private val fg: FunGraphics = new FunGraphics(12 * caseSize, 12 * caseSize)

  fg.addMouseListener(new MouseAdapter {
    override def mousePressed(e: MouseEvent): Unit = {
      val x = e.getX / caseSize - 1
      val y = e.getY / caseSize - 1
      if (x >= 0 && x < 10 && y >= 0 && y < 10) {
        onPress(x, y) // callBack
      }
    }

    override def mouseReleased(e: MouseEvent): Unit = {
      val x = e.getX / caseSize - 1
      val y = e.getY / caseSize - 1
      if (x >= 0 && x < 10 && y >= 0 && y < 10) {
        onRelease(x, y) // callBack
      }
    }
  })

  def draw(grid: Array[Array[CellState]], ships: Array[Ship] = Array.empty): Unit = {
    fg.clear()
    fg.drawString(caseSize * 6, caseSize / 2, title, halign = SwingConstants.CENTER, fontSize = caseSize / 2)
    for (y <- grid.indices) {
      fg.drawString((y + 1) * caseSize + caseSize / 2, caseSize, ('A'.toInt + y).toChar.toString, halign = SwingConstants.CENTER, fontSize = caseSize / 2)
      fg.drawString(caseSize / 2, (y + 1) * caseSize + caseSize / 2, (y + 1).toString, valign = SwingConstants.CENTER, fontSize = caseSize / 2)
      for (x <- grid(y).indices) {
        val value = grid(y)(x)
        value match {
          case Miss =>
            fg.setColor(Color.blue)
          case Hit =>
            fg.setColor(Color.red)
          case _ =>
            fg.setColor(Color.white)
        }
        fg.drawFillRect((x + 1) * caseSize, (y + 1) * caseSize, caseSize, caseSize)
        fg.setColor(Color.black)
        fg.drawLine((x + 1) * caseSize, (y + 1) * caseSize, (x + 1) * caseSize, (y + 2) * caseSize)
        fg.drawLine((x + 1) * caseSize, (y + 1) * caseSize, (x + 2) * caseSize, (y + 1) * caseSize)
        fg.drawLine((x + 2) * caseSize, (y + 1) * caseSize, (x + 2) * caseSize, (y + 2) * caseSize)
        fg.drawLine((x + 1) * caseSize, (y + 2) * caseSize, (x + 2) * caseSize, (y + 2) * caseSize)
      }
    }
    for (ship <- ships){
      for (loc <- ship.getLocations){
        fg.setColor(Color.green)
        fg.drawFillRect((loc(0) + 1) * caseSize, (loc(1) + 1) * caseSize, caseSize, caseSize)
      }
    }
  }

  def eliminated(): Unit = {
    fg.setColor(Color.red)
    fg.drawFillRect(0, 0, 12 * caseSize, 12 * caseSize)
    fg.drawString(caseSize * 6, caseSize / 2, title, halign = SwingConstants.CENTER, fontSize = caseSize / 2, color = Color.white)
  }

  def winner(): Unit = {
    fg.setColor(Color.green)
    fg.drawFillRect(0, 0, 12 * caseSize, 12 * caseSize)
    fg.drawString(caseSize * 6, caseSize / 2, title, halign = SwingConstants.CENTER, fontSize = caseSize / 2, color = Color.white)
  }
}