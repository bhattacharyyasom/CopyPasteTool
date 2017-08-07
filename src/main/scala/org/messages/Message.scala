package org.messages

sealed abstract trait Message
case class CopyMessage(rank: Int) extends Message
case class PasteMessage(rank: Int) extends Message
case class UpdateClipboard(rank: Int, str: String) extends Message
case class RequestClipboardItem(rank: Int) extends Message