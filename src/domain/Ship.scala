package domain

/**
 * Représente un navire sur la grille
 * @param positions toutes les cases occupées par le navire
 * @param hits les cases du navire qui ont été touchées
 */
final case class Ship(
                       positions: Set[Position],
                       hits: Set[Position] = Set.empty
                     ) {

  /** Taille du navire (nombre de cases) */
  def size: Int = positions.size

  /**
   * Vérifie si le navire occupe une position donnée
   */
  def occupies(position: Position): Boolean =
    positions.contains(position)

  /**
   * Enregistre un tir sur le navire
   * @return Un nouveau navire avec le tir enregistré
   */
  def hit(position: Position): Ship =
    if (occupies(position)) copy(hits = hits + position)
    else this

  /**
   * Vérifie si le navire est coulé
   * (toutes ses cases ont été touchées)
   */
  def isSunk: Boolean =
    hits.size == positions.size
}

object Ship {

  /**
   * Crée un navire à partir d'une position de départ et d'une orientation
   * @param startX position X de départ
   * @param startY position Y de départ
   * @param size taille du navire
   * @param orientation horizontal ou vertical
   */
  def create(startX: Int, startY: Int, size: Int, orientation: Orientation): Ship = {
    val positions = orientation match {
      case Orientation.Horizontal =>
        (0 until size).map(i => Position(startX + i, startY)).toSet
      case Orientation.Vertical =>
        (0 until size).map(i => Position(startX, startY + i)).toSet
    }
    Ship(positions)
  }
}

