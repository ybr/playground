name := "playground"

version := "0.0.8"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.2.2",
  "com.typesafe.play" %% "anorm" % "2.2.2"
)

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions += "-feature"
