package com.filipe;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.filipe.utils.testUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.actor.testkit.typed.javadsl.ActorTestKit;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.javadsl.AskPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@Slf4j
public class SimpleActorTest {

    private ActorTestKit testKit;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setup() {
        testKit = ActorTestKit.create();

        listAppender = new ListAppender<>();
        listAppender.start();
        Logger logger = (Logger) LoggerFactory.getLogger(SimpleActor.class);
        logger.addAppender(listAppender);

    }

    @RepeatedTest(2)
    void test() {
        ActorRef<String> simpleActor = testKit.spawn(SimpleActor.create());

        simpleActor.tell("First message");
        testUtils.wait(100);
        ILoggingEvent first = listAppender.list.getFirst();

        Assertions.assertTrue(first.getFormattedMessage().contains("Received command: First message"),
                    "Log message should contain 'Received command: First message'");

    }
    @RepeatedTest(2)
    void testAsk() {
        ActorRef<String> simpleActor = testKit.spawn(SimpleActor.create());

        CompletionStage<String> responseFuture =
                AskPattern.ask(
                        simpleActor,
                        replyTo -> "First message", // Assuming SimpleActor supports ask pattern
                        Duration.ofSeconds(2),
                        testKit.scheduler()
                );

        Assertions.assertThrows(java.util.concurrent.CompletionException.class,
                () -> responseFuture.toCompletableFuture().join(),
                "Expected timeout since SimpleActor doesn't respond to messages");

    }




}