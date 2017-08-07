package org.som.listeners

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import org.jnativehook.keyboard.NativeKeyEvent
import org.som.actors.KeyPressedActor
import org.som.copyActorSystem

/**
 * The singleton listener object we are using to listen for events and delegate them to actors as needs be
 *
 * @author shankarb
 *
 */
object KeyListener extends org.jnativehook.keyboard.NativeKeyListener {
  val keyPressedActor = implicitly[ActorSystem].actorOf(Props[KeyPressedActor](), "KeyPressedActor")
  def nativeKeyPressed(event: NativeKeyEvent): Unit = {
    keyPressedActor ! event
  }
  def nativeKeyTyped(event: NativeKeyEvent): Unit = {
    System.out.print("test")
  }
  def nativeKeyReleased(event: NativeKeyEvent): Unit = {
    System.out.print("test")
  }
}