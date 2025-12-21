import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}
import javax.swing.SwingConstants

class Grid(caseSize: Int = 150) {
  val fg: FunGraphics = new FunGraphics(12 * caseSize, 12 * caseSize)
  var onClick: (Int, Int) => Unit = (x, y) => {}

  fg.addMouseListener(new MouseAdapter {
    override def mouseClicked(e: MouseEvent): Unit = {
      val x = e.getX / caseSize - 1
      val y = e.getY / caseSize - 1
      if (x >= 0 && x < 10 && y >= 0 && y < 10) {
        onClick(x, y) // callBack
      }
    }
  })

  def draw(grid: Array[Array[Int]]): Unit = {
    fg.clear()
    for (y <- grid.indices){
      fg.drawString((y + 1) * caseSize + caseSize / 2, caseSize, ('A'.toInt + y).toChar.toString, halign = SwingConstants.CENTER, fontSize = caseSize / 2)
      fg.drawString(caseSize / 2, (y + 1) * caseSize + caseSize / 2, (y + 1).toString, valign = SwingConstants.CENTER, fontSize = caseSize / 2)
      for (x <- grid(y).indices){
        val value = grid(y)(x)
        value match {
          case 1 =>
            fg.setColor(Color.blue)
          case 2 =>
            fg.setColor(Color.red)
          case 3 =>
            fg.setColor(Color.green)
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
  }
}