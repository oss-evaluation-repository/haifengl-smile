name := "smile-serve"

packageOptions += Package.ManifestAttributes("Automatic-Module-Name" -> "smile.serve")

Compile / mainClass := Some("smile.serve.Main")

// native packager
enablePlugins(JavaAppPackaging)
maintainer := "Karl Li <kkli@umich.edu>"
packageName := "smile-serve"
packageSummary := "LLM Serving by SMILE"

// dealing with long classpaths
scriptClasspath := Seq("*")

executableScriptName := "smile-serve"
bashScriptConfigLocation := Some("${app_home}/../conf/smile-serve.ini")
batScriptConfigLocation := Some("%APP_HOME%\\conf\\smile-serve.ini")

bashScriptExtraDefines ++= Seq(
  """addJava "-Dsmile.home=${app_home}/.."""",
  """addJava "-Dscala.usejavacp=true""""
)

batScriptExtraDefines ++= Seq(
  """call :add_java -Dsmile.home=%APP_HOME%""",
  """call :add_java -Dscala.usejavacp=true""",
  """call :add_java -Djava.library.path=%APP_HOME%\bin""",
  """set OPENBLAS_NO_AVX512=1""",
  """set OPENBLAS_NUM_THREAD=1""",
  """set PATH=!PATH!;%~dp0"""
)

// BuildInfo
enablePlugins(BuildInfoPlugin)
buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoPackage := "smile.serve"
buildInfoOptions += BuildInfoOption.BuildTime

libraryDependencies ++= Seq(
  "ch.qos.logback"    %  "logback-classic" % "1.5.6",
  "com.github.scopt"  %% "scopt" % "4.1.0",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3"
)

val os = sys.props.get("os.name").get.toLowerCase.split(" ")(0)
val gpu = Seq(
  "org.bytedeco"   % "pytorch" % "2.3.0-1.5.11-SNAPSHOT" classifier s"$os-x86_64-gpu",
  "org.bytedeco"   % "cuda" % "12.3-8.9-1.5.11-SNAPSHOT" classifier s"$os-x86_64-redist"
)

libraryDependencies ++= (
  os match {
    case "linux" | "windows" => gpu
    case _ => Seq.empty
  }
)
