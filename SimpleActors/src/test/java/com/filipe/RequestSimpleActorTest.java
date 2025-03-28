package com.filipe;

import org.apache.pekko.actor.testkit.typed.javadsl.ActorTestKit;
import org.apache.pekko.actor.testkit.typed.javadsl.BehaviorTestKit;
import org.apache.pekko.actor.testkit.typed.javadsl.TestInbox;
import org.apache.pekko.actor.testkit.typed.javadsl.TestProbe;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.javadsl.AskPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

class RequestSimpleActorTest {

    private ActorTestKit testKit;

    @BeforeEach
    public void setup() {
        testKit = ActorTestKit.create();
    }

    @RepeatedTest(3)
    void tellTest() {
        ActorRef<Command> simpleActor = testKit.spawn(RequestSimpleActor.create());
        TestProbe<String> replyToActorTestProbe = testKit.createTestProbe();
        simpleActor.tell(new RequestCommand("First message", replyToActorTestProbe.getRef()));
        var response = replyToActorTestProbe.receiveMessage();

        Assertions.assertTrue(response.contains("Response to: First message"),
                "Log message should contain 'Received command: First message'");

    }

    @RepeatedTest(3)
    void askTest() {
        ActorRef<Command> simpleActor = testKit.spawn(RequestSimpleActor.create());

        CompletionStage<String> responseFuture =
                AskPattern.ask(
                        simpleActor,
                        replyTo -> new RequestCommand("Hello Actor!", replyTo),
                        Duration.ofSeconds(3),
                        testKit.scheduler()
                );

        String response = responseFuture.toCompletableFuture().join();

        Assertions.assertTrue(response.contains("Response to: Hello Actor!"),
                "Response should contain the message we sent");
    }

    @RepeatedTest(3)
    void askWithBehaviorTestKitTest() {
        var behaviorTestKit = BehaviorTestKit.create(RequestSimpleActor.create());
        TestInbox<String> inbox = TestInbox.create();

        behaviorTestKit.run(new RequestCommand("Hello Actor!", inbox.getRef()));

        Assertions.assertTrue(inbox.hasMessages());
        String response = inbox.receiveMessage();

        Assertions.assertTrue(response.contains("Response to: Hello Actor!"),
                "Response should contain the message we sent");
    }



}