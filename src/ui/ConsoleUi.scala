package ui

import domain.{Player, Position, Grid}

/**
 * Interface utilisateur en mode console
 * Gère tous les affichages et interactions avec le joueur
 */
object ConsoleUI {

  /**
   * Affiche un message de bienvenue
   */
  def showWelcome(): Unit = {
    println("====================================")
    println("   🚢 BATAILLE NAVALE 🚢")
    println("====================================")
    println()
  }

  /**
   * Affiche le tour d'un joueur
   */
  def showPlayerTurn(playerName: String): Unit = {
    println(s"\n--- Tour de $playerName ---")
  }

  /**
   * Demande au joueur de saisir des coordonnées de tir
   * Format attendu : "x y" (exemple : "3 5")
   */
  def askForShot(): Position = {
    println("Entrez les coordonnées de tir (format: x y) :")
    print("> ")

    val input = scala.io.StdIn.readLine().split(" ")

    try {
      val x = input(0).toInt
      val y = input(1).toInt
      Position(x, y)
    } catch {
      case _: Exception =>
        println("❌ Format invalide ! Réessayez.")
        askForShot() // Redemander en cas d'erreur
    }
  }

  /**
   * Affiche le résultat d'un tir
   */
  def showShotResult(result: String): Unit = {
    result match {
      case "Hit" => println("💥 TOUCHÉ !")
      case "Sunk" => println("💥💥 COULÉ !")
      case "Miss" => println("💦 À L'EAU !")
      case _ => println(s"Résultat: $result")
    }
  }

  /**
   * Affiche un message d'erreur
   */
  def showError(message: String): Unit = {
    println(s"⚠️  Erreur: $message")
  }

  /**
   * Affiche le gagnant de la partie
   */
  def showWinner(winnerName: String): Unit = {
    println("\n====================================")
    println(s"🎉 VICTOIRE DE $winnerName ! 🎉")
    println("====================================")
    println("Merci d'avoir joué !")
  }

  /**
   * Affiche la grille d'un joueur (pour debug)
   */
  def showGrid(player: Player): Unit = {
    println(s"\nGrille de ${player.name}:")
    // TODO: Implémenter l'affichage de la grille
    println("(Affichage de la grille à implémenter)")
  }
}

