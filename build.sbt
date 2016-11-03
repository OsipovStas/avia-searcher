name := "spark-hw-sbt"

version := "1.0"

scalaVersion := "2.10.6"

parallelExecution in Test := false

resolvers += "clojars.org" at "http://clojars.org/repo"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.2" % "provided"
//libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.2" % "provided"
//libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "1.6.2" % "provided"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.2" % "provided"
//libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.2" % "provided"
//libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.8.2.1"
//libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.2" % "provided"


libraryDependencies += "com.typesafe.play" % "play-ws_2.10" % "2.4.8"
libraryDependencies += "com.typesafe.play" % "play-json_2.10" % "2.4.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
