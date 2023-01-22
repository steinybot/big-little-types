val Scala_2_13 = "2.13.10"
val Scala_3 = "3.2.1"

lazy val root = project.in(file("."))
  .aggregate(demoJS, demoJVM)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val demo = crossProject(JSPlatform, JVMPlatform).in(file("demo"))
  .settings(
    scalaVersion := Scala_2_13,
    crossScalaVersions := List(Scala_2_13, Scala_3),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) => Seq("-Ymacro-annotations")
        case _ => Seq.empty
      }
    },
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.15" % Test,
    Test / parallelExecution := false
  )
  .jvmSettings()
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    // ScalaJSBundler only seems to support this for tests for some reason.
    //jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
    Test / requireJsDomEnv := true,
    installJsdom / version := "21.0.0",
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.1.0",
      "me.shadaj" %%% "slinky-web" % "0.7.3"
    ),
    Compile / npmDependencies ++= Seq(
      "react" -> "16.12.0",
      "react-dom" -> "16.12.0"
    ),
    Compile / npmDevDependencies ++= Seq(
      "source-map-support" -> "0.5.21"
    )
  )

lazy val demoJS = demo.js
  .enablePlugins(ScalaJSBundlerPlugin)

lazy val demoJVM = demo.jvm
