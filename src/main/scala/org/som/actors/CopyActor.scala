package org.som.actors

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import org.messages.CopyMessage
import org.messages.CopySuccess
import org.messages.PasteMessage
import org.messages.PasteSuccess

import akka.actor.Actor
import akka.actor.actorRef2Scala

class CopyActor extends Actor {
  val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
  def receive = {
    case CopyMessage =>
      //Thread.sleep(1000) // Not recommended but for purposes of demo and as latency affects the system clipboard updates.
      Try(clipboard.getData(DataFlavor.stringFlavor).toString()) match {
        case Success(a)         => sender ! CopySuccess(a)
        case Failure(exception) => self ! CopyMessage
      }
    case PasteMessage(text) =>
      Try(clipboard.setContents(new StringSelection(text), new StringSelection(text))) match {
        case Success(_)         => sender ! PasteSuccess
        case Failure(exception) => self ! PasteMessage(text)
      }
    case _ => System.out.println("Uh oh in copy ......")
  }
}