package org.som.actors

import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import scala.util.Try
import org.jnativehook.keyboard.NativeKeyEvent
import akka.actor.Actor
import scala.util.Success
import scala.util.Failure

class KeyPressedActor extends Actor {

  val COPY_CODE = KeyEvent.VK_C
  val PASTE_CODE = KeyEvent.VK_L
  val RESET_CODE = KeyEvent.VK_N
  val copyQueue = new scala.collection.mutable.Queue[String]()
  val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()

  def receive = {
    case event: NativeKeyEvent =>

      if (event.paramString().contains("modifiers=Ctrl")) {
        if (event.getRawCode.equals(COPY_CODE)) {
          copyDataToClipboard()
        }
        if (event.getRawCode.equals(RESET_CODE)) {
          copyQueue.clear()
          clipboard.setContents(new StringSelection(""), new StringSelection(""))
        }
        if (event.getRawCode.equals(PASTE_CODE)) {
          pasteDataToClipboard()
        }
      }
    case _ => System.out.println("Bummer !  You are NOT supposed to see THIS !")
  }

  def copyDataToClipboard(): Unit = {
    val clipBoardMessage = clipboard.getData(DataFlavor.stringFlavor).toString()
    if (clipBoardMessage.length == 0) {
      return
    }
    copyQueue.enqueue(clipBoardMessage)
    clipboard.setContents(new StringSelection(""), new StringSelection(""))
  }

  def copyFromClipboardToPlatform() = {
    val robot: Robot = new Robot()
    robot.keyPress(KeyEvent.VK_CONTROL)
    robot.keyPress(KeyEvent.VK_V)
    robot.keyRelease(KeyEvent.VK_V)
    robot.keyRelease(KeyEvent.VK_CONTROL)
    //clipboard.setContents(new StringSelection(""), new StringSelection(""))
  }

  def pasteDataToClipboard() = {
    Try(copyQueue.dequeue()) map (x => clipboard.setContents(new StringSelection(x), new StringSelection(x))) match {
      case Success(a)   => System.out.println("Success in paste.")
      case Failure(exc) => exc.printStackTrace()
    }
    copyFromClipboardToPlatform()
  }
}