package com.filipe;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.filipe.utils.testUtils;
import org.apache.pekko.actor.testkit.typed.javadsl.ActorTestKit;
import org.apache.pekko.actor.typed.ActorRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.LoggerFactory;

class SimpleActorWithCommandTest {

    private ActorTestKit testKit;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setup() {
        testKit = ActorTestKit.create();
        listAppender = new ListAppender<>();
        listAppender.start();
        Logger logger = (Logger) LoggerFactory.getLogger(SimpleActorWithCommand.class);
        logger.addAppender(listAppender);
    }

    @RepeatedTest(2)
    void test() {
        ActorRef<SimpleCommand> simpleActor = testKit.spawn(SimpleActorWithCommand.create());

        simpleActor.tell(new SimpleCommand());
        testUtils.wait(100);
        ILoggingEvent first = listAppender.list.getFirst();

        Assertions.assertTrue(first.getFormattedMessage().contains("Received command"),
                "Log message should contain 'Received command'");

    }



}