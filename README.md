# Modular Java Monolith Architecture POC

When starting a new application there are often doubts about what kind of architecture should be used.
Currently, the literature pushes toward Micro-Services architectures while most of the companies are experienced with the more common monolithic approach.

This POC shows a balanced approach that brings the following advantages:
- the **modularity** and **isolation** of the microservices
- the **simplicity** of the monolith.

This POC is a "Currency exchange rate publishing server" which aims to be easy to develop, cheap to maintain and straightforward to extend.


# Architecture Design

## Introduction

This section describes the architecture and technologies used to implement the currency rate exchange system. It provides a high-level view of the architecture for the whole solution, clarifying the approach used to satisfy all mentioned functional and nonfunctional requirements.

## Design of the system

The solution has been developed in a way that permits to deploy it as a classic monolithic three tier application or with a microservices architecture style.

The backend is written in Java; REST end points are provided to expose the internal services.

The frontend is an Angular Single Page Web Application written in TypeScript and transpiled to JavaScript through nodejs. Nodejs is not needed at runtime.

## Requirements

The only requirement needed to deploy and start the solution is a properly configured JVM 8. In addition, the port 8080 of the target machine must be available (a different port number can be configured).

To build from the source code, the requirements are a JDK8 and maven 3. Please note that the very first build can take some additional minutes because the entire nodejs ecosystem has to be downloaded.

The maven build process executes the unit and integration tests for both the backend and the frontend; a recent Chrome browser is needed to execute the frontend tests in headless mode. 

On the backend, the unit and integration tests are executed on two different build phases. First, the Unit tests are executed during the *test* phase, then the Integration tests are executed in the *verify* phase.


## Technology stack

The following technologies have been used to implement the solution:

-   **Java 8:** the JDK version 1.8.0\_151 was used for the backend code;
-   **Spring Boot:** Spring Boot was selected as core framework for the backend. It provides a coherent and huge ecosystem to support the development of Java Enterprise applications. It promotes low coupling and great modularity thanks to its extensive support for Inversion Of Control and Dependency Injection;
-   **Maven**: maven was selected as build and code management tool for the project. Even if it is mainly a Java build tool, Maven has a plugin based structure that permits to extend it to handle whatever type of task. In this project, among the others, the following two plugins are used:
    -   *frontend-maven-plugin*: as already stated, the frontend logic has been completely written in TypeScript. Nodejs is used to transpile, concatenate, minimize, test, collect and build the frontend to a production ready version. The frontend-maven-plugin permits the execution of nodejs tasks within the maven build process. In addition, it does not require nodejs to be installed on the build machine; it is therefore very useful in environment where only java is available (e.g. the development machine of a java developer that do not want to configure a full nodejs installation);
    -   *maven-docker-plugin*: this plugin is used to build and eventually push a docker image that contains the full currency exchange rate application. This is useful for local testing or to deploy the solution on docker based environment or cloud platforms (e.g. Amazon EC2 Container Service);
