package com.filipe;

import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.Behaviors;

/**
 * Simple actor that logs received String commands.
 */
@Slf4j
public class RequestSimpleActor {
    public static Behavior<Command> create() {
        return Behaviors.receive(Command.class)
                .onMessage(RequestCommand.class, request -> {
                    log.info("Received message: " + request.message);
                    request.replyTo.tell(("Response to: " + request.message));
                    return Behaviors.same();
                })
                .build();
    }
}