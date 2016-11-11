name := "avia-searcher"

version := "1.0"

scalaVersion := "2.10.6"

parallelExecution in Test := false

resolvers += "clojars.org" at "http://clojars.org/repo"
resolvers += "Apache HBase" at "https://repository.apache.org/content/repositories/releases"

resolvers += "Thrift" at "http://people.apache.org/~rawson/repo/"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.2" % "provided"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.2" % "provided"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.2" % "provided"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.8.2.1"
libraryDependencies += "org.apache.kafka" % "connect-api" % "0.10.0.1"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-core" % "0.20.2",
  "org.apache.hbase" % "hbase" % "1.2.3",
  "org.apache.hbase" % "hbase-common" % "1.2.3",
  "org.apache.hbase" % "hbase-client" % "1.2.3"
)
//libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.2" % "provided"

libraryDependencies += "com.typesafe.play" % "play-ws_2.10" % "2.4.8"
libraryDependencies += "com.typesafe.play" % "play-json_2.10" % "2.4.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
