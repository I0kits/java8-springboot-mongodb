#!/bin/sh
CLASSPATH="*"

# Spring Boot uses LOADER_PATH to specify external classpath. It must be used with PropertiesLauncher
MAIN_CLASS="org.springframework.boot.loader.PropertiesLauncher"
JAVA_CMD=java
echo $CLASSPATH

$JAVA_CMD -classpath "$CLASSPATH" $MAIN_CLASS
