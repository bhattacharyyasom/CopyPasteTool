package org.som.console

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.som.listeners.KeyListener

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