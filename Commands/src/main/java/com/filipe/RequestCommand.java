package com.filipe;

import org.apache.pekko.actor.typed.ActorRef;

public class RequestCommand implements Command {
    public final String message;
    public final ActorRef<String> replyTo;
    
    public RequestCommand(String message, ActorRef<String> replyTo) {
        this.message = message;
        this.replyTo = replyTo;
    }
}