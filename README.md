# Noom Technical Challenge

This is my solution to the Sleep technical challenge from Noom. All the code is in this repository, and I'll explain some
decisions I made during the development in the sections below.

### Business Rules

I decided to adopt the following business rules that weren't specified in the challenge description:

Authentication/Authorization - it was instructed to ignore these aspects but be aware of the concept of a user. I did so through
the use of the `username` header. This header identifies which user is making the request, and the API will only return
the data related to this user.

Only one Sleep Log per day - I decided to allow only one Sleep Log per day, mainly to simplify the API and avoid
confusion about the concepts of "night sleep" and "day sleep". If a user tries to create a new Sleep Log for a day that
already has one, the API will return an error.

Last Night Log - The "last night" log is considered the log that has the date of the current day. Since the sleep
can start in the night of the last day or in the first hours of the current day, only the wake up time is considered to
determine the date of the log.

Create Sleep Logs for past nights - It's allowed to create Sleep Logs for dates in the past, but not for future dates.
This is most to facilitate the population of the database with logs for more days, but also can be a nice feature to
consider if a new user wants to import his past data.

Date Time Format - The date time format chosen for inputs is `yyyy-MM-ddTHH:mm:ss:SSSZ`, and the API will only accept this format.
For the time values on outputs, the format is `HH:mm`, I choose to not consider the seconds in the format because I think that seconds of sleep it's not relevant. 
This can also be seeing in the information about the total time of sleep and the average time of sleep, all of them are in minutes.

### Implementation

I created a REST API using Kotlin and Spring Boot, with a Postgres database. The API has three endpoints:
 - Create a new Sleep Log
 - Find last night Sleep Log
 - Find the averages of the last 30 days Sleep Logs

I provided a simple open-ai documentation for the APi, along with the swagger-ui that can be accessed at 
.html`.

Dto classes where created to handle the data and validations of the requests and responses. This was done to separate
the concerns of the Rest API and the entities, providing the information requested for the API without the need to 
change the entities, business logic and the database schema. It also facilitates other integrations, 
like message brokers, to use the same data structure.

The Repository followed the same logic, separating the concerns of the database and the business logic.
An adapter interface was created to handle the communication between the repository and the service, 
and a Jdbc implementation was created to handle the database operations. This allows it to be easily changed to a JPA,
NoSQL or any other implementation.

The RequestDto class fields allow null values because of a Spring Boot side effect: since the deserialization of the request body is done before the
validations, it triggers a parse error and not the validation error desired.

### Migrations

The database use the flyway library to handle the migrations. During the development, I changed my mind a couple of 
times about the naming and types of the columns, but decided to edit the V1.1 file instead of creating new versions,
mainly to keep it more organized and avoid having a lot of migration files to do the same change.

### Tests

I created unit tests for the services, controller and mapper to check the functionality of the code. 
I also created an integration test for the repository to check the connection and operations in a postgres database. 
I used the JUnit and MockK libraries to create the unit tests, and Testcontainers to create the integration test.

### Running the application

The provided docker-compose file was moved to inside the project to facilitate the execution of the application.
To run the application, you need to have Docker and Docker Compose installed. Then, you can run the following command:

```shell
docker compose up
```

This will provide a PostgreSQL database running on port 5432, and the API running on port 8080. 
You can access the [swagger-ui](http://localhost:8080/swagger-ui.html).

Also, a postman collection was provided in the file `sleep.postman_collection.json`. It can be imported to Postman and
contains an example of the requests to the API. 
When using the data examples, be careful about the `/sleep-log/last-night` endpoint, because it could return a
not found response if the date of the created log is not the current day.