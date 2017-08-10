package org.som.actors

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import org.messages.ResetMessage
import org.messages.ResetSuccess

import akka.actor.Actor
import akka.actor.actorRef2Scala

class RstActor extends Actor {
  val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()

  def receive = {
    case ResetMessage => Try(clipboard.setContents(new StringSelection(""), new StringSelection(""))) match {
      case Success(_)         => sender ! ResetSuccess
      case Failure(exception) => throw exception
    }
    case _ => System.out.println("Uh oh in reset ...")
  }
}