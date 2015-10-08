# JSFring

Integration of Spring into JSF project.

## Frameworks & APIs

### Used

** [**Maven**](https://maven.apache.org/)
* Webapp:
	* **JSF**
	* [**PrimeFaces**](http://primefaces.org/)
* **JPA**:
	* [**Hibernate**](http://hibernate.org/)
	* [**Querydsl**](http://www.querydsl.com/)
* [**CDI**](https://docs.oracle.com/javaee/6/tutorial/doc/gjbnr.html)
* [**Spring**](http://projects.spring.io/spring-framework/)
	* Context & Context support
		* Cache
		* Scheduling
		* Mail ([JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html))
	* *Database*
		* JDBC
		* ORM : Hibernate 4
		* JPA
	* Test : [DBUnit](http://springtestdbunit.github.io/spring-test-dbunit/)
* [**Spring security**](http://projects.spring.io/spring-security/)
	* Taglib (JSF integration)
* Tests:
	* [**Junit**](http://junit.org/)
	* [**DBUnit**](http://dbunit.sourceforge.net/)
* Loggin
	* [SLF4J](http://www.slf4j.org/)
	* [**Log4j**](http://logging.apache.org/log4j/2.x/)
* Reporting:
	* [**Jasper Report**](https://community.jaspersoft.com/project/jasperreports-server)

### TODO

* [Multitenancy](https://en.wikipedia.org/wiki/Multitenancy) architecture
* Datastore
	* NoSQL: [**MongoDB**](https://www.mongodb.org/)
	* [Elasticsearch](https://www.elastic.co/fr/)
* Utils:
	* [JIRA](https://fr.atlassian.com/software/jira)
	* [**Jenkins**](https://jenkins-ci.org/)
* Test
	* [**JMeter**](http://jmeter.apache.org/)

## Code quality

### Code quality

Check that **code quality** respect *best practices*.

Util: [SonarQube](http://www.sonarsource.com/)

Result: 

### Tests

#### Unit test

**JUnit** is used to execute code.

#### Integration test

**HtmlUnit** is used to navigate into website.

#### Continue Integration

Build and run unit/integration tests, in continue!

[![Build Status](https://travis-ci.org/pinguet62/JSFring.svg?branch=master)](https://travis-ci.org/pinguet62/JSFring)

#### Code coverage

Measure the degree to which the source code of a program is *tested*.

[![Coverage Status](https://coveralls.io/repos/pinguet62/JSFring/badge.svg?branch=master&service=github)](https://coveralls.io/github/pinguet62/JSFring?branch=master)

## SAS & Continue Integration

Deploy web-application on the cloud, in continue!

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)
