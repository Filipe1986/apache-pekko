package com.filipe;

import org.apache.pekko.actor.testkit.typed.javadsl.ActorTestKit;
import org.apache.pekko.actor.testkit.typed.javadsl.TestProbe;
import org.apache.pekko.actor.typed.ActorRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class RequestSimpleActorTest {

    private ActorTestKit testKit;

    @BeforeEach
    public void setup() {
        testKit = ActorTestKit.create();
    }

    @RepeatedTest(5)
    void test() {
        ActorRef<Command> simpleActor = testKit.spawn(RequestSimpleActor.create());
        TestProbe<String> replyToActorTestProbe = testKit.createTestProbe();
        simpleActor.tell(new RequestCommand("First message", replyToActorTestProbe.getRef()));
        var response = replyToActorTestProbe.receiveMessage();

        Assertions.assertTrue(response.contains("Response to: First message"),
                "Log message should contain 'Received command: First message'");

    }

}