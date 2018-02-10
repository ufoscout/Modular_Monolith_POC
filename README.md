# Modular Java Monolith Architecture POC

When starting a new application there are often doubts about what kind of architecture to use.
Currently, the litterature pushes toward Micro-Services architectures while most of the companies are experienced with the more common Monolithic approach.

This POC shows a balanced approach that brings the following advantages:
- the **modularity** and **isolation** of the micro services
- the **simplicity** of the monolith.

At the end we'll get a "Currency exchange rate publishing server" which is easy to develop, cheap to maintain and straightforward to extend.


# Architecture Design

## Introduction

This document describes the architecture and technologies used to implement a currency rate exchange system. It provides a high level view of the architecture for the whole solution, clarifying the approach used to satisfy all mentioned functional and nonfunctional requirements.

## Design of the system

The solution has been developed in a way that permits to deploy it as a classic monolithic three tier application or with a micro services architecture style.

The server side part is completely written in Java; REST end points are provided to expose the internal services.

The client is a Single Page web Application written in HTML 5 and JavaScript.

## Requirements

The only requirement needed to deploy and start the solution is a properly configured JDK 8. In addition, the port 8080 of the target machine must be available.

To build from the source code, maven 3 is additionally required (please note that on the first build the entire nodejs ecosystem is downloaded, this takes some minutes)

## Technology stack

The following technologies have been used to implement the solution:

-   **Java 8:** the JDK version 1.8.0\_60 has been used for the backend code;
-   **Spring Boot:** Spring Boot has been selected as core framework for the backend. It provides a coherent and huge ecosystem to support the development of Java Enterprise applications. It promotes low coupling and great modularity thanks to its extensive support for Inversion Of Control and Dependency Injection;
-   **Maven**: maven has been selected as build and code management tool for the project. Even if it is mainly a Java build tool, Maven has plugin based structure that permits to extend it to handle whatever type of task. In this project, among the others, the following two plugins have been used:
    -   *frontend-maven-plugin*: as already stated, the frontend logic has been completely written in javascript. Nodejs is used to concatenate, minimize, test, collect and build the frontend to a production ready version. The frontend-maven-plugin permits the execution of nodejs tasks within the maven build process. In addition, it does not require nodejs to be installed on the build machine; it is therefore very useful in environment where only java is available (e.g. the development machine of a java developer that do not want to configure a full nodejs installation);
    -   *maven-docker-plugin*: this plugin is used to build and
        eventually push a docker image that contains the full currency exchange rate application. This is useful for local testing or to deploy the solution on docker based environment or cloud platforms (e.g. Amazon EC2 Container Service);
-   **H2**: the solution requires read/write access to a database; H2, a Java embeddable database that permits fast prototyping and maximizes portability, has been used for this purpose. This decision permits to deploy the solution as a self-contained java application that has no external dependencies. Nevertheless, an external more canonical database can be used with a simple change in the configuration file;
-   **Spring Data JPA – Hibernate: **Spring Data JPA has been selected for its ability to generate at runtime most of the needed code to handle a repository. In addition, used with Hibernate, it permits to write performant code not bound to a specific database instance. By default the solution uses an H2 database.
-   **Liquibase**: Liquibase is a database change management tool for tracking, managing and applying database schema changes. Rather than writing SQL scripts for a specific database, it permits to write the changes with a higher level of abstraction and then to generate database specific scripts for the supported databases. In addition, it can be used to create or to update a schema at runtime avoiding the need to manually cope with SQL files.
-   **Nodejs**: nodejs is used to manage, build and test the frontend code;
-   **bower**: it is a web dependencies management tool for nodejs. It has been used to avoid manual collecting of JavaScript dependencies;
-   **gulp**: gulp is a nodejs based task runner used to automatize the build and test process of the frontend;
-   **AngularJS**: AngularJS is the facto standard for development of Single Page web Application; it has been selected for its ability to greatly simplify the creation of modern web clients thanks to a modern dependency injection system and an extremely modular architecture;
-   **Bootstrap:** Bootstrap is a widely used open source CSS framework built to be responsive and easy to use. It has excellent integration with AngularJS thanks to projects like ui-bootstrap. A key feature of bootstrap is its ability to change theme without modifying the HTML code; so the CSS and the HTML/JavaScript developments can proceed in parallel in nearly perfect isolation.
-   **REST**: the backend exposes services as JSON based REST endpoints. The REST API represents the contract between backend and frontend; once there is an agreement on the REST API, the development of the two can progress independently.
-   **Docker**: the solution can be packaged and built as a docker image permitting the deployment of it in any local or remote environment that provides docker support as well as in many popular cloud platforms.

