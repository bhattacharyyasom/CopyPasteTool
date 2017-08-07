package org.som.actors

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import org.jnativehook.keyboard.NativeKeyEvent
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Matchers
import org.scalatest.WordSpecLike
import akka.actor.ActorSystem
import akka.testkit.ImplicitSender
import akka.testkit.TestActorRef
import akka.testkit.TestKit
import java.awt.datatransfer.DataFlavor

class KeyPressedActorSpec extends TestKit(ActorSystem("MySpec")) with ImplicitSender
    with WordSpecLike {
  val actor = TestActorRef[KeyPressedActor]
  val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
  clipboard.setContents(new StringSelection(""), new StringSelection(""))

  "A Key Pressed Actor" should {
    "exact string in system clipboard to the copyQueue".in {
      clipboard.setContents(new StringSelection("copytest"), new StringSelection("copytest"))
      //This next event is a a CTRL +C mock if you will
      actor ! new NativeKeyEvent(2401, 2, 67, 46, '\0', 1)
      assert(actor.underlyingActor.copyQueue.dequeue().equals("copytest"))
    }
  }

  "A Key Pressed Actor" should {
    "paste the exact string to system clipboard from the copyQueue".in {
      clipboard.setContents(new StringSelection("copytest"), new StringSelection("copytest"))
      actor ! new NativeKeyEvent(2401, 2, 67, 46, '\0', 1) // Copy event
      actor ! new NativeKeyEvent(2401, 2, 76, 38, '\0', 1) // Paste event
      assert(clipboard.getData(DataFlavor.stringFlavor).toString().equals("copytest"))
    }
  }
}

