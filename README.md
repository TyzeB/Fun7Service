Fun7Service Code Challenge
============================

This is an App Engine Flexible Java application from the appengine-flexible-archetype archetype.

This application provides information on whether services are enabled or not. There are three services available. Multiplayer, Ads and Customer Support.

It should return a JSON like this:
Values can either be `enabled` or `disabled`.

```json
{
	"multiplayer": "enabled",
    "user-support": "enabled",
    "ads": "disabled"
}
```

## How to use
### Visit the link:

If running on a local development server:

* Use Insomnia REST Client or Postman to see the JSON

```http
http://localhost:8080?userid=200&cc=USA&timezone=LJ
```

If you'd like to try it out:

```http
https://fun7service.appspot.com?userid=200&cc=USA&timezone=LJ
```

Note the query parameters:

 - userid
- cc
- timezone

These parameters must be provided, or the service will return:

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
