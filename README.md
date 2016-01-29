# JSFring

Integration of Spring into JSF project.

## Frameworks & APIs & Utils

### Used

Layers :
* Data access
	* ![LOGO](https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png =90x25) [**Spring Data**](http://projects.spring.io/spring-data)
	* Databases :
		* SQL
			* **JPA**
				* ![LOGO](http://static.jboss.org/hibernate/images/hibernate_logo_whitebkg_200px.png =100x25) [Hibernate](http://hibernate.org)
			* ![LOGO](https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/langfr-540px-Postgresql_elephant.svg.png =24x25) [PostgreSQL](http://www.postgresql.org)
		* NoSQL
			* ![LOGO](https://www.mongodb.org/assets/global/mongodb-logo-web-tagline-99280fe76cc002a93d023901c1a05df8b621f1c893084a580dee83de9be96630.png =69x25) [MongoDB](https://www.mongodb.org)
	* ![LOGO](http://devrates.com/project/logo/337129 =80x25) [Querydsl](http://www.querydsl.com)
* Webservice:
	* ![LOGO](http://www.mkyong.com/wp-content/uploads/2011/07/jaxrs-tutorials.gif =62x25)
		[**JAX-RS**](https://jax-rs-spec.java.net)
		* ![LOGO](https://jersey.java.net/images/jersey_logo.png =29x25)
			[Jersey](https://jersey.java.net)
* Webapp:
	* Java
		* ![LOGO](https://upload.wikimedia.org/wikipedia/en/thumb/7/75/20110510-jsf-logo.tiff/lossless-page1-320px-20110510-jsf-logo.tiff.png =50x25) **JSF**
			* ![LOGO](http://www.primefaces.org/images/logo.png =111x25) [**PrimeFaces**](http://primefaces.org)
		* ![LOGO](https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png =90x25) [Spring security](http://projects.spring.io/spring-security)
			* Taglib (JSF integration)
	* ![LOGO](https://angularjs.org/img/AngularJS-large.png =89x25) [**AngularJS**](https://angularjs.org)
		* ![LOGO](https://angular-translate.github.io/img/logo/angular-translate-alternative/angular-translate_alternative_medium2.png =186x25) [Angular Translate](https://angular-translate.github.io)
		* ![LOGO](https://upload.wikimedia.org/wikipedia/fr/1/13/TwitterBootstrap.png =25x25) [**Bootstrap**](http://getbootstrap.com)
			* [UI Bootstrap](https://angular-ui.github.io/bootstrap)
		* [ngDialog](http://ngmodules.org/modules/ngDialog)
		* [UI Grid](http://ui-grid.info)

Transverse :
* *Transverse*
	* **Java EE**
		* [**CDI**](https://docs.oracle.com/javaee/7/tutorial/cdi-basic.htm) (Contexts and Dependency Injection)
	* ![LOGO](https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png =90x25) [**Spring framework**](http://projects.spring.io/spring-framework)
		* [Caching Data](https://spring.io/guides/gs/caching)
		* [Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks)
* Tests:
	* ![LOGO](http://junit.org/images/junit-logo.png =63x25) [**JUnit**](http://junit.org)
	* ![LOGO](http://hamcrest.org/images/logo.jpg =25x25) [Hamcrest](http://hamcrest.org/JavaHamcrest)
	* ![LOGO](http://dbunit.sourceforge.net/images/dbunit-logo.jpg =50x25) [**DBUnit**](http://dbunit.sourceforge.net)
	* [Spring Test DBUnit](http://springtestdbunit.github.io/spring-test-dbunit)
* Logging:
	* ![LOGO](http://www.slf4j.org/images/logos/slf4j-logo.jpg =59x25) [SLF4J](http://www.slf4j.org)
	* ![LOGO](http://logging.apache.org/log4j/2.x/images/logo.jpg =66x25) [**Log4j2**](http://logging.apache.org/log4j/2.x)
* Mail:
	* ![LOGO](https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png =90x25) [JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html)
* Reporting:
	* ![LOGO](http://community-static.jaspersoft.com/sites/default/files/styles/project_icon/public/projects/icons/js_reports-library_1_rg2.png?itok=YfQKPh7R =28x25) [**Jasper Report**](https://community.jaspersoft.com/project/jasperreports-server)

Utils :
* ![LOGO](https://maven.apache.org/images/maven-logo-black-on-white.png =99x25) [**Maven**](https://maven.apache.org)

### TODO

* [Multitenancy](https://en.wikipedia.org/wiki/Multitenancy) architecture
* Datastore
	* [Elasticsearch](https://www.elastic.co/fr)
* Test
	* [**JMeter**](http://jmeter.apache.org)
* Logging
	* [Logstash](https://www.elastic.co/products/logstash)
* ![LOGO](http://oauth.net/images/oauth-2-sm.png =25x25) ![LOGO](https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png =90x25) [OAuth2](http://oauth.net/2)

### Version

All dependencies use the latest version.

[![Dependency Status](https://www.versioneye.com/user/projects/56729e638fea07000e093826/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56729e638fea07000e093826)
[![Dependency Status](https://www.versioneye.com/user/projects/56729e698fea07000a09375a/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56729e698fea07000a09375a)
[![Dependency Status](https://www.versioneye.com/user/projects/56729e6f8fea07000e093878/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56729e6f8fea07000e093878)
[![Dependency Status](https://www.versioneye.com/user/projects/56729e738fea07000e0938b0/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56729e738fea07000e0938b0)
[![Dependency Status](https://www.versioneye.com/user/projects/56729e798fea07000e0938eb/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56729e798fea07000e0938eb)
[![Dependency Status](https://www.versioneye.com/user/projects/56729e808fea07000a0937cb/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56729e808fea07000a0937cb)

## Code style/quality, Continue Integration, Deployment

### Code quality

Check that *source code* respect *best practices*:
* project metrics
* code patterns
* code coverage
* code complexity
* code duplication
* ...

[![Codacy Badge](https://api.codacy.com/project/badge/9ea574afb85b496294af3b6057e278aa)](https://www.codacy.com/app/pinguet62/JSFring)

### Testing

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

[![codecov.io](https://codecov.io/github/pinguet62/JSFring/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/JSFring?branch=master)

## SAS & Continue Integration

Deploy web-application on the cloud, in continue!

[![Deploy](https://www.herokucdn.com/deploy/button.png)](http://pinguet62-jsfring.herokuapp.com)

## Licence

[![Creative Commons BY-NC-SA](https://img.shields.io/badge/Licence-Creative%20Commons-brightgreen.svg)](http://creativecommons.org/licenses/by-nc-sa/4.0)
