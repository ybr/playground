name := "playground"

version := "0.0.13"

organization := "ybr"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.3.3" % "provided",
  "com.typesafe.play" %% "anorm" % "2.3.3" % "provided",
  "com.typesafe.play" %% "play-jdbc" % "2.3.3" % "provided"
)

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions += "-feature"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.10.0", "2.11.0")
