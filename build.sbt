name := """Brandmedia"""

version := "1.0-SNAPSHOT"

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
  "com.github.t3hnar" %% "scala-bcrypt" % "2.6"
)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
