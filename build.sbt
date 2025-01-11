val tapirVersion = "1.11.9"

val tapirLibs = Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-jsoniter-scala" % tapirVersion
)

val jsoniterVersion = "2.30.14"
val jsonLibs = Seq(
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion
)

val zioLoggingVersion = "2.1.15"
val logLibs = Seq(
  "ch.qos.logback" % "logback-classic" % "1.5.12",
  "dev.zio" %% "zio-logging" % zioLoggingVersion,
  "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion
)

val testLibs = Seq(
  "dev.zio" %% "zio-test" % "2.0.13" % Test,
  "dev.zio" %% "zio-test-sbt" % "2.0.13" % Test,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirVersion % Test,
  "com.softwaremill.sttp.client3" %% "jsoniter" % "3.10.1" % Test
)

lazy val rootProject = (project in file(".")).settings(
  Seq(
    name := "booking-hotel-service",
    version := "0.1.0",
    organization := "com.renghen",
    scalaVersion := "3.5.2",
    libraryDependencies ++= tapirLibs ++ jsonLibs ++ logLibs ++ testLibs,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )
)
