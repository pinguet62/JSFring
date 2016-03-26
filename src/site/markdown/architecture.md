# Architecture

## Global

![ERROR](images/global.svg)

## DAO

### Global

Data Access Object layer is link between *services* and *database*.

![ERROR](images/dao.svg)

There are 2 DAO type: SQL and NoSQL.

#### SQL

![ERROR](images/dao-sql.svg)

SQL-DAO use **JPA** API.

* **Cache :** JPA provides a cache to optimize database access.
* **Driver :** JDBC permits connect to all SGBD types.

#### NoSQL

![ERROR](images/dao-nosql.svg)

## Service

Service layer **provides functionalities** to final projects: *webservices*, *user interface*, ...

![ERROR](images/service.svg)

* **Transaction :** methods are transactional, using `@Transactional` annotation.
* **Cache :** methods may have cache, using `@Cacheable` and other related annotation.

## Webservice

Webservice expose *services* to external applications : webapp client, external applications, ...

![ERROR](images/webservice.svg)

## Presentation layers

### Server-side

This application can directly call *service layer*.

![ERROR](images/webapp-serverside.svg)

### Client-side

This application exchage data by *webservice* calls.

![ERROR](images/webapp-clientside.svg)
