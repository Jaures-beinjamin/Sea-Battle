import scala.collection.mutable.ArrayBuffer

sealed trait CellState
case object Empty extends CellState
case object Miss extends CellState
case object Hit extends CellState
case object Sink extends CellState

sealed trait GamePhase
case object ShipPlacement extends GamePhase
case object Battle extends GamePhase
case object GameOver extends GamePhase

class Game(numPlayers: Int, caseSize: Int = 100, shipSize: Array[Int] = Array(1, 2, 3)) {
  private val boards: Array[Array[Array[CellState]]] = Array.fill(numPlayers)(Array.fill(10, 10)(Empty))
  private val grids = new Array[Grid](numPlayers)
  private val ships: Array[ArrayBuffer[Ship]] = Array.fill(numPlayers)(ArrayBuffer.empty)
  for (i <- 0 until numPlayers) {
    println(s"Grid for Player ${i + 1} created")
    grids(i) = new Grid(s"Player ${i + 1}", caseSize, (x, y) => onPress(i, x, y), (x, y) => onRelease(i, x, y))
  }

  private var phase: GamePhase = _
  private var playerTurn: Int = _

  // Ships placement variable
  private var shipPlaced = 0
  private var startX = 0
  private var startY = 0

  def start(): Unit = {
    println("Game started")
    for (i <- 0 until numPlayers) {
      boards(i) = Array.fill(10, 10)(Empty)
      grids(i).draw(boards(i))
    }
    phase = ShipPlacement
    playerTurn = 0
    println(s"Player ${playerTurn + 1} place ship, size = ${shipSize(0)}")
  }

  private def shot(numPlayer: Int, x: Int, y: Int): Unit = {
    var hitSomething = false
    for (ship <- ships(numPlayer)) {
      val hit = ship.shot(x, y)
      if (hit) {
        hitSomething = true
        boards(numPlayer)(y)(x) = Hit
        if (ship.isSunk) {
          println(s"Hit at $x, $y and SUNK!")
          for (loc <- ship.getLocations){
            boards(numPlayer)(loc(1))(loc(0)) = Sink
          }
        }
        else println(s"Hit at $x, $y")
      }
    }
    if (!hitSomething) {
      println(s"Miss at ($x, $y)")
      boards(numPlayer)(y)(x) = Miss
    }
  }

  private def isAlive(numPlayer: Int): Boolean = {
    for (ship <- ships(numPlayer)) {
      if (!ship.isSunk) return true
    }
    false
  }

  def onPress(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case ShipPlacement =>
        if (boardNumber == playerTurn) {
          startX = x
          startY = y
        }
      case Battle =>
        if (boardNumber != playerTurn) {
          if (boards(boardNumber)(y)(x) != Empty) {
            println(s"You already shot at $x, $y! Try another cell.")
            return
          }

          println(s"Shot on Player ${boardNumber + 1} $x, $y")
          shot(boardNumber, x, y)

          val isStillAlive = isAlive(boardNumber)
          if (!isStillAlive) {
            println(s"Player ${boardNumber + 1} is eliminated!")
            grids(boardNumber).eliminated()
          } else {
            grids(boardNumber).draw(boards(boardNumber))
          }

          var numPlayerAlive = 0
          var lastPlayerAlive = -1
          for (i <- 0 until numPlayers) {
            if (isAlive(i)) {
              numPlayerAlive += 1
              lastPlayerAlive = i
            }
          }

          if (numPlayerAlive == 1) {
            phase = GameOver
            grids(lastPlayerAlive).winner()
            println(s"Player ${lastPlayerAlive + 1} is the winner!!!")
            println(s"Click on a grid to play again")
          } else {
            do {
              playerTurn = (playerTurn + 1) % numPlayers
            } while (!isAlive(playerTurn))
            println(s"Player ${playerTurn + 1} can shoot")
          }
        } else {
          println("You can't shoot your own grid!")
        }
      case GameOver =>
        start()
      case _ => ()
    }
  }

  def onRelease(boardNumber: Int, x: Int, y: Int): Unit = {
    if (phase == ShipPlacement && boardNumber == playerTurn) {
      val ship = new Ship
      if (startY == y || startX == x) {
        ship.place(startX, startY, x, y, shipSize(shipPlaced))
        ships(playerTurn) += ship
      } else {
        return
      }
      shipPlaced += 1
      grids(boardNumber).draw(boards(boardNumber), ships(playerTurn).toArray)
      if (shipPlaced == shipSize.length) {
        grids(boardNumber).draw(boards(boardNumber))
        shipPlaced = 0
        if (playerTurn == numPlayers - 1) phase = Battle
        if (playerTurn == numPlayers - 1) {
          playerTurn = 0
          println(s"Player ${playerTurn + 1} can shoot")
        } else {
          playerTurn += 1
          println(s"Player ${playerTurn + 1} place ships, size = ${shipSize(0)}")
        }
      } else {
        println(s"Next ship, size = ${shipSize(shipPlaced)}")
      }
    }
  }
}