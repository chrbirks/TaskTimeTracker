#!/bin/sh

export LOGGING_LIBS=/usr/share/java/slf4j/slf4j-api-1.7.12.jar:/usr/share/java/logback-1.1.3/logback-classic-1.1.3.jar:/usr/share/java/logback-1.1.3/logback-core-1.1.3.jar
export MAINAPP=target/TaskTimeTracker-0.1.jar

java -cp $LOGGING_LIBS:$MAINAPP main.java.ttt.TaskTimeTracker
