ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "srbm-notebook"
  )

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "org.wit.srbm" % "srbm-spark-lite" % "1.0-SNAPSHOT",
  "org.apache.spark" % "spark-core_2.13" % "3.3.0",
  "org.apache.spark" % "spark-streaming_2.13" % "3.3.0",
  "org.apache.spark" % "spark-sql_2.13" % "3.3.0",
  "org.apache.spark" % "spark-mllib_2.13" % "3.3.0"
)






