import scala.collection.mutable.ArrayBuffer

class Ship {
  private var locations: ArrayBuffer[Array[Int]] = ArrayBuffer[Array[Int]]()
  private var status: ArrayBuffer[Boolean] = ArrayBuffer[Boolean]()

  def place(startX: Int, startY: Int, x: Int, y: Int, size: Int): Unit = {
    if (startY == y) {
      for (i <- 0 until size) {
        locations += Array(startX + i, y)
        status += false
      }
    } else if (startX == x) {
      for (i <- 0 until size) {
        locations += Array(x, startY + i)
        status += false
      }
    }
  }

  def shot(x: Int, y: Int): Boolean = {
    for (i <- locations.indices) {
      if (locations(i)(0) == x && locations(i)(1) == y) {
        if (!status(i)) {
          status(i) = true
          return true
        }
      }
    }
    false
  }

  def getLocations: Array[Array[Int]] = {
    locations.toArray
  }

  def isSunk: Boolean = {
    status.forall(s => s)
  }
}