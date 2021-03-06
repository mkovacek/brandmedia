name := """Brandmedia"""

version := "1.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

javaOptions ++= Seq("-javaagent:/newrelic/newrelic.jar")

libraryDependencies ++= Seq(
  cache,
  ws,
  filters,
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.github.t3hnar" %% "scala-bcrypt" % "2.6",
  "com.pauldijou" %% "jwt-play" % "0.8.0",
  "org.json4s" %% "json4s-native" % "3.3.0"
)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

sources in (Compile, doc) := Seq.empty

publishArtifact in (Compile, packageDoc) := false