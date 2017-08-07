package org.som.console

import org.jnativehook.NativeHookException
import org.jnativehook.GlobalScreen
import org.som.listeners.KeyListener
import akka.actor.ActorSystem
import akka.actor.Props
import org.som._

/**
 * This app object is the main stub that registers listeners for the entire screen
 *
 * @author shankarb
 *
 */
object ConsoleApp extends App {
  try {
    GlobalScreen.registerNativeHook();
    GlobalScreen.addNativeKeyListener(KeyListener)
  } catch {
    case nativehookException: NativeHookException => System.out.println("Encountered error while Hooking to global screen" + nativehookException.fillInStackTrace())
    case ex: Exception                            => System.out.println("Miserable ! Shouldn't have happened ." + ex.fillInStackTrace())
  }
}