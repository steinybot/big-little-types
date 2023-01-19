scalaVersion := "2.13.10"

enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)

scalacOptions += "-Ymacro-annotations"

scalaJSUseMainModuleInitializer := true

// ScalaJSBundler only seems to support this for tests for some reason.
//jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
Test / requireJsDomEnv := true
installJsdom / version := "21.0.0"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "2.1.0",
  "me.shadaj" %%% "slinky-web" % "0.7.3"
)

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.15" % "test"

Compile / npmDependencies ++= Seq(
  "react" -> "16.12.0",
  "react-dom" -> "16.12.0"
)

Compile / npmDevDependencies ++= Seq(
  "source-map-support" -> "0.5.21"
)
