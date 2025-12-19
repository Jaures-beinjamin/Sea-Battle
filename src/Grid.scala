import hevs.graphics.FunGraphics

import java.awt.Color
import javax.swing.SwingConstants

class Grid() {
  val caseSize = 20
  val fg: FunGraphics = new FunGraphics(12 * caseSize, 12 * caseSize)

  def draw(grid: Array[Array[Int]]): Unit = {
    for (y <- grid.indices){
      fg.drawString((y + 1) * caseSize + caseSize / 2, caseSize, ('A'.toInt + y).toChar.toString, halign = SwingConstants.CENTER, fontSize = caseSize / 2)
      fg.drawString(caseSize / 2, (y + 1) * caseSize + caseSize / 2, ('A'.toInt + y).toChar.toString, valign = SwingConstants.CENTER, fontSize = caseSize / 2)
      for (x <- grid(y).indices){
        fg.setColor(Color.black)
        fg.drawLine((x + 1) * caseSize, (y + 1) * caseSize, (x + 1) * caseSize, (y + 2) * caseSize)
        fg.drawLine((x + 1) * caseSize, (y + 1) * caseSize, (x + 2) * caseSize, (y + 1) * caseSize)
        fg.drawLine((x + 2) * caseSize, (y + 1) * caseSize, (x + 2) * caseSize, (y + 2) * caseSize)
        fg.drawLine((x + 1) * caseSize, (y + 2) * caseSize, (x + 2) * caseSize, (y + 2) * caseSize)
        val value = grid(y)(x)
        value match {
          case 1 =>
            fg.setColor(Color.blue)
            fg.drawFilledCircle((x + 1) * caseSize, (y + 1) * caseSize, caseSize)
          case 2 =>
            fg.setColor(Color.red)
            fg.drawFilledCircle((x + 1) * caseSize, (y + 1) * caseSize, caseSize)
          case _ =>
        }
      }
    }
  }
}