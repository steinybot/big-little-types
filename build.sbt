import scalajsbundler.JSDOMNodeJSEnv

val Scala_2 = "2.13.10"
val Scala_3 = "3.2.1"

ThisBuild / scalaVersion := Scala_2
ThisBuild / crossScalaVersions := List(Scala_2, Scala_3)

lazy val root = project.in(file("."))
  .aggregate(demoJS, demoJVM)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val demo = crossProject(JSPlatform, JVMPlatform).in(file("demo"))
  .settings(
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) => Seq("-Ymacro-annotations")
        case _ => Seq("-indent", "-rewrite", "-explain")
      }
    },
    libraryDependencies ++= Seq(
      "eu.timepit" %%% "refined" % "0.10.1",
      "org.scalatest" %%% "scalatest" % "3.2.15" % Test
    ),
    Test / parallelExecution := false
  )
  .jvmSettings()
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    // ScalaJSBundler only seems to support this for tests for some reason.
    //jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
    Test / requireJsDomEnv := true,
    Test / fastLinkJS / scalaJSLinkerConfig ~= {
      _
        // FIXME: This doesn't seem to work
        //.withModuleKind(ModuleKind.CommonJSModule)
        .withSourceMap(true)
    },
    Test / jsEnv := {
      val defaultJSEnv = (Test / jsEnv).value
      val optJsdomDir = Def.taskDyn[Option[File]] {
        if ((Test / requireJsDomEnv).value)
          (Test / installJsdom).map(Some(_))
        else
          Def.task(None)
      }.value
      optJsdomDir match {
        case Some(jsdomDir) =>
          new JSDOMNodeJSEnv(
            JSDOMNodeJSEnv.Config(jsdomDir)
            // Use this to debug Node.js.
            //.withArgs("--trace-warnings" :: List("--inspect-brk")))
          )
        case None =>
          defaultJSEnv
      }
    },
    Test / webpackConfigFile := Some(baseDirectory.value / "test.webpack.config.js"),
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
