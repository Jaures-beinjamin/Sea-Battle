name := "Sea-Battle"

version := "1.0"

scalaVersion := "2.13.18"

// Dépendances
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test

// Ajouter funGraphics comme JAR non géré
Compile / unmanagedJars += baseDirectory.value / "src" / "funGraphics-1.5.20.jar"

// Configuration des répertoires source
Compile / scalaSource := baseDirectory.value / "src"

// Configuration pour créer un JAR exécutable
Compile / mainClass := Some("Main")

