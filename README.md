# Study-Track-System

## Application
- `mvn spring-boot:run`, to start application
- `mvn dependency:tree`, to find out the detailed dependencies info in repository

## Feature
This is a Java backend project developed under Spring Framework and Maven management tool
- Hibernate ORM applied for mapping model to relational database *h2database*
- lombok library injected to generate boilerplate code in entity definition
- Junit5 test suite implemented for multi-branch CRUD operations testing
- Apache logging tool *Log4j2* applied to help detect and fix issues

### Environment
- Spring Framework 6.0.13
- Spring Boot 3.1.5
- Maven 3.9.5
- JDK 17.0.2
### Dependencies
- h2database 2.1.214
- lombok 1.18.30

## Entity
Material is the object to study, and commit is when to do it. Study track is a conclusion of progressing of the material.
||||
|:---:|:---:|:---|
|Material|->|name|
||->|description|
||->|url|
||->|note|
|||
|Commit|->|date|
||->|*"hours"*|
||->|start|
||->|end|
|||
|Track|->|[*Commit]
||->|*Material|
||->|progress|