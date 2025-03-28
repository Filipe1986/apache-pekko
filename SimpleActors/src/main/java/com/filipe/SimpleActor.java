package com.filipe;

import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.Behaviors;

/**
 * Simple actor that logs received String commands.
 */
@Slf4j
public class SimpleActor {
    public static Behavior<String> create() {
        return Behaviors.receive(String.class)
                .onAnyMessage(command -> {
                    log.info("Received command: " + command);
                    return Behaviors.same();
                })
                .build();
    }
}