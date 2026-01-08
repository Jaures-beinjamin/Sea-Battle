object Main extends App {
  private val game = new Game(numPlayers = 2, caseSize = 50, shipSize = Array(3, 2, 1))
  game.start()
}