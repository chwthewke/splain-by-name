import sbt._
import sbt.Keys._
import sbt.librarymanagement.DependencyBuilders

object DependenciesPlugin extends AutoPlugin {
  type Deps = Seq[ModuleID]

  object autoImport {
    type Deps = DependenciesPlugin.Deps

    implicit def ToStringOps( orgName: String ): StringOps = new StringOps( orgName )

    implicit def ToDbOanOps( dbOans: Seq[DbOan] ): DbOanOps = new DbOanOps( dbOans )

    implicit def ToGroupOps( deps: Deps ): GroupOps = new GroupOps( deps )

    val kindProjector: Deps =
      Seq( compilerPlugin( "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full ) )

    val splain: Deps = Seq( compilerPlugin( "io.tryp" % "splain" % "0.4.1" cross CrossVersion.patch ) )

    val betterMonadicFor: Deps = Seq( compilerPlugin( "com.olegpy" %% "better-monadic-for" % "0.3.1" ) )

    val catsVersion    = "2.0.0"
    val cats: Deps     = "org.typelevel" %% Seq( "cats-core", "cats-kernel", "cats-macros" ) % catsVersion
    val catsFree: Deps = Seq( "org.typelevel" %% "cats-free" % catsVersion )
    val catsMtl: Deps  = Seq( "org.typelevel" %% "cats-mtl-core" % "0.7.0" )
    val mouse: Deps    = Seq( "org.typelevel" %% "mouse" % "0.23" )
    val kittens: Deps  = Seq( "org.typelevel" %% "kittens" % "2.0.0" )

    val catsEffect: Deps = Seq( "org.typelevel" %% "cats-effect" % "2.0.0" )

    val fs2: Deps = "co.fs2" %% Seq( "fs2-core", "fs2-io" ) % "2.0.1"

    val http4sVersion           = "0.21.0-M5"
    val http4s: Deps            = Seq( "org.http4s" %% "http4s-dsl" % http4sVersion )
    val http4sBlazeServer: Deps = Seq( "org.http4s" %% "http4s-blaze-server" % http4sVersion )
    val http4sBlazeClient: Deps = Seq( "org.http4s" %% "http4s-blaze-client" % http4sVersion )

    val monocleVersion       = "2.0.0"
    val monocle: Deps        = "com.github.julien-truffaut" %% Seq( "monocle-core", "monocle-macro" ) % monocleVersion
    val monocleState: Deps   = Seq( "com.github.julien-truffaut" %% "monocle-state" % monocleVersion )
    val monocleGeneric: Deps = Seq( "com.github.julien-truffaut" %% "monocle-generic" % monocleVersion )

    val circeVersion      = "0.12.2"
    val circe: Deps       = "io.circe" %% Seq( "circe-core", "circe-generic", "circe-parser" ) % circeVersion
    val circeOptics: Deps = Seq( "io.circe" %% "circe-optics" % "0.12.0" )
    val circeFs2: Deps    = Seq( "io.circe" %% "circe-fs2" % "0.12.0" )

    val enumeratum: Deps =
      Seq( "com.beachape" %% "enumeratum" % "1.5.13", "com.beachape" %% "enumeratum-cats" % "1.5.16" )
    val enumeratumCirce: Deps = Seq( "com.beachape" %% "enumeratum-circe" % "1.5.22" )

    val shapeless: Deps = Seq( "com.chuusai" %% "shapeless" % "2.3.3" )

    val java8compat: Deps = Seq( "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0" )
    val scalaXml: Deps    = Seq( "org.scala-lang.modules" %% "scala-xml"          % "1.2.0" )

    val logging: Deps = Seq( "org.slf4j" % "slf4j-api" % "1.7.28", "ch.qos.logback" % "logback-classic" % "1.2.3" )

    val pureconfigVersion = "0.12.1"
    val pureconfig: Deps = "com.github.pureconfig" %% Seq(
      "pureconfig-core",
      "pureconfig-cats",
      "pureconfig-cats-effect"
    ) % pureconfigVersion

    val pureconfigEnumeratum: Deps = Seq( "com.github.pureconfig" %% "pureconfig-enumeratum" % pureconfigVersion )
    val pureconfigFs2: Deps        = Seq( "com.github.pureconfig" %% "pureconfig-fs2"        % pureconfigVersion )
// TODO
//    val pureconfigHttp4s: Deps        = Seq( "com.github.pureconfig" %% "pureconfig-http4s" % pureconfigVersion )

    private[DependenciesPlugin] val typesafeConfig: Deps = Seq( "com.typesafe" % "config" % "1.4.0" )

    val decline: Deps = "com.monovore" %% Seq( "decline", "decline-effect" ) % "1.0.0"

    val doobieVersion             = "0.8.4"
    val doobie: Deps              = "org.tpolecat" %% Seq( "doobie-core", "doobie-free" ) % doobieVersion
    val doobiePostgres: Deps      = "org.tpolecat" %% Seq( "doobie-postgres", "doobie-hikari" ) % doobieVersion
    val doobiePostgresCirce: Deps = Seq( "org.tpolecat" %% "doobie-postgres-circe" % doobieVersion )
    val doobieH2: Deps            = Seq( "org.tpolecat" %% "doobie-h2" % doobieVersion )
    val doobieScalatest: Deps     = Seq( "org.tpolecat" %% "doobie-scalatest" % doobieVersion )

    val postgresql: Deps = Seq( "org.postgresql" % "postgresql"  % "42.2.8" )
    val h2database: Deps = Seq( "com.h2database" % "h2"          % "1.4.200" )
    val flywayCore: Deps = Seq( "org.flywaydb"   % "flyway-core" % "6.0.6" )

    val scalatest: Deps = Seq( "org.scalatest" %% "scalatest" % "3.0.8" )

    val scalacheck: Deps =
      Seq(
        "org.scalacheck"    %% "scalacheck"      % "1.14.2",
        "io.chrisdavenport" %% "cats-scalacheck" % "0.2.0"
      )

    val autoDiffVersion          = "0.4.0"
    val autoDiff: Deps           = "fr.thomasdufour" %% Seq( "auto-diff-core", "auto-diff-generic" ) % autoDiffVersion
    val autoDiffEnumeratum: Deps = Seq( "fr.thomasdufour" %% "auto-diff-enumeratum" % autoDiffVersion )
    val autoDiffScalatest: Deps  = Seq( "fr.thomasdufour" %% "auto-diff-scalatest" % autoDiffVersion )
  }

  import autoImport._

  def allModules: Deps =
    cats ++
      catsFree ++
      catsMtl ++
      mouse ++
      catsEffect ++
      fs2 ++
      http4s ++
      http4sBlazeServer ++
      http4sBlazeClient ++
      kittens ++
      monocle ++
      monocleState ++
      monocleGeneric ++
      circe ++
      circeFs2 ++
      circeOptics ++
      enumeratum ++
      enumeratumCirce ++
      shapeless ++
      java8compat ++
      scalaXml ++
      logging ++
      pureconfig ++
      pureconfigEnumeratum ++
      pureconfigFs2 ++
//      pureconfigHttp4s ++
      typesafeConfig ++
      decline ++
      doobie ++
      doobiePostgres ++
      doobiePostgresCirce ++
      doobieH2 ++
      doobieScalatest ++
      postgresql ++
      h2database ++
      flywayCore ++
      scalatest ++
      scalacheck ++
      autoDiff ++
      autoDiffEnumeratum ++
      autoDiffScalatest

  /*
  override def buildSettings: Seq[Def.Setting[_]] =
    dependencyOverrides in ThisBuild ++= Seq(
      "org.scala-lang" % "scala-library"  % scalaVersion.value,
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect"  % scalaVersion.value
    ) ++ allModules
  */
  
  type DbOan = DependencyBuilders.OrganizationArtifactName

  class GroupOps( val self: Seq[ModuleID] ) extends AnyVal {
    def exclude( org: String, name: String ): Seq[ModuleID] =
      self.map( _.exclude( org, name ) )

    def %( configurations: String ): Seq[ModuleID] =
      self.map( _ % configurations )

    def classifier( c: String ): Seq[ModuleID] =
      self.map( _ classifier c )
  }

  class StringOps( val self: String ) extends AnyVal {
    def %%( artifactIds: Seq[String] ): Seq[DbOan] = artifactIds.map( self %% _ )
  }

  class DbOanOps( val self: Seq[DbOan] ) extends AnyVal {
    def %( revision: String ): Deps = self.map( _ % revision )
  }

}
