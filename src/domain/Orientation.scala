package domain

/**
 * Orientation d'un navire sur la grille
 */
sealed trait Orientation

object Orientation {
  /** Navire placé horizontalement (de gauche à droite) */
  case object Horizontal extends Orientation

  /** Navire placé verticalement (de haut en bas) */
  case object Vertical extends Orientation
}

