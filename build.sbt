import sbt._
import sbt.Keys._

organization      in ThisBuild := "fr.thomasdufour"
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "2.13.1"
// TODO when I can make sense of lm-coursier
conflictManager   in ThisBuild                         := ConflictManager.strict
conflictManager   in updateSbtClassifiers in ThisBuild := ConflictManager.default

enablePlugins( DependenciesPlugin )

val `splain-by-name-nosplain` = project
  .settings( libraryDependencies ++= cats ++ kittens )

val `splain-by-name-yessplain` = project
  .settings( libraryDependencies ++= splain ++ cats ++ kittens )

val `splain-by-name-all` = project
  .in( file( "." ) )
  .aggregate( `splain-by-name-nosplain`, `splain-by-name-yessplain` )
