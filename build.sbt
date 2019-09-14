enablePlugins(GatlingPlugin)

lazy val gatling = Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts",
  "io.gatling" % "gatling-test-framework"
).map(_ % "3.2.1" % Test)

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "info.ludwikowski",
      scalaVersion := "2.12.9",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "perftests",
    libraryDependencies ++= gatling
  )
