package org.messages

sealed abstract trait Message
case class CopyMessage() extends Message
case class ResetMessage() extends Message
case class ResetSuccess() extends Message
case class CopySuccess(text: String) extends Message
case class PasteMessage(text: String) extends Message
case class PasteSuccess() extends Message