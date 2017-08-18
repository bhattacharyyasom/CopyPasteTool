package org.som.actors

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

import org.messages.CopyMessage
import org.messages.CopySuccess
import org.messages.PasteMessage
import org.messages.PasteSuccess
import org.scalatest.BeforeAndAfterAll
import org.scalatest.WordSpecLike

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.testkit.ImplicitSender
import akka.testkit.TestKit

class CopyActorSpec extends TestKit(ActorSystem("CopyPasteSystem")) with ImplicitSender with WordSpecLike with BeforeAndAfterAll {
  val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
  clipboard.setContents(new StringSelection(""), new StringSelection(""))

  "A Copy actor" must {
    "send a copy success message back when sent a CopyMessage".in {
      val actor = system.actorOf(Props[CopyActor], name = "copyActor")
      clipboard.setContents(new StringSelection("copytest"), new StringSelection("copytest"))
      actor ! CopyMessage
      expectMsg(CopySuccess("copytest"))
    }
  }

  "A Copy actor" must {
    "send a paste success message back when sent a PasteMessage".in {
      val actor = system.actorOf(Props[CopyActor], name = "pasteActor")
      clipboard.setContents(new StringSelection("copytest"), new StringSelection("copytest"))
      actor ! PasteMessage("copytest")
      expectMsg(PasteSuccess)
    }
  }

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
}