# The Project
This project emulates a basic CRM customer data holder.

The goal of the project its explained on [this document](documents/API Backend.pdf).

### Configurations
There's some needed configuration on the application.properties files that you have to check before running the project.
server.port

- spring.datasource.url: URL to the database connection
- spring.datasource.driverClassName: drive of the database, needed for the JDBC API
- spring.datasource.username: Username of database
- spring.datasource.password: Password for database
- spring.jpa.database-platform: Dialect for the database
- spring.jpa.hibernate.ddl-auto: This will tell to the database if there's some needed actions about creating the schema, updating, etc

- spring.security.oauth2.client.registration.github.clientId: Client id of the oauth2 application provider
- spring.security.oauth2.client.registration.github.clientSecret: Client id of the oauth2 application provider
- crm.customers.document.validation=Map object of key, value where the KEY is the country code and the value is the regexp for the document. Example: {\
'ES': '^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$',\
'DE': '[A-Za-z][A-Za-z0-9]{9}D[A-Za-z0-9]'\
}
### Run the project
We are assuming that you have installed docker and docker compose on your server machine.
There are 3 instructions to run the project.

Situate on the folder that is holding the project and use these commands.

This command stops the current task if one is running.
> docker-compose down

This command starts the application
>docker-compose run -d -p "8080:8080" java-api gradle clean build bootRun -x test

This command execute the tests
>docker-compose run --rm --no-deps -p "8080:8080" java-api gradle test

We are not going in deep on docker functionalities. Check the official documentation if you have any doubt.

After running the containers, connect to the database and insert your first user. This is needed to interact with the API. There you have an [insert example](documents/data.sql)

### Documentation

The API of the project its explained on [this document](documents/api-doc.yaml).
This can be read with [swagger editor](https://editor.swagger.io/). Copy the yaml content and paste on the editor. You will see a human-readable documentation.
//TODO ver el tema login para testing desde POSTMAN

### Dependencies

- We use different Spring dependencies for this project.
- Lombok for a better clean code.
- hibernate-validator for custom field validations

## Technologies

* [Java 18](https://openjdk.java.net/projects/jdk/18/)
* [Gradle 7](https://docs.gradle.org/7.0/release-notes.html)
* [Spring boot](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org/)
* [Junit](https://junit.org/junit5/)
* [JaCoCo](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
* [Docker](https://www.docker.com/)

### Overview

This project is based on
a [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) approach, so you
could find the first basic layers:

#### Infrastructure

Here you will find the different files to interact with the outside. In this folder you there are two different folders:

* `controllers`: Here you will have the classes that handle the REST endpoints and the Request/Response
* `persistence`: Here it is the persistence layer, which interact with the PostgreSQL database, decoupling the rest of
  the application

You can use this as a starting point to continue with this architecture, or adapt it to your preferences.

#### Domain

Any of your domain Entities, or Services, that models your business logic. These classes should be completely isolated
of any external dependency or framework, but interact with them. This layer should follow the Dependency Inversion
principle.