package org

import akka.actor.ActorSystem
package object som {
  implicit val copyActorSystem = ActorSystem("CopyPasteActorSystem")

  implicit class OpsNum(val str: String) extends AnyVal {
    def isNumeric() = scala.util.Try(str.toDouble).isSuccess
  }
}