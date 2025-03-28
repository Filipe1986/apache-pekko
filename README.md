# SimpleActors

A demonstration project for Apache Pekko actors with various testing approaches in Java.

## Overview

This project showcases different patterns for implementing and testing actors using Apache Pekko's actor system (a fork of the Akka project). It provides examples of:

- Basic actors
- Actors with command-based messaging
- Request-response pattern actors
- Various testing strategies for actors

## Prerequisites

- Java 21 or higher
- Maven 3.6+

## Project Structure

- **SimpleActor**: Basic actor implementation
- **SimpleActorWithCommand**: Actor that handles command objects
- **RequestSimpleActor**: Actor demonstrating request-response pattern
- **Test Utils**: Utility methods for testing

## Testing Approaches

The project demonstrates several testing approaches:

1. **Basic Actor Testing**: Using `ActorTestKit` for simple actor tests
2. **Log Verification**: Capturing and verifying log output from actors
3. **Request-Response Testing**:
    - Using Tell pattern with TestProbe
    - Using Ask pattern with CompletionStage
    - Using BehaviorTestKit for synchronous testing

## Running Tests

```bash
mvn clean test
```

To build the entire project:

```bash
mvn clean install
```

> **Note**: All test classes should be declared as `public` to ensure Maven's Surefire plugin can discover and run them properly.

## Example Usage

Check the test classes for examples of how to:
- Spawn actors
- Send messages to actors
- Verify actor behavior through logs
- Test request-response interactions

## Dependencies

- Apache Pekko Actor
- JUnit 5
- Logback for logging