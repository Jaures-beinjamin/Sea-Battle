package domain

/**
 * Représente un joueur de bataille navale
 * @param name nom du joueur
 * @param grid grille du joueur
 * @param ships ensemble des navires du joueur
 * @param shots ensemble des tirs effectués par le joueur
 */
final case class Player(
                         name: String,
                         grid: Grid,
                         ships: Set[Ship] = Set.empty,
                         shots: Set[Position] = Set.empty
                       ) {

  /**
   * Vérifie si tous les navires du joueur sont coulés
   * (condition de défaite)
   */
  def allShipsSunk: Boolean =
    ships.forall(_.isSunk)

  /**
   * Enregistre un nouveau tir effectué par le joueur
   * @return Un nouveau joueur avec le tir enregistré
   */
  def registerShot(position: Position): Player =
    copy(shots = shots + position)

  /**
   * Ajoute un navire au joueur
   * @return Un nouveau joueur avec le navire ajouté
   */
  def addShip(ship: Ship): Player =
    copy(ships = ships + ship)

  /**
   * Trouve le navire présent à une position donnée
   * @return Some(navire) si un navire est présent, None sinon
   */
  def shipAt(position: Position): Option[Ship] =
    ships.find(_.occupies(position))
}

