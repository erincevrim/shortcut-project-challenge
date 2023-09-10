# The Shortcutter's Coding Challenge
## URL Shortener

<!-- TOC -->
* [The Shortcutter's Coding Challenge](#the-shortcutters-coding-challenge)
  * [URL Shortener](#url-shortener)
    * [Solution](#solution)
    * [How to Run](#how-to-run)
      * [Build an executable JAR -maven](#build-an-executable-jar--maven)
      * [Run application with jar](#run-application-with-jar)
      * [Api Test](#api-test)
    * [Technical choices and architecture](#technical-choices-and-architecture)
      * [Language](#language)
      * [Database](#database)
      * [Other](#other)
      * [Basic Architecture Diagram](#basic-architecture-diagram)
    * [Trade-Offs](#trade-offs)
    * [Skipped Features](#skipped-features-)
<!-- TOC -->


### Solution
* REST Api:
  * Spring Boot framework is used for creating REST Api.
* Database:
  * H2 Database
* Authentication:
  * JWT Token is used for authentication.
* Url Hashing
  * Google Murmur3 32 bit hashing is used for shortening urls.
    <br/>[Murmur Hash Wiki](https://en.wikipedia.org/wiki/MurmurHash)
* Testing
  * Junit

<br/>

### How to Run
#### Build an executable JAR -maven
```maven
mvn clean package spring-boot:repackage
java -jar target/UrlShortener-1.0.jar
```

#### Run application with jar
```
java -jar ./UrlShortener-1.0.jar
```

#### Api Test
* [Postman Collection](./docs/Url%20Shortener.postman_collection.json)
* Sign Up
  * [http://localhost:8787/rest/auth/sign-up](http://localhost:8787/rest/auth/sign-up) [POST]
```json
{
  "username": "erincevrim@test.com",
  "password": "testtest"
}
```
<br/>

* Sign In
  * [http://localhost:8787/rest/auth/sign-in](http://localhost:8787/rest/auth/sign-in) [POST]
```json
{
  "username": "erincevrim@test.com",
  "password": "testtest"
}
```
<br/>

* Url Shortener
  * [http://localhost:8787/r](http://localhost:8787/r) [POST]
```json
{
  "url": "https://www.google.com"
}
```
<br/>

* Url Shortened Url
  * [http://localhost:8787/r/{KEY}](http://localhost:8787/r/<KEY>) [GET]


* Health Check
  * [http://localhost:8787/rest/health-check](http://localhost:8787/rest/health-check) [GET]


<br/>

### Technical choices and architecture

#### Language
 * Java / Spring Boot Framework 
   * **Reason**: My main experience is on Java and Spring Boot so I have chosen these to handle this assignment.
 

#### Database
 * H2 Database
   * **Reason**: H2 Database is embedded and in-memory database, I have chosen this DB, to run the application without any DB setup and not spend time on creating & hosting DB. 
   

#### Other
 * Murmur Hash
   * **Reason**: Murmur is simple, has good performance and has good collision resistance. 
 * Maven
   * **Reason**: Easy to maintain libraries and run the application.

#### Potential Architecture Diagram
![](.\diagram.png)
 <br/>*Dashed ones have not been implemented. 

<br/>

### Trade-Offs
 * No stand alone DB:
   * Each time you run the application, DB will be created from scratch so you need to create user and urls again.
 
 * Exception Handling & Error Messages
   * General exception handling has been implemented to comply with the specified time period

<br/>

### Features Improvement
 * Host Application in the cloud.
 * CI/CD integration
 * Exception Handling & Error Messages
   * Error codes will be seperated for custom exceptions that front-end will handle it.
   * Language localization can be added for api error messages.
 * User specific Shortened Urls
   * In current implementation if same urls are requested by different users, output will be same but it can be user specific.
 * Email validation for username.
 * Custom url hashing algorithm can be implemented.
 * Serverless Infrastructure 
   