# SIARD Suite Release Testing Guide

## Motivation

The development of SIARD Suite is financed by the Swiss Federal Archives (SFA), which are one of the main stakeholders of the project. The SFA are also one of the main users of the application. There are many restrictions on SFA staff workstations:

- Pre-installed Java 8 (+JavaFX) with no possibility to switch to another JRE.
- Inability to download and run any form of executable binary or script (e.g. `SIARD-suite.bat`).

The only way SFA staff can run the application is with the following cli command:

```bash
java -jar lib/siard-suite.jar
```

Due to these constraints, the application will often run fine when using native installers or start scripts (with bundled JRE), but not with the above command, without anyone noticing until an SFA staff member tries to run the application without success.

That's why it is recommended to perform the following steps, to make sure that the application really works for SFA staff and for users of different distributions.


## Test script

First, switch to use Java 17 with JavaFX via sdkman (or any other way of switching between Java versions): 

```bash
sdk use java 17.0.10.fx-zulu
```

Run a build and create native installers and packaged distributions: 

```bash
./gradlew clean jpackage
```

Start some databases:

```bash
cd docker
docker compose up -d --build
```

## Smoke tests for SFA environment

Switch to the unpacked distribution of your application:

```bash
cd build/install/SIARD-Suite/
```

Use Java 8 with JavaFX via sdkman:

```bash
sdk use java 8.0.402.fx-zulu
```

Run the application:

```bash
java -jar lib/siard-suite-2.2.*.jar 
```

In the application, please carry out the following steps to archive data sources:

* Configure the data source for archiving: Select "Archive" and "Use new connection".
* Fill out metadata.
* Save the archive and select "View archive" at the end of the wizard.

### Microsoft SQL Server
  * Connection URL: jdbc:sqlserver://localhost:1433;databaseName=siard
  * Username: sa
  * Password: Yukon900

### Oracle
   * Connection URL: jdbc:oracle:thin:@localhost:1521/siard
   * Username: siard
   * Password: siard

### MS Access
  * MS Access files: 
    * `/home/<username>/<your-directory>/siard-suite/docker/msaccess/Northwind.accdb`
    * `/home/<username>/<your-directory>/siard-suite/docker/msaccess/nations.accdb`


## Smoke tests for packaged distributions

Change to the directory containing the unpacked application distribution (including a JRE)

```bash
cd build/jpackage/SIARD-Suite/
```

Run the application:

```bash
./bin/SIARD-Suite(.bat)
```

Run the tests described above for both data sources. If everything works, push changes to the main branch and run the release task according to the readme.


## Considerations

Testing the release manually is tedious and time-consuming. Be aware that you should only test two things manually:
1. Does the application start?
1. Is it possible to archive a simple database and open the archive?

The above tests are only valuable if they are actually run at least once. We should find ways to automate this step and add it to the build pipeline.

Testing special cases and complex databases should be done in the SiardCmd repository using the integration test framework!

