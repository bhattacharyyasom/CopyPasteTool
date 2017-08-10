package org.som.actors

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import org.messages.PasteMessage
import org.messages.PasteSuccess

import akka.actor.Actor
import akka.actor.actorRef2Scala

class PasteActor extends Actor {

  def receive = {
    case PasteMessage(text) =>
      val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
      Try(clipboard.setContents(new StringSelection(text), new StringSelection(text))) match {
        case Success(_)         => sender ! PasteSuccess
        case Failure(exception) => self ! PasteMessage(text)
      }
    case _ => System.out.println("Uh oh in paste ....")
  }
}