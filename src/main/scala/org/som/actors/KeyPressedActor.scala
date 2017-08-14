package org.som.actors

import java.awt.Robot
import java.awt.event.KeyEvent

import scala.concurrent.duration.DurationInt

import org.jnativehook.keyboard.NativeKeyEvent
import org.messages.CopyMessage
import org.messages.CopySuccess
import org.messages.PasteMessage
import org.messages.PasteSuccess
import org.messages.ResetMessage

import akka.actor.Actor
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.SupervisorStrategy
import akka.actor.actorRef2Scala

class KeyPressedActor extends Actor {

  val COPY_CODE = KeyEvent.VK_C
  val PASTE_CODE = KeyEvent.VK_U
  val RESET_CODE = KeyEvent.VK_N
  val copyQueue = new scala.collection.mutable.Queue[String]()

  val copyActor = context.actorOf(Props[CopyActor](), "CopyActor")
  val rstActor = context.actorOf(Props[RstActor], "RstActor")

  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: IllegalStateException => SupervisorStrategy.Restart
      case exc: Exception           => SupervisorStrategy.Stop
    }

  def receive = {
    case event: NativeKeyEvent =>
      if (event.paramString().contains("modifiers=Ctrl")) {
        if (event.getRawCode == COPY_CODE) {
          copyActor ! CopyMessage
        }
        if (event.getRawCode == RESET_CODE) {
          copyQueue.clear()
          rstActor ! ResetMessage
        }
        if (event.getRawCode == PASTE_CODE) {
          copyActor ! PasteMessage(copyQueue.front)
        }
      }
    case CopySuccess(text) => if (text.length != 0) {
      copyQueue.enqueue(text)
    }
    case PasteSuccess =>
      copyQueue.dequeue()
      copyFromClipboardToPlatform
    case _ => System.out.println("Bummer !  You are NOT supposed to see THIS !")
  }

  def copyFromClipboardToPlatform() = {
    val robot: Robot = new Robot()
    robot.keyPress(KeyEvent.VK_CONTROL)
    robot.keyPress(KeyEvent.VK_V)
    robot.keyRelease(KeyEvent.VK_V)
    robot.keyRelease(KeyEvent.VK_CONTROL)
  }
}