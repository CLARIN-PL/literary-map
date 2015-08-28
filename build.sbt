import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

maintainer in Linux := "Wojciech Gaweł <wojciech.gawel@pwr.edu.pl>"

packageSummary in Linux := "Literary Map"

packageDescription := "Literary Map extract object localization from plain text."

name := """litmap"""

version := "1.1.3-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "bootstrap" % "3.1.1-2",
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc4",
  "com.google.code.geocoder-java" % "geocoder-java" % "0.15"
)

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)

buildInfoPackage := "pl.edu.pwr.litmap"

fork in run := false