# JSFring

Integration of Spring into JSF project.

## Frameworks & APIs & Utils

### Used

Layers :
* Data access
	* <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-data-project.png" height="25" /> [**Spring Data**](http://projects.spring.io/spring-data) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
	* Databases :
		* SQL
			* <img src="http://blog.soat.fr/wp-content/uploads/2015/10/lb-square.jpg" height="25" /> [Liquibase](http://www.liquibase.org)
			* <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/langfr-540px-Postgresql_elephant.svg.png" height="25" /> [PostgreSQL](http://www.postgresql.org)
			* **JPA**
				* <img src="http://static.jboss.org/hibernate/images/hibernate_logo_whitebkg_200px.png" height="25" /> [Hibernate](http://hibernate.org)
		* NoSQL
			* <img src="https://upload.wikimedia.org/wikipedia/en/4/45/MongoDB-Logo.svg" height="25" /> [MongoDB](https://www.mongodb.org)
	* <img src="https://avatars2.githubusercontent.com/u/7044479?v=3&s=200" height="25" /> [Querydsl](http://www.querydsl.com)
* Webservice:
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> [**Spring MVC**](https://spring.io/guides/gs/serving-web-content) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
	* <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" height="25" /> [Spring security](http://projects.spring.io/spring-security) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
	* <img src="http://oauth.net/images/oauth-2-sm.png" height="25" /> [OAuth2](http://oauth.net/2) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
* Webapp:
	* Java
		* <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/20110510-jsf-logo.tiff/lossless-page1-320px-20110510-jsf-logo.tiff.png" height="25" /> **JSF**
			* <img src="http://www.primefaces.org/images/logo.png" height="25" /> [**PrimeFaces**](http://primefaces.org)
		* <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" height="25" /> [Spring security](http://projects.spring.io/spring-security) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
			* Taglib (JSF integration)
	* <img src="https://angularjs.org/img/AngularJS-large.png" height="25" /> [**AngularJS**](https://angularjs.org)
		* <img src="https://upload.wikimedia.org/wikipedia/fr/1/13/TwitterBootstrap.png" height="25" /> [**Bootstrap**](http://getbootstrap.com)
		* AngularJS modules:
			* <img src="http://angular-ui.github.io/bootstrap/assets/favicon.ico" height="25" /> [UI Bootstrap](https://angular-ui.github.io/bootstrap)
			* <img src="https://angular-translate.github.io/img/logo/angular-translate-alternative/angular-translate_alternative_medium2.png" height="25" /> [Angular Translate](https://angular-translate.github.io)
			* [ngDialog](http://ngmodules.org/modules/ngDialog)
			* [UI Grid](http://ui-grid.info)
	* <img src="https://angular.io/assets/images/logos/angular/logo-nav@2x.png" height="25" /> [**Angular**](https://angular.io)
		* <img src="https://www.primefaces.org/primeng/assets/showcase/images/logo.png" height="25" /> [Primeng](http://www.primefaces.org/primeng)
		* [Material 2](https://material.angular.io)
* Batch:
	* <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-batch-project.png" height="25" /> [Spring batch](http://projects.spring.io/spring-batch) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
	* [Spring batch admin](http://docs.spring.io/spring-batch-admin)

Transverse :
* *Transverse*
	* <img src="https://upload.wikimedia.org/wikipedia/fr/2/2e/Java_Logo.svg" height="25" /> [**Java**](http://www.oracle.com/technetwork/java/index.html)
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> [**Spring framework**](http://projects.spring.io/spring-framework)
		* <img src="http://g00glen00b.be/wp-content/uploads/2012/08/spring-boot-logo.png" height="25" /> [Spring Boot](http://projects.spring.io/spring-boot) by <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" />
		* [Caching Data](https://spring.io/guides/gs/caching)
		* [Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks)
* Tests:
	* <img src="http://junit.org/junit4/images/junit-logo.png" height="25" /> [**JUnit**](http://junit.org)
	* <img src="http://hamcrest.org/images/logo.jpg" height="25" /> [Hamcrest](http://hamcrest.org/JavaHamcrest)
	* <img src="http://dbunit.sourceforge.net/images/dbunit-logo.jpg" height="25" /> [**DBUnit**](http://dbunit.sourceforge.net) + [Spring Test DBUnit](http://springtestdbunit.github.io/spring-test-dbunit)
	* <img src="http://htmlunit.sourceforge.net/images/GargoyleSoftwareLogo.jpg" height="25" /> [**HtmlUnit**](http://htmlunit.sourceforge.net)
* Logging:
	* <img src="http://www.slf4j.org/images/logos/slf4j-logo.jpg" height="25" /> [SLF4J](http://www.slf4j.org)
	* <img src="https://logback.qos.ch/images/logos/lblogo.jpg" height="25" /> [**Logback**](https://logback.qos.ch)
* Mail:
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> [JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html)
* Reporting:
	* <img src="http://community-static.jaspersoft.com/sites/default/files/styles/project_icon/public/projects/icons/js_reports-library_1_rg2.png?itok=YfQKPh7R" height="25" /> [**Jasper Report**](https://community.jaspersoft.com/project/jasperreports-server)

Utils :
* <img src="https://maven.apache.org/images/maven-logo-black-on-white.png" height="25" /> [**Maven**](https://maven.apache.org)

Production
* <img src="https://www.docker.com/sites/all/themes/docker/assets/images/brand-full.svg" height="25" /> [**Docker**](https://www.docker.com)
* <img src="https://www-assets3.herokucdn.com/assets/logo-purple-08fb38cebb99e3aac5202df018eb337c5be74d5214768c90a8198c97420e4201.svg" height="25" /> [Heroku](https://www.heroku.com)

### TODO

* [Multitenancy](https://en.wikipedia.org/wiki/Multitenancy) architecture
* Datastore
	* <img src="http://www.steamulo.com/blobs/com.cardiweb.cardiboxv6.cm.business.Article/3131807079036093132/img/1/fr/logo-elastic.png" height="25" /> [Elasticsearch](https://www.elastic.co/fr)
* Test
	* [**JMeter**](http://jmeter.apache.org)
* Logging
	* <img src="http://logz.io/wp-content/uploads/2015/12/logstash-logo.png" height="25" /> [Logstash](https://www.elastic.co/products/logstash)

## Continuous Updating

All dependencies use the latest version.

<img src="https://libraries.io/assets/logo-ff2b4e24d68406c7bddf1bea8d15a9f3f06623bc47933cda0c7dc22828dd4a6a.svg" height="25" /> 
[Libraries.io](https://libraries.io) 
[![Libraries.io for GitHub](https://img.shields.io/librariesio/github/pinguet62/jsfring.svg)](https://libraries.io/github/pinguet62/jsfring)

## Security & vulnerabilities

<img src="https://res.cloudinary.com/snyk/image/upload/v1468845259/logo/snyk-dog.svg" height="25" /> 
[Snyk](https://snyk.io) 
[![Known Vulnerabilities](https://snyk.io/test/github/pinguet62/jsfring/badge.svg)](https://snyk.io/test/github/pinguet62/jsfring)

## Static analysis

### Code quality

Check that *source code* respect *best practices*:
* project metrics
* code patterns
* code coverage
* code complexity
* code duplication
* ...

<img src="https://www.codacy.com/versioned/images/favicon.png" height="25" /> 
[Codacy](https://www.codacy.com) 
[![Codacy Badge](https://api.codacy.com/project/badge/9ea574afb85b496294af3b6057e278aa)](https://www.codacy.com/app/pinguet62/JSFring)

## Build & Test

### Continuous Integration

Build and run unit/integration tests, in continue!

<img src="http://img.photobucket.com/albums/v330/pretendercrazy/travis-an_zpsb8ef2f7e.gif" height="25" /> 
[Travis CI](https://travis-ci.org) 
[![Build Status](https://travis-ci.org/pinguet62/JSFring.svg?branch=master)](https://travis-ci.org/pinguet62/JSFring)

### Code coverage

Measure the degree to which the source code of a program is *tested*.

<img src="https://coveralls.io/favicon.png" height="25" /> 
[Coveralls](https://coveralls.io) 
[![Coverage Status](https://coveralls.io/repos/github/pinguet62/JSFring/badge.svg?branch=master)](https://coveralls.io/github/pinguet62/JSFring?branch=master)

<img src="https://avatars2.githubusercontent.com/u/8226205?v=3&s=200" height="25" /> 
[Codecov](https://codecov.io) 
[![codecov.io](https://codecov.io/github/pinguet62/JSFring/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/JSFring?branch=master)

## Install & Runtime

### SaaS (Software as a Service): Continuous Deployment

Multi web-application deployed on cloud... in continue!

* Webapp:
	* JSF: <img src="https://www-assets3.herokucdn.com/assets/logo-purple-08fb38cebb99e3aac5202df018eb337c5be74d5214768c90a8198c97420e4201.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Heroku](https://heroku-badge.herokuapp.com/?app=jsfring-webapp-jsf)](https://jsfring-webapp-jsf.herokuapp.com)
	* AngularJS: <img src="https://www-assets3.herokucdn.com/assets/logo-purple-08fb38cebb99e3aac5202df018eb337c5be74d5214768c90a8198c97420e4201.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Heroku](https://heroku-badge.herokuapp.com/?app=jsfring-webapp-angularjs)](https://jsfring-webapp-angularjs.herokuapp.com)
	* Angular 2: <img src="https://www-assets3.herokucdn.com/assets/logo-purple-08fb38cebb99e3aac5202df018eb337c5be74d5214768c90a8198c97420e4201.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Heroku](https://heroku-badge.herokuapp.com/?app=jsfring-webapp-angular)](https://jsfring-webapp-angular.herokuapp.com)
	* Batch-admin: <img src="https://www-assets3.herokucdn.com/assets/logo-purple-08fb38cebb99e3aac5202df018eb337c5be74d5214768c90a8198c97420e4201.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Heroku](https://heroku-badge.herokuapp.com/?app=jsfring-batch-admin)](https://jsfring-batch-admin.herokuapp.com)
* Webservice: <img src="https://www-assets3.herokucdn.com/assets/logo-purple-08fb38cebb99e3aac5202df018eb337c5be74d5214768c90a8198c97420e4201.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Heroku](https://heroku-badge.herokuapp.com/?app=jsfring-webservice)](https://jsfring-webservice.herokuapp.com)

## Documentation

See [**Maven site**](http://pinguet62.github.io/JSFring)

## Licence

<img src="http://mirrors.creativecommons.org/presskit/buttons/88x31/png/by-nc-sa.png" height="25" />
[Creative Commons BY-NC-SA](http://creativecommons.org/licenses/by-nc-sa/4.0)
[![Creative Commons BY-NC-SA](https://img.shields.io/badge/Licence-Creative%20Commons-brightgreen.svg)](http://creativecommons.org/licenses/by-nc-sa/4.0)
