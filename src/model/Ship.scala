package model

final case class Ship( positions: Set[Position],  hits: Set[Position] = Set.empty)
{

  /** Taille du navire */
  def size: Int = positions.size

  /** Vérifie si le navire occupe une position */
  def occupies(position: Position): Boolean =
    positions.contains(position)

  /** Enregistre un impact sur le navire */
  def hit(position: Position): Ship =
    if (occupies(position))
      copy(hits = hits + position)
    else
      this

  /** Vérifie si le navire est coulé */
  def isSunk: Boolean =
    hits.size == positions.size
}
