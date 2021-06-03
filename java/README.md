# Youtube Challenge - Java
The Java Youtube Challenge uses Java 11, Junit 5.4 and Maven.

If you need to install Maven, follow the instructions in [this](https://www.baeldung.com/install-maven-on-windows-linux-mac) link.

## Setting up
You can write code in any editor you'd like. However, different editors have 
different ways of dealing with Java code, so in case of doubt we recommend 
you run the code and tests from the command line as shown  below.

The below commands assume you are located in the java/ folder.

## Running and Testing from the Commandline
To build:
```shell script
mvn compile
```

To build & run:
```shell script
mvn exec:java
```

To run all the tests:
```shell script
mvn test
```

To run tests for a single Part:
```shell script
mvn test -Dtest=Part1Test
mvn test -Dtest=Part2Test
mvn test -Dtest=Part3Test
mvn test -Dtest=Part4Test
```

## Running and Testing from IntelliJ
You should be able to import the project with java/ being the root of the project.

To run the Application, click on the little green play symbol next to `Run`.
To run the tests, click on the little green double arrow next to the tests class.
