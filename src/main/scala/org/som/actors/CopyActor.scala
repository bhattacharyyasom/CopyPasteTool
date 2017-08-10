package org.som.actors

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import org.messages.CopyMessage
import org.messages.CopySuccess
import akka.actor.Actor
import akka.actor.actorRef2Scala
import org.messages.PasteMessage
import java.awt.datatransfer.StringSelection
import org.messages.PasteSuccess

class CopyActor extends Actor {

  def receive = {
    case CopyMessage =>
      val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
      Try(clipboard.getData(DataFlavor.stringFlavor).toString()) match {
        case Success(a)         => sender ! CopySuccess(a)
        case Failure(exception) => self ! CopyMessage
      }
    case PasteMessage(text) =>
      val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
      Try(clipboard.setContents(new StringSelection(""), new StringSelection(text))) match {
        case Success(_)         => sender ! PasteSuccess
        case Failure(exception) => self ! PasteMessage(text)
      }
    case _ => System.out.println("Uh oh in copy ......")
  }
}