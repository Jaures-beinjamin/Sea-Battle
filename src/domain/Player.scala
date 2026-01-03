package domain

final case class Player(
                         name: String,
                         grid: Grid,
                         ships: Set[Ship] = Set.empty,
                         shots: Set[Position] = Set.empty
                       ) {

  /** Vérifie si tous les navires du joueur sont coulés */
  def allShipsSunk: Boolean =
    ships.forall(_.isSunk)

  /** Enregistre un tir sur l'adversaire */
  def registerShot(position: Position): Player =
    copy(shots = shots + position)

  /** Ajoute un navire au joueur */
  def addShip(ship: Ship): Player =
    copy(ships = ships + ship)

  /** Vérifie si une position touche un de ses navires */
  def shipAt(position: Position): Option[Ship] =
    ships.find(_.occupies(position))
}
