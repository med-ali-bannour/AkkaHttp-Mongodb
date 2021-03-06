import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.dali"
ThisBuild / organizationName := "akkahttp-mongodb"
ThisBuild / resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.bintrayRepo("hseeberger", "maven")
)

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "AkkaHttpProject",
    scalafmtOnCompile in Compile := true,
    unmanagedResourceDirectories in Compile += {
      baseDirectory.value / "src/main/resources"
    },
    packageName in Docker := "user-app",
    dockerExposedPorts := Seq(8080),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding",
      "UTF-8",
      "-Ypartial-unification",
      "-Ywarn-unused-import"
    ),
    libraryDependencies ++= Seq(
      scalaTest % Test,
      akkaHttp,
      circeGeneric,
      akkahttpCirce,
      akkaHttpTest,
      akkaStream,
      akkaTestkit,
      sl4j,
      logback,
      scalaLogging,
      scalamock,
      logback,
      mongo
    )
  )
