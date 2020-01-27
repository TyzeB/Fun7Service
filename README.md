Fun7Service Code Challenge
============================

This is a App Engine Flexible Java application from the appengine-flexible-archetype archetype.

## How to use
Visit the link:

    https://fun7service.appspot.com/?userid=200&cc=USA&timezone=LJ

Note the:
	- userid
	- cc
	- timezone
These parameters must be provided in order to use the service. If any of them is missing, the service will return:
    
    400 Bad Request

## Requirements

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi) (at least 3.3.9)
* [Gradle](https://gradle.org/gradle-download/) (optional)
* [Google Cloud SDK](https://cloud.google.com/sdk/) (aka gcloud)

Initialize the Google Cloud SDK using:

    gcloud init

This skeleton is ready to run.

## Maven

### Run Locally

    mvn appengine:run
#### If you get the following error while running locally:

    Failed to execute goal com.google.cloud.tools:appengine-maven-plugin:1.3.1:run (default-cli) on project fun7service: Execution default-cli of goal com.google.cloud.tools:appengine-maven-plugin:1.3.1:run failed: Non zero exit: 1 -> [Help 1]

Try running:

    netstat -ano | grep 8080
Then kill the task with the PID that uses the port 8080

    taskkill -pid  PID -f

Try running locally again.

### Deploy

    mvn appengine:deploy

### Test Only

    mvn verify
