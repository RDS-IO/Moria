import sbt.Keys._
import play.PlayJava

name := """Etu-Moria"""

version := "1.0-SNAPSHOT"

organization := "com.rds"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  jdbc,
  javaWs,
  javaEbean,
  anorm,
  cache,
  "be.objectify" %% "deadbolt-java" % "2.3.2"
)

resolvers ++= Seq(
    "Apache" at "http://repo1.maven.org/maven2/",
    Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)
)

publishArtifact in (Compile, packageDoc) := false

publishArtifact in packageDoc := false

sources in (Compile,doc) := Seq.empty