## Database scripts

The database is managed automatically at runtime by the application itself through liquibase. Therefore no SQL scripts are provided.

## Architecture and structure of the code

This chapter describes the structure of the code explaining that the wayt he code has been written permits to package and deploy the application as a single deployable unit or as a set of micro services.

The source code is organized as a multi module maven project with five children:

1. **common**: it contains common logic shared by other modules;
1. **currency**: this module implements the server side logic related to the currency domain. It exposes two REST endpoints:
    - To get the list of all know currencies;
    - To obtain the list of currencies whose name matches a specific filter.
1.  **exchangeRate**: the exchange rate module has been designed to be independent from the currency one; it has its own database schema and exposes two REST services:
    -   To get the exchange rates for a list of currencies;
    -   To convert an amount between two currencies.
1.  **frontend**: it contains the code of the frontend. It is built with nodejs. This module has no external java dependencies. It does not depend on other modules. The idea is that this module can be developed by a dedicated team with specific JavaScript expertise. The needed data is obtained through their REST endpoints exposed by the other two modules.
1.  **build**: this module imports currency, exchangeRate and frontend and packages them in a single deployable artifact.

In conclusion, the three modules “common”, “currency” and “exchangeRate” contain the business logic of the server part; the “frontend” module contains the web client code and, finally, the “build” module collects all the needed dependencies (i.e. common, currency, exchangeRate and frontend) and package all of them in a single deployable artifact. This artifact is a runnable jar file with an embedded Tomcat servlet container that can be started with a simple “java -jar com.currency.build.jar” command.

Even if the frontend is not written in Java, to simplify the packaging and the deployment process, the web resources (e.g. JS, html) are packaged as a jar library and managed as a typical maven dependency.

With the use of the build module, the solution is packaged as a monolithic application as shown by this picture:

However, due to the fact that “currency”, “exchangeRate” and “frontend” are completely independent self-contained applications, it can be easily repackaged to match a micro service architecture:

In this last case, the deployment process becomes more powerful and more complex at the same time. For example a proxy will be needed to route the frontend calls to the correct micro service:
Spring Cloud can be introduced used to take advantage of the ready to use components needed to build such a distributed system (e.g. service discovery, load balancing, configuration server).

## Deployment strategy

Extract the “Deployment/com.currency.build.jar” file from the provided zip archive to a local folder. Then from that folder execute the command:

java -jar com.currency.build.jar

This will start a web server on port 8080. To start using the application, open a browser (the application has been tested with Firefox and Chrome) and access the URL:

http://localhost:8080

Alternatively, the application can be deployed as a docker image.

A docker image can be created from the source code executing the following command from the “Code” folder:

mvn clean package docker:build

This create a docker image named “currency/build”, it can be locally started with:

docker run -p 8080:8080 currency/build


# To be implemented and future improvements

## Backend

The solution is not tuned to be used in a production environment; for example, the cache manager is a NoOperationCache. To make it ready for production, specific Spring profiles should be created (e.g. one for development, one for local deploy with an external databse, one for AWS that uses Redis for the cache and Amazon RDS as database).

In addition, different storage systems should be taken into account instead of a database. In fact, the main service of the currency module performs a sort of full text search on the database using the “like” SQL operator; this operator is particularly slow because it executes a full table scan on each execution. In case of big datasets or high load, the system would greatly benefit from a search engine like ElasticSearch..

Finally, the exchange rates could be gathered from a third party trusted source (e.g.
[*http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote*](http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote)
)

## Frontend

On the frontend side there are multiple missing things:

-   The frontend should be localized through a proper translation system (e.g. using angular-translate);
-   To improve the global reactivity and to simplify the code management, the AngularJS services should return immutable objects wrapped into RxJs observables. This is, for example, required to in case, for example, of Flux architectures;
-   Unit testing for frontend has been omitted for lack of time, this is definitely a bad practice as the frontend should have a proper test coverage like the backend.
