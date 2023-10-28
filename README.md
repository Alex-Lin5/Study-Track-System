# Study-Track-System

## Application
`mvn spring-boot:run`, to start application

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