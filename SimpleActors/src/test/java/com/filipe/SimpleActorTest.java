package com.filipe;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.actor.testkit.typed.javadsl.ActorTestKit;
import org.apache.pekko.actor.typed.ActorRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.LoggerFactory;

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

    @RepeatedTest(5)
    void test() {
        ActorRef<String> simpleActor = testKit.spawn(SimpleActor.create());

        simpleActor.tell("First message");
        wait(100);
        ILoggingEvent first = listAppender.list.getFirst();

        Assertions.assertTrue(first.getFormattedMessage().contains("Received command: First message"),
                    "Log message should contain 'Received command: First message'");

    }

    public void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}