package model

/** Résultat d’un tir */
enum ShotResult {
  case Miss      // Tir raté
  case Hit       // Tir touché mais navire pas coulé
  case Sunk      // Navire entièrement coulé
}
