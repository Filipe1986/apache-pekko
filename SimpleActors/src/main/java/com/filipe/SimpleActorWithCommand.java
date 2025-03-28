package com.filipe;

import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.Behaviors;

/**
 * Simple actor that logs when receives a command.
 */
@Slf4j
public class SimpleActorWithCommand {
    public static Behavior<SimpleCommand> create() {
        return Behaviors.receive(SimpleCommand.class)
                .onAnyMessage(command -> {
                    log.info("Received command: " + command);
                    return Behaviors.same();
                })
                .build();
    }

}