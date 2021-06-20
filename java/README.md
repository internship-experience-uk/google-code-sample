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
If you haven't changed anything in the code, it's expected to see error messages like this:
```
[ERROR] Tests run: 67, Failures: 63, Errors: 0, Skipped: 0
```

To run tests for a single Part:
```shell script
mvn test -Dtest=Part1Test
mvn test -Dtest=Part2Test
mvn test -Dtest=Part3Test
mvn test -Dtest=Part4Test
```

## Running and Testing from IntelliJ
To import the project as a Maven project, follow [the official IntelliJ instructions](https://www.jetbrains.com/help/idea/maven-support.html#maven_import_project_start). Alternatively, you should be able to import the project at the java/ folder and IntelliJ will automatically recognize the project as a Maven project.
Make sure that the project SDK is set to Java 11, [the official IntelliJ instructions on how to set it](https://www.jetbrains.com/help/idea/sdk.html#change-project-sdk).

To run the Application, click on the little green play symbol next to `Run`.
To run the tests, click on the little green double arrow next to the tests class.
