name := "cont-aio"

version := "0.0.1"

scalaVersion := "2.9.2"

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.1")

scalacOptions ++= Seq("-P:continuations:enable")