name := "spark-hw-sbt"

version := "1.0"

scalaVersion := "2.10.6"

parallelExecution in Test := false

resolvers += "clojars.org" at "http://clojars.org/repo"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
libraryDependencies += "com.typesafe.play" % "play-ws_2.10" % "2.4.8"
libraryDependencies += "com.typesafe.play" % "play-json_2.10" % "2.4.8"
    