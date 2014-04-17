name := "playground"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.2.2"
)

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions += "-feature"