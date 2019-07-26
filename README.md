##Perfect Number API

This is a small Java program to check if a number is perfect or not. It will also returns all perfect numbers in a given range of numbers.

This contains two REST APIs to serve the following operations:

1. Check if a given number is perfect
2. Find all perfect numbers between a range (start-end)

This uses a small algorithm to compute the perfect numbers using some Java 8 features.

Technologies to used:

* Embedded Jetty server
* Apache CXF
* Gradle and
* Java 8

Also includes few unit tests:
* To test the perfect number generation algorithm
* Few Integration tests which access the Jetty container and access the rest APIs

##Prerequisites

* Java 8
* Gradle

##Execution

The application consists of some unit test and integration tests to verify the functionality and they can be executed by running the command from project home folder
* ./gradlew test

The test cases can be found at ./src/test/java/lk/inli/rest/test where
* PerfectNumberProcessorTest - contains unit tests
* PerfectNumberServiceTest - contains integration tests

##Build PerfectNumberAPI

Execute the following command to bundle the application with all the dependencies into a JAR file. It will also execute all unit tests as a precondition.

* ./gradlew packageJar

This will download all dependencies and copy them to build/libs/dependencies and bundle them to PerfectNumberAPI-1.0-SNAPSHOT.jar

##Run Perfect Number API

Execute the following command which will deploy the application in embedded Jetty container and will allow the user to access the APIs

* java -jar PerfectNumberAPI-1.0-SNAPSHOT.jar

##API Definition

There are three end-points available as follows:
* /perfectnumber/{input} - Check if a given number in "input" is a perfect number
* /perfectnumber?start={start}&end={end} - Gets all perfect numbers with in the range
* /perfectnumber/ping - This is to check the heart-beat of the API

##API Security
These APIs were secured with Basic Auth header and the following username and password has to be provided.
* Username: REST_USER
* Password: REST_PASSWORD

##API Usage
The APIs can be accessed through CURL or Postman. Following commands uses CURL to access the APIs

* Check the number is perfect

```
curl -XGET --user REST_USER:REST_PASSWORD http://localhost:8080/perfectnumber/28
```

The response will be a JSON like below

```js
{
   "statusCode": 401,
   "message": "Invalid credentials"
}
```
* Get all the perfect numbers with in range
```
curl -XGET --user REST_USER:REST_PASSWORD http://0.0.0.0:8080/perfectnumbers?start=1&end=1000
```
Response:
```js
{
    "rangeStart": 1,
    "rangeEnd": 1000,
    "perfectNumbers": [
        6,
        28,
        496
    ]
}
```
* Check heart beat of the API which will return 200 if API is available
```
curl -XGET --user REST_USER:REST_PASSWORD http://0.0.0.0:8080/perfectnumber/ping
```

## The Design
* The services were designed having the testability in mind so that they can be unit tested easily. This makes it more maintainable and robust.
* The heart beat end-point will allow to check the health of the application
* The APIs were secured with basic Authentication and performance was considered to great extend
* Parameter validation was decoupled from the main business logic so that developers can concern various parts separably

## Improvements and Known Issues
* Long data type was used in the implementation which limits the number range to 2^64 and that can be increased by use of other data types like BigInteger etc
* API versioning can be used to maintain the old contracts and new contracts
* Logging can also be improved
* Currently it takes little long time to generate perfect numbers of a very large number range and to check if very large number is perfect