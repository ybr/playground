name := "playground"

version := "0.0.12"

organization := "ybr"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.2.3",
  "com.typesafe.play" %% "anorm" % "2.2.3"
)

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions += "-feature"
