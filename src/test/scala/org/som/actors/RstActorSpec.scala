package org.som.actors

import org.messages.ResetMessage
import org.messages.ResetSuccess
import org.scalatest.BeforeAndAfterAll
import org.scalatest.WordSpecLike

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.testkit.ImplicitSender
import akka.testkit.TestKit

class RstActorSpec extends TestKit(ActorSystem("CopyPasteSystem")) with ImplicitSender with WordSpecLike with BeforeAndAfterAll {

  "A Reset actor" must {
    "send a ResetSuccess message back when sent a ResetMessage".in {
      val actor = system.actorOf(Props[RstActor], name = "rstActor")
      actor ! ResetMessage
      expectMsg(ResetSuccess)
    }
  }

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
}