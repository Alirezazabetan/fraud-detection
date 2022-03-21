# Fraud Detection Service Application
Application to manage Fraud Transactions based on **Spring Boot Rest Api**.
Provides a processing Transaction Files with Spring Batch and expose an Api to trigger job.

## Technology choices
* Lightweight **Spring Boot** service with embedded tomcat and use Spring Batch to proceeding transaction list files.
* Rest Api is documented with **Swagger and openApi**, which provides nice UI with all documentation and ability to test services
* Persistence based on **Spring Data JPA** and **in-memory h2 database** to simplify the deployment and avoid external dependencies. (not suitable for production, but easily replaceable by other relational database)
* Unit and integration testing with **JUnit and Mockito**. Rest Api integration testing with spring-test.

## Prerequisites
To be able to run application you need to have installed:

- Spring webmvc/jpa data/hibernate
- **[Maven](https://maven.apache.org/)**
- Java 17
- h2
- Docker
- Swagger/OpenApi/Springfox
- Mockito

## Running modes
By default application will run in **development mode** (see application.properties):
- Logs are printed only to console and root level for application packages - DEBUG
- Application uses in-memory database

To run in **production mode**, application should be started with spring profile "server" activated (see application-server.properties):
- Logs will be printed to file in the logs folder of the root directory
- Logging root level for all packages - INFO

## How to run application

**Run the docker compose file**

````bash
      docker-compose -f "docker-compose.yml" up --build -d
````

**With maven:**    
Use spring boot plugin to start(it will build backend and frontend):   
_mvn spring-boot:run_

**For deployment on test/production without docker server:**   
Build jar package with maven:   
_mvn clean package_  
Run application with:  
_java -jar fraud-detection-0.0.1-SNAPSHOT.jar --spring.profiles.active=server_

## Testing of application functionality  
   
Example of request to trigger the job for processing CSV file and return fraud list:

**curl --location --request GET 'http://localhost:9595/job/CSV'**

Example of request to trigger the job for processing JSON file and return fraud list:

**curl --location --request GET 'http://localhost:9595/job/JSON'**

Or you can use Swagger UI, which contains Rest Api documentation:     
**_http://localhost:9595/swagger-ui/index.html_**

## Limitations
- Application uses in-memory relational database h2, so after restart all the data is reloaded.
- On startup application creates tables in the database based on JPA-entities, which is not suitable for production.

## TODO (for the next iterations)
- Move to proper persistence storage: relational or NoSql database.
- Use encrypted transport protocol(TLS) for Rest Api.
- Improve authentication/authorization with proper user/roles management.
- Add support for encrypted properties to avoid sensitive data provided in plain text.
- Provide some service to upload and manage the Fraud file with different formats
- Add a AMQP like Kafka to manage and detect fraud detection in real time. 
- Configure CI/CD server for build and deployment
- Improve input validation for Rest Api. Define better error messages in property file.
- Add pagination for GET all frauds api to be able to limit amount of returned items.
- Add separate maven profile to run integration tests.


