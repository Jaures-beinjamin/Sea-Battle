# Sea Battle – Bataille Navale en Scala

## Description du projet

Ce projet consiste en la réalisation d’un **jeu vidéo de bataille navale (Sea Battle)** développé en **Scala**, dans le cadre d’un **d'un projet equipe**. L’application s’appuie sur la librairie **FunGraphics** afin de gérer l’interface graphique ainsi que les interactions avec l’utilisateur.

Le jeu met en scène une confrontation stratégique entre **deux joueurs**, dont l’objectif est de **couler l’ensemble des navires adverses** avant que les siens ne soient détruits.

---

## Objectifs pédagogiques

Ce Project vise à :

* Mettre en pratique les **concepts fondamentaux du langage Scala**
* Utiliser une **librairie graphique** pour créer une interface interactive (FunGraphics)
* Concevoir et structurer une application en **programmation orientée objet**
* Gérer les **événements utilisateur** (clics, tours de jeu, interactions)
* Implémenter une **logique de jeu complète** (règles, états, conditions de victoire)

---

## Principe du jeu

* Le jeu se déroule sur une **grille maritime**
* Chaque joueur dispose de **4 navires**, placés sur la grille
* Deux joueurs s’affrontent en **mode tour par tour**
* À chaque tour, un joueur peut **tirer une seule fois** sur une case de la grille adverse
* Un navire est considéré comme coulé lorsque toutes ses cases ont été touchées
* Le gagnant est le joueur qui **coule tous les navires de son adversaire**

### État d’un tir

* **Touché**

La partie prend fin lorsqu’un joueur ne possède plus aucun navire en état de flotter.

---

## Fonctionnalités principales

* Affichage graphique de la grille de jeu avec **FunGraphics**
* Placement des navires sur la grille
* Gestion des tirs des joueurs
* Alternance automatique des tours
* Détection des coups réussis et des navires coulés
* Affichage du résultat final de la partie (**victoire / défaite**)

---

## Technologies utilisées

* **Langage** : Scala
* **Librairie graphique** : FunGraphics
* **Paradigme** : Programmation Orientée Objet

---

---

##  Lancement du jeu

1. Vérifier que **Scala** est installé
2. Importer le projet dans un IDE (IntelliJ recommandé)
3. Vérifier que la librairie **FunGraphics** est bien configurée
4. Lancer le fichier `Main.scala`

---

##  Règles du jeu

* Un tir par tour
* Impossible de tirer deux fois sur la même case
* Les navires occupent plusieurs cases consécutives
* La victoire est déclarée lorsque tous les navires adverses sont coulés

---

##  Aperçu 


---

##  Améliorations possibles

* Mode deux joueurs
* Placement manuel des navires
* Niveau de difficulté
* Effets sonores
* Animation des tirs

---

##  Auteur

* **Nom** : Jaures Beinjamin - Samuel
* **Cadre** : TP – Création d’un jeu vidéo en Scala

---

##  Licence

Projet réalisé dans un cadre pédagogique.

---

*Bon jeu et bonne exploration de Scala !*
