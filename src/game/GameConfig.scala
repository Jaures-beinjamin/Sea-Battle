package game

object GameConfig {

  /** Taille de la grille (10x10) */
  val GRID_SIZE: Int = 10

  /** Nombre total de navires par joueur */
  val NUMBER_OF_SHIPS: Int = 4

  /** Tailles des navires (en nombre de cases) */
  val SHIP_SIZES: List[Int] = List(2, 3, 3, 4)

  /** Symboles pour l'affichage */
  val SYMBOL_EMPTY: String = "~"
  val SYMBOL_SHIP: String = "S"
  val SYMBOL_HIT: String = "X"
  val SYMBOL_MISS: String = "O"

}
