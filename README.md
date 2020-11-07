# Birras Application - Santander Tecnología Challenge

## Technologies & Frameworks used:

Java 8
- Springboot 
- Retry
- Circuit Breaker
- Swagger
- Lombok
- Junit
- Mockito
- AssertJ
- H2 Database
- Java Email
- Maven
- JPA

## DB Credentials
user: admin 
password: admin
jdbcURL:jdbc:h2:mem:santander
console: http://localhost:8080/h2/

## Puerto y Host
http://localhost:8080/

## Swagger
http://localhost:8080/swagger-ui.html

## Email Notifications
- An email will be sent to a user when it is registered, when it registers/checksIn/attends to a meetup
- An email will be sent to the administrator when it creates a new meetup santander.tecnologia.meetups@gmail.com

## Retry
- The Application will retry 2 times to connect to the Weather Service.

## Circuit Breaker
- If the Application cannot retrieve the weather forecast from the Weather Service(it is down or internet connection failed), it will be looked up from the database, if there is no record it will answer that the weather couldn't be retrieved.
- The weather register for a given date is saved in the DB only once the first time it is retrieved from Weather Service.

## Spring Security
Please refer to my spring security project https://github.com/agusaliaga/springsecurityapp for a full spring security implementation

## Unit Tests
Code coverage is aprox. 93%.

## Suggested Pipeline for CI/CD
- The developer pushes a commit to a git branch in remote repository. This will trigger a Jenkins Build. Sonar will analyze the code for smells and bugs. It will also show test coverage and make sure it is enough to assure the good quality of the code.
- Jenkins will run unit and integration tests, and if they are successful the branch is ready to be merged.
- Once the branch is merged (after code review), this version will be deployed by Jenkins in a testing environment where automation testing will be performed.
- After successful testing the new version can be deployed in UAT environments and finally in production.

## READMEs
- I use the max. temperature for the day, and retrieve the weather forecast for 18:00hs on the base that most meetups are after 18:00hs and before sunset a maximum temperature will be reached.
- Admin and User can retrieve temperature for meetup within the next 5 days (we cannot predict further with certainty)

