val Http4sVersion = "0.20.8"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"

lazy val doobieVersion = "0.8.4"
lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

organization := "com.example"
name := "server_functional"

val thirdDependencies = Seq(
    "io.circe"        %% "circe-generic"       % CirceVersion,
    "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
    "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,

    scalaTest % Test,
    "com.github.nscala-time" %% "nscala-time" % "2.22.0",

    // http4s
    "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
    "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
    "org.http4s"      %% "http4s-circe"        % Http4sVersion,
    "org.http4s"      %% "http4s-dsl"          % Http4sVersion,

    //  Cats
    "org.typelevel" %% "cats-core" % "2.0.0-M4",
    "org.typelevel" %% "cats-effect" % "1.3.1",

    //  Refined
    "eu.timepit" %% "refined"                 % "0.9.10",
    "eu.timepit" %% "refined-cats"            % "0.9.10",

    //  Doobie
    "org.tpolecat" %% "doobie-core"     % doobieVersion,
    "org.tpolecat" %% "doobie-postgres" % doobieVersion,
    "org.tpolecat" %% "doobie-specs2"   % doobieVersion
  )


val commonsSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.8"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)

lazy val domain = (project in file("subprojects/domain"))
  .settings(
    name := "domain subproject",
    commonsSettings,
    libraryDependencies ++= thirdDependencies
  )

lazy val infrastructure = (project in file("subprojects/infrastructure"))
  .dependsOn(domain % "test->test;compile->compile")
  .settings(
    name := "infrastructure subproject",
    commonsSettings,
    libraryDependencies ++= thirdDependencies
  )

lazy val application = (project in file("subprojects/application"))
  .dependsOn(domain % "test->test;compile->compile", infrastructure % "test->test;compile->compile")
  .settings(
    name := "application subproject",
    commonsSettings,
    libraryDependencies ++= thirdDependencies
  )

lazy val learning = (project in file("subprojects/learning"))
  .settings(
    name := "learning subproject",
    commonsSettings,
    libraryDependencies ++= thirdDependencies
  )

lazy val ui = (project in file("subprojects/ui"))
  .aggregate(learning, domain, infrastructure, application)
  .settings(
    commonsSettings,
    libraryDependencies ++= thirdDependencies
  )

lazy val root = (project in file("."))
  .aggregate(learning, domain, infrastructure, application, ui)
  .settings(
    commonsSettings,
    libraryDependencies ++= thirdDependencies,
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.0")
  )
