package model

/** Résultat d'un tir */
sealed trait ShotResult

object ShotResult {
  case object Miss extends ShotResult  // Tir raté
  case object Hit extends ShotResult   // Tir touché mais navire pas coulé
  case object Sunk extends ShotResult  // Navire entièrement coulé
}
