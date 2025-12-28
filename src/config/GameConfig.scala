package config

object GameConfig {

  // Taille de la grille (grille carrée)
  val GRID_SIZE: Int = 10

  // Nombre total de navires par joueur
  val NUMBER_OF_SHIPS: Int = 4

  // Tailles des navires (en nombre de cases)
  val SHIP_SIZES: List[Int] = List(2, 3, 3, 4)

  // États possibles d’une case
  val EMPTY: Int = 0
  val SHIP: Int = 1
  val HIT: Int = 2
  val MISS: Int = 3

}
