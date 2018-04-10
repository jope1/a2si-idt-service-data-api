# dos-wrapper
This application primarily acts as a proxy between a DoS client and the DoS system itself.
It only takes additional processing when handling a Check Capacity Summary Response, 
the SOAP response to a DoS SOAP request of CheckCapacitySummary.

When processing CheckCapacitySummaryResponse, the wrapper uses the service id of each 
ServiceCareSummaryDestination element in the response and calls the Capacity Service, requesting
Capacity Information for that specific Service Id.

If Capacity Information is returned then this information is formatted into plan text 
(albeit with carriage returns) and PREFIXED to the response's notes field.


This is a Spring Boot application, when running a Maven build, it also creates a zip file that includes
the Spring Boot application and a Docker file, which is a specific set of instructions describing the 
Docker image to be built.

## Getting started
First, download the code from GitHub. This can be done using the desktop git tool, an IDE which supports git or by downloading the code as a zip file which you can then extract.

Next, install the dev tools and dependencies....

##Installation of Development Tools and Dependencies
Install Git for Windows:
Install official git release: https://git-scm.com/download/win

Or install GitHub Desktop which also includes a GUI interface for git management: https://desktop.github.com/

###Install Java Development Kit 8:
http://www.oracle.com/technetwork/java/javase/downloads/

###Install Maven 3:
https://maven.apache.org/download.cgi

###Environment Variables
Ensure that the system environment variables for Java and Maven are set correctly, as described below...

M2_HOME should point to the install directory of your local Maven install folder, e.g.
M2_HOME C:\Maven\apache-maven-3.3.9

JAVA_HOME should point to the install directory of your local Java JDK install folder, e.g.

JAVA_HOME C:\Program Files\Java\jdk1.8.0_121

PATH should contain the bin directory of both M2_HOME and JAVA_HOME, e.g.

```
...;%JAVA_HOME%\bin;%M2_HOME%\bin;...
```


## Use maven to build the project:

cd {projectRoot}
mvn clean install

the Maven "install" goal stores the built artifact into the local Maven repository, 
making it accessible to other projects using this repository.

The application is going to be deployed in AWS using Elastic Beanstalk, using Docker as a container. Elastic Beanstalk
allows Spring Boot applications to be packaged along with a DockerFile in a single zip file. This zip file is all
that is required to deploy into AWS Elastic Beanstalk. Environment variables may be required to define the 
Spring Profiles Active variable.

## Running the Application
............................. 

## Maintaining the Application
............................
```  

## Application Configuration
Following a best practice approach that comes from Spring, configuration files hold data specific to 
environments whilst wiring of class dependencies is done via Java Configuration classes. These classes
use the configuration files to set simple properties and are created when the application starts.