-   **H2**: the solution requires read/write access to a database; H2, a Java embeddable database that permits fast prototyping and maximizes portability, has been used for this purpose. This decision permits to deploy the solution as a self-contained Java application that has no external dependencies. Nevertheless, H2 is used only for simplifying and speed up the deployment of the POC; in production it should be replaced by a more canonical external database. In fact, the structure of the application permits to switch to whatever database with a single change in a configuration file.
-   **Spring Data JPA – Hibernate:** Spring Data JPA has been selected for its ability to generate at runtime most of the needed code to handle a repository. In addition, used with Hibernate, it permits to write performant code not bound to a specific database instance.
-   **Liquibase**: Liquibase is a database change management tool for tracking, managing and applying database schema changes. Rather than writing SQL scripts for a specific database, it permits to write the changes with a higher level of abstraction and to generate at runtime specific SQL statements for the target database. In addition, it can be used to create or to update a schema at runtime avoiding the need to manually cope with SQL files between different application releases.
-   **Nodejs**: nodejs is used to manage, build and test the frontend code;
-   **Angular**: Angular is the facto standard for development of Single Page web Application; it has been selected for its ability to greatly simplify the creation of modern web clients thanks to a modern dependency injection system and an extremely modular architecture;
-   **Bootstrap:** Bootstrap is a widely used open source CSS framework built to be responsive and easy to use. It has excellent integration with Angular thanks to projects like [ng-bootstrap](https://ng-bootstrap.github.io/). A key feature of bootstrap is its ability to change theme without modifying the HTML code; so the CSS and the HTML/JavaScript developments can proceed in parallel in nearly perfect isolation.
-   **REST**: the backend exposes services as JSON based REST endpoints. The REST API represents the contract between backend and frontend; once there is an agreement on the REST API, the development of the two can progress independently.
-   **Docker**: the solution can be packaged and built as a docker image permitting the deployment of it in any local or remote environment that provides docker support as well as in many popular cloud platforms.


## Database scripts

The database is managed automatically at runtime by the application itself. Therefore, no SQL scripts are provided.


## Architecture and structure of the code

This chapter describes the structure of the code explaining how to package and deploy the application as a single deployable unit or as a set of micro services.

The source code is organized as a multi module maven project with five children:

1. **common**: it contains common logic shared by all modules;
1. **currency**: this module implements the server side logic related to the currency domain. It exposes two REST endpoints:
    - To get the list of all know currencies;
    - To obtain the list of currencies whose name matches a specific filter.
1. **exchangeRate**: the exchange rate module has been designed to be independent from the currency one; it has its own database schema and exposes two REST services:
    - To get the exchange rates for a list of currencies;
    - To convert an amount between two currencies.
1. **frontend**: it contains the code of the frontend. It is built with nodejs. This module has no external java dependencies and it does not depend on other modules. The idea is that it can be developed by a dedicated team with specific frontend expertise. The needed data is obtained through the REST endpoints exposed by the other two modules.
1. **build**: this module imports currency, exchangeRate and frontend and packages them in a single deployable artifact.

In conclusion, the “common” module contains shared logic; the “currency” and “exchangeRate” modules implement isolated business domains; the “frontend” module contains the web client code and, finally, the “build” module collects all the needed dependencies (i.e. common, currency, exchangeRate and frontend) and package all of them in a single deployable artifact. This artifact is a runnable jar file with an embedded Tomcat servlet container that can be started with a simple “java -jar com.modular.build.jar” command.

Even if the frontend is not written in Java, to simplify the packaging and the deployment process, the web resources (e.g. JS, html, css, etc.) are packaged as a jar library and managed as a typical maven dependency so they can be versioned and imported easily.

Through the build module, the solution is packaged as a monolithic application as shown by this picture:

!!! TODO: Insert picture !!!!

However, due to the fact that “currency”, “exchangeRate” and “frontend” are completely independent self-contained applications, they can be easily repackaged and deployed independently to match a micro service architecture.

In this case, the deployment process becomes more powerful and more complex at the same time. For example a proxy will be needed to route the frontend calls to the correct micro service. At this point it is required to introduce a new layer to handle the requests routing; for example, Spring Cloud could be used to provide the needed components (e.g. service discovery, load balancing, configuration server).


## Deployment strategy

### Using the executable jar

Once packaged with:
> mvn clean verify

Enter the "build" folder and start the application with:

> java -jar ./target/com.modular.build.jar

This starts a web server on port 8080. To start using the application, open a browser (the application has been tested with Firefox and Chrome) and access the URL:

http://localhost:8080

The executable jar can be copied and executed from whatever position on the file system.

### Using docker

Alternatively, the application can be deployed as a docker image.

Once the application is built, a docker image can be created from the source code executing the following command:

> mvn clean package docker:build

This create a docker image named “modular/build”. A new instance of it can be started locally with:

> docker run -p 8080:8080 modular/build

### Using spring-boot

At development time, the application can also be started using the spring-boot maven plugin from the "build" folder:

> mvn spring-boot:run

In addition, the frontend part can be launched from the "frontend/webapp" folder with the command:

> npm start

This requires nodejs 8.9.x to be installed on the local machine.


# To be implemented and future improvements

## Backend

The solution is not tuned to be used in a production environment; for example, the cache manager is a NoOperationCache. To make it ready for production, specific Spring profiles should be created (e.g. one for development, one for local deploy with an external database, one for AWS that uses Redis for the cache and Amazon RDS as database).

In addition, different storage systems should be taken into account instead of a database. In fact, the main service of the currency module performs a sort of full text search on the database using the “like” SQL operator; this operator is particularly slow because it executes a full table scan on each execution. In case of big datasets or high load, the system would greatly benefit from a search engine like ElasticSearch.

Finally, the exchange rates could be gathered from a third party trusted source (e.g.
[*http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote*](http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote)
)

## Frontend

The frontend code and architecture are not 
On the frontend side there are multiple missing things:

-   The frontend should be localized through a proper translation system (e.g. using angular-translate);
-   To improve the global reactivity and to simplify the code management, the AngularJS services should return immutable objects wrapped into RxJs observables. This is, for example, required to in case, for example, of Flux architectures;
-   Unit testing for frontend has been omitted for lack of time, this is definitely a bad practice as the frontend should have a proper test coverage like the backend.
