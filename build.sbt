name := """infoshareapi"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, DebianPlugin, DockerPlugin,JavaServerAppPackaging,SystemdPlugin)


scalaVersion := "2.11.8"

val PhantomVersion = "2.3.1"

maintainer := "Boniface Kabaso <boniface@kabaso.com>"
packageSummary in Linux := "Infoshare REST API"
packageDescription := "Infoshare For Clients REST API "


javaOptions in Universal ++= Seq(
  // -J params will be added as jvm parameters
  "-J-Xms4g",
  "-J-Xmx4g",
  "-J-Xmn2g",

  // Since play uses separate pidfile we have to provide it with a proper path
  // name of the pid file must be play.pid
  s"-Dpidfile.path=/var/run/${packageName.value}/play.pid",

  // alternative, you can remove the PID file
  // s"-Dpidfile.path=/dev/null",

//  // Use separate configuration file for production environment
//  s"-Dconfig.file=/usr/share/${packageName.value}/conf/production.conf",
//
//  // Use separate logger configuration file for production environment
//  s"-Dlogger.file=/usr/share/${packageName.value}/conf/production-logger.xml",
//
//  // You may also want to include this setting if you use play evolutions
//  "-DapplyEvolutions.default=true",

  // others will be added as app parameters
  "-Dproperty=true",
  "-port=8080",

  // you can access any build setting/task here
  s"-version=${version.value}"
)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws
)
libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" % "scalatestplus-play_2.11" % "2.0.0"
libraryDependencies += "javax.mail" % "javax.mail-api" % "1.5.6"
libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"

libraryDependencies += "com.outworkers" % "phantom-dsl_2.11" % PhantomVersion
libraryDependencies += "com.outworkers" % "phantom-streams_2.11" % PhantomVersion
libraryDependencies += "com.outworkers" % "phantom-connectors_2.11" % PhantomVersion

libraryDependencies += "com.github.karelcemus" % "play-redis_2.11" % "1.3.1"
libraryDependencies += "com.github.romix.akka" % "akka-kryo-serialization_2.11" % "0.5.0"
libraryDependencies += "com.esotericsoftware" % "kryo" % "4.0.0"
libraryDependencies += "com.github.t3hnar" % "scala-bcrypt_2.11" % "3.0"


libraryDependencies += "junit" % "junit" % "4.12"
libraryDependencies += "me.lessis" % "base64_2.11" % "0.2.0"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.5"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"


libraryDependencies += "com.rometools" % "rome" % "1.7.1"

libraryDependencies += "com.gravity.goose" % "goose_2.11" % "2.2.8"



resolvers ++= Seq(
  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "softprops-maven" at "http://dl.bintray.com/content/softprops/maven",
  "Brando Repository" at "http://chrisdinn.github.io/releases/",
  "Sonatype repo" at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging" at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Twitter Repository" at "http://maven.twttr.com",
  "Websudos releases" at "https://dl.bintray.com/websudos/oss-releases/",
  "Goose Updates " at "https://dl.bintray.com/raisercostin/maven/",
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("public")
)


