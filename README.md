# RESTful Web Service with Spring Boot

Demo project using Spring Boot Web, Spring Security, Spring Boot Actuator, Spring REST Docs, 
and accessing relational data using: JDBC with Spring and Spring Data JPA.

## Dockerfile configuration
- Create simple Dockerfile and run application with user privileges.
```
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

- Create layers in container filesystem.
```
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","pe.carlosesp.demo.demorestservice.DemoRestServiceApplication"]
```

- Build Docker Image with Maven:
```
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=carespiritu/demo-rest-service
```

## Spring Security

- Customer endpoints configured with Spring Security: /customers
- Actuator endpoints Shutdown and Health with Spring Security.

## Sprint REST Docs

Helps you produce documentation for your RESTful services using Asciidoctor by default. 
The Asciidoctor file is then processed to produce an HTML file with the complete 
documentation based on the tests created for our services.

Uses snippets produced by test written with Spring MVC's [test framework][1], 
Spring WebFlux's [WebTestClient][2] or [REST Assured 3][3].

### Requirements

- Java 8
- Spring 5.0.2 or later

The `spring-restdocs-restassured` module requires REST Assured 3.0.

Provides first-class support for JUnit 4 and JUnit 5. Other frameworks, 
such as TestNG, are also supported, but require more setup.

[1]: https://docs.spring.io/spring-framework/docs/5.0.x/spring-framework-reference/testing.html#spring-mvc-test-framework
[2]: https://docs.spring.io/spring-framework/docs/5.0.x/spring-framework-reference/testing.html#webtestclient
[3]: https://rest-assured.io

### Snippets

You must create an `.adoc` source file first. You can then include 
the generated snippets in the Asciidoc file created.

The default location of the source files, and the resulting HTML files depends on the build tool used:

|Build Tool | Source files               | Generated files               |
|-----------|:--------------------------:|------------------------------:|
|Maven      | `src/main/asciidoc/*.adoc` | `target/generated-docs/*.html`|
|Gradle     | `src/docs/asciidoc/*.adoc` | `build/asciidoc/html5/*.html` |

