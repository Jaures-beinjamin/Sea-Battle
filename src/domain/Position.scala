package domain

/**
 * Représente une position sur la grille
 * @param x coordonnée horizontale (0 à 9)
 * @param y coordonnée verticale (0 à 9)
 */
final case class Position(x: Int, y: Int) {

  /**
   * Affiche la position sous forme lisible
   * Exemple: Position(3, 5) => "(3, 5)"
   */
  override def toString: String = s"($x, $y)"
}

