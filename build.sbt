name := "Sea-Battle"

version := "1.0"

scalaVersion := "2.13.18"

// Dépendances
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test

// Configuration des répertoires source
Compile / scalaSource := baseDirectory.value / "src"

// Configuration pour créer un JAR exécutable
Compile / mainClass := Some("main.Main")

