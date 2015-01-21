import sbt.Keys._
import play.PlayJava

name := """Etu-Moria"""

version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  jdbc,
  javaWs,
  javaEbean,
  anorm,
  cache
)

resolvers ++= Seq(
"Apache" at "http://repo1.maven.org/maven2/"
)

publishArtifact in (Compile, packageDoc) := false

publishArtifact in packageDoc := false

sources in (Compile,doc) := Seq.empty


