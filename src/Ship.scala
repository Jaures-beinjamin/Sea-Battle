import scala.collection.mutable.ArrayBuffer

class Ship {
  var locations: ArrayBuffer[Array[Int]] = ArrayBuffer[Array[Int]]()

  def place(startX: Int, startY: Int, x: Int, y: Int, size: Int): Unit = {
    if (startY == y) {
      for (i <- 0 until size) {
        locations += Array(startX + i, y)
      }
    } else if (startX == x) {
      for (i <- 0 until size) {
        locations += Array(x, startY + i)
      }
    }
  }

  def shot(x: Int, y: Int): Boolean = {
    false // return Hit or not
  }
}
