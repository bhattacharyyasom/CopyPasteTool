import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

name := "CopyPasteTool"

organization := "org.som"
version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

TaskKey[Unit]("checkScalariform") := {
  val diff = "git diff".!!
  if(diff.nonEmpty){
    sys.error("Working directory is dirty!\n" + diff)
  }
}

val akkaActorAPIVersion        = "2.3.15"
val scalazCoreVersion          = "7.1.11"
val scalacticVersion           = "2.2.6"
val scalaTestVersion           = "2.2.6"
val scalaMockVersion           = "3.2.2"
val springLibraryVersion       = "4.1.6.RELEASE"
val guavaVersion               = "14.0.1"
val gsonVersion                = "2.3"
val commonsLangVersion         = "2.5"
val commonsIOVersion           = "1.4"
val commonsCodecVersion        = "1.6"
val commonsCollectionVersion   = "3.2.2"
val commonsLoggingVersion      = "1.1.1"
val openCSVVersion             = "2.3"
val xstreamVersion             = "1.4.8"
val httpComponentsVersion      = "4.3.3"
val jmdnsVersion               = "0.2"
val javaxMailVersion           = "1.5.4"
val junitVersion               = "4.12"
val jnativeHookVersion         = "2.1.0"



resolvers ++= Seq(
                  "Typesafe Repository"               at "http://repo.typesafe.com/typesafe/releases/",
                  "SpringSource Milestone Repository" at "http://repo.spring.io/libs-milestone-local"
)



libraryDependencies ++= Seq(
                       "com.typesafe.akka"          % "akka-actor_2.11"                         % akkaActorAPIVersion,
                       "com.typesafe.akka"          % "akka-testkit_2.11"                       % akkaActorAPIVersion,
                       "org.scalaz"                 % "scalaz-core_2.11"                        % scalazCoreVersion,
		       "org.scalaz.stream" % "scalaz-stream_2.11" % "0.8.6",
		       "org.scalaz" % "scalaz-concurrent_2.11" % "7.1.11",
		       "org.typelevel" % "scodec-bits_2.11" % "1.0.4",
		       "org.scalacheck" % "scalacheck_2.11" % "1.12.6" % "test",
		       "org.scalaz" % "scalaz-scalacheck-binding_2.11" % "7.1.11" % "test",
                       "org.scalactic"              % "scalactic_2.11"                          % scalacticVersion ,
                       /*Dependencies for CDCL May not be necessary later*/
                       "org.springframework"        % "spring-context"                          % springLibraryVersion,
                       "org.springframework"        % "spring-beans"                            % springLibraryVersion,
                       "org.springframework"        % "spring-core"                             % springLibraryVersion,
                       "org.springframework"        % "spring-context"                          % springLibraryVersion,
                       "org.springframework"        % "spring-web"                              % springLibraryVersion,
                       "com.google.guava"           % "guava"                                   % guavaVersion,
                       "com.google.code.gson"       % "gson"                                    % gsonVersion,
                       "commons-lang"               % "commons-lang"                            % commonsLangVersion,
                       "net.sf.opencsv"             % "opencsv"                                 % openCSVVersion,
                       "com.thoughtworks.xstream"   % "xstream"                                 % xstreamVersion,
                       "commons-io"                 % "commons-io"                              % commonsIOVersion,
                       "commons-codec"              % "commons-codec"                           % commonsCodecVersion,
                       "commons-collections"        % "commons-collections"                     % commonsCollectionVersion,
                       "commons-logging"            % "commons-logging"                         % commonsLoggingVersion,
                       "org.apache.httpcomponents"  % "httpclient"                              % httpComponentsVersion,
                       "org.apache.httpcomponents"  % "httpmime"                                % httpComponentsVersion,
                       "jmdns"                      % "jmdns"                                   % jmdnsVersion,
                       "com.sun.mail"               % "javax.mail"                              % javaxMailVersion,
		       "com.1stleg"                 % "jnativehook"                             % jnativeHookVersion,
                       /*Dependencies for test*/
                       "org.scalatest"              % "scalatest_2.11"                          % scalaTestVersion  ,
		       "org.specs2"                 % "specs2_2.11"                             % "2.4.15"         ,
                       "org.scalamock"              % "scalamock-scalatest-support_2.11"        % scalaMockVersion  % "test",
		       "junit"                      % "junit"                                   % junitVersion      % "test"
    )

scalaSource in Compile := baseDirectory.value / "src"


SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)
