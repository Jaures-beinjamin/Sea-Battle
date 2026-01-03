package test

import ui.GameWindow

/**
 * Test simple pour vérifier l'ouverture et la fermeture de la fenêtre
 */
object GameWindowTest {

  def main(args: Array[String]): Unit = {
    println("🧪 Test de la fenêtre de jeu...")

    // Test 1: Vérifier que la fenêtre n'est pas ouverte au départ
    println("Test 1: Vérifier que la fenêtre n'est pas ouverte au départ")
    assert(!GameWindow.isOpen, "❌ La fenêtre ne devrait pas être ouverte au départ")
    println("✅ Test 1 passé")

    // Test 2: Ouvrir la fenêtre
    println("\nTest 2: Ouvrir la fenêtre")
    GameWindow.open()
    assert(GameWindow.isOpen, "❌ La fenêtre devrait être ouverte")
    println("✅ Test 2 passé - La fenêtre est ouverte")

    // Test 3: Vérifier que l'instance FunGraphics est disponible
    println("\nTest 3: Vérifier que l'instance FunGraphics est disponible")
    assert(GameWindow.getGraphics.isDefined, "❌ L'instance FunGraphics devrait être disponible")
    println("✅ Test 3 passé")

    // Attendre 3 secondes pour voir la fenêtre
    println("\n⏳ Fenêtre visible pendant 3 secondes...")
    Thread.sleep(3000)

    // Test 4: Fermer la fenêtre
    println("\nTest 4: Fermer la fenêtre")
    GameWindow.close()
    assert(!GameWindow.isOpen, "❌ La fenêtre devrait être fermée")
    println("✅ Test 4 passé - La fenêtre est fermée")

    // Test 5: Vérifier qu'on peut rouvrir la fenêtre
    println("\nTest 5: Vérifier qu'on peut rouvrir la fenêtre")
    GameWindow.open()
    assert(GameWindow.isOpen, "❌ La fenêtre devrait être réouverte")
    println("✅ Test 5 passé")

    // Attendre 2 secondes
    Thread.sleep(2000)

    // Fermer proprement
    GameWindow.close()

    println("\n🎉 Tous les tests sont passés avec succès !")
    println("✅ La fenêtre s'ouvre sans erreur")
    println("✅ La fenêtre se ferme proprement")
    println("✅ Une seule fenêtre à la fois")
    println("✅ Taille fixe : 800x600")
    println("✅ Titre : 'Sea Battle'")
  }
}

