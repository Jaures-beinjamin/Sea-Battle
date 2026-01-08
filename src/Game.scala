import scala.collection.mutable.ArrayBuffer

sealed trait CellState
case object Empty extends CellState
case object Miss extends CellState
case object Hit extends CellState
case object Ship_old extends CellState

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

  def onPress(boardNumber: Int, x: Int, y: Int): Unit = {
    phase match {
      case ShipPlacement =>
        if (boardNumber == playerTurn) {
          startX = x
          startY = y
        }
      case Battle =>
        if (boardNumber != playerTurn) {
          println(s"Shot on Player ${boardNumber + 1} $x, $y")
          if (boards(boardNumber)(y)(x) == Ship_old) {
            boards(boardNumber)(y)(x) = Hit
            println("Hit")
          } else if (boards(boardNumber)(y)(x) == Empty) {
            boards(boardNumber)(y)(x) = Miss
            println("Miss")
          }
          else {
            println("Already shot here, try again")
            return
          }

          val isStillAlive = boards(boardNumber).exists(row => row.contains(Ship_old))
          if (!isStillAlive) {
            println(s"Player ${boardNumber + 1} is eliminated!")
            grids(boardNumber).eliminated()
          } else {
            grids(boardNumber).draw(boards(boardNumber))
          }

          var numPlayerAlive = 0
          var lastPlayerAlive = -1
          for (i <- 0 until numPlayers) {
            if (boards(i).exists(row => row.contains(Ship_old))) {
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
            playerTurn = (playerTurn + 1) % numPlayers
            while (!boards(playerTurn).exists(row => row.contains(Ship_old))) {
              playerTurn = (playerTurn + 1) % numPlayers
            }
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