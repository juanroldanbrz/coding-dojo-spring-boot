Spring Boot Coding Dojo
---

Welcome to the Spring Boot Coding Dojo!

### Introduction

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) and stores the result in a database. The current implementation has quite a few problems making it a non-production ready product.

### The task

As the new engineer leading this project, your first task is to make it production-grade, feel free to refactor any piece
necessary to achieve the goal.

### How to deliver the code

Please send an email containing your solution with a link to a public repository.

>**DO NOT create a Pull Request with your solution** 

### Footnote
It's possible to generate the API key going to the [OpenWeather Sign up](https://openweathermap.org/appid) page.


DB Scripts and migration strategy
---
I am not using liquibase or other migration tool due lack of time, but it would be nice to evaluate if
it is needed.

I did move the creation scripts to  the sq/create folder, so we can divide them from the update scripts
in the future.

Running the service
--- 
- Run the postgreSQL compose file `docker-compose.yml`
- Execute `mvn clean package && export API_WEATHER_KEY=your_api_key && mvn spring-boot:run`

Building the docker image
---
With JIB:

`mvn com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=coding/dojo`

Testing
---
I assume that the DB used is PostgreSQL 9.1 because the driver that was being used.
I wrote 2 type of tests, unit tests and integration tests. The integration tests uses a technology 
called `testcontainers` which run a database in docker and connects it to the application context, 
allowing to test it with a real database rather that with in-memory H2.
The cons of this approach is that you need to have docker in the runtime to run these tests.

For testing the external API calls I am mocking the `openweather` JSON response. Check `WeatherApiClientTest` test. 

- Run unit tests with `mvn clean test`.
- Run integration tests with `mvn failsafe:integration-test` (Docker required)

Configuration
---
This is the default configuration:
```yaml
spring:
  # Spring Datasource and JPA properties
  datasource:
    hikari:
      connectionTimeout: 20000
      maximumPoolSize: 5
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: none
    database-platform: org.hibernate.dialect.PostgreSQLDialect

# API Key for openweather.
api:
  weather:
    key: changeme
```

Error handling
---
Most of the errors are not recoverable because they are related with the external API call, so they
are managed in the `RestExceptionHandler`.


Changelog
---
- Replace old version of the JDBC driver to the last one, which also supports Postgres 9.1
- Restructure the whole code and applying Google code style.
- Separate the layers of the application, so the entity is not returned directly by the controller. Entity belongs to the domain layer and not to the web layer.
- Add a `created_at` timestamp in the entity. **NOTE:** I would love to know why the `id` is returned by the API response. If it is not needed we should remove from the WeatherDTO.
- Add a docker-compose file with Postgres 9.1 initialized with the creation SQL scripts.
- Add README to build the docker image.
- Update Spring boot parent version to fix an hibernate issue `Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented` exception.
- Fix SQL scripts (issues with the auto increment of the primary key)
- Use `testcontainers` to perform the integration test with a postgreSQL instance.
- Add exception handling.
- Add logging.
