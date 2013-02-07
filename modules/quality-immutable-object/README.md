Quality-Immutable-Object
========================

Example of how to use Quality-Immutable-Object: https://github.com/before/quality-check

How to build and run this example:

    % git clone https://github.com/before/quality-check.git
    % cd quality-check
    % mvn package
    % java -jar target/quality-immutable-object-0.1-SNAPSHOT.jar target/test-classes/Car.java

You can also do a clean build and run directly from maven:

    % mvn clean package exec:java
