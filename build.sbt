name := "scala-csv-parser"

version := "0.1"
scalaVersion := "2.12.6"

val circeVersion = "0.9.3"
val catsVersion = "1.3.1"
val http4sVersion = "0.19.0-SNAPSHOT"

// Plugins & options:

scalacOptions += "-Ypartial-unification"
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

// Resolvers:

resolvers += Resolver.sonatypeRepo("snapshots")

// Dependencies:

// libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.13.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core",
  "org.typelevel" %% "cats-kernel",
  "org.typelevel" %% "cats-macros"
).map(_  % catsVersion)
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)