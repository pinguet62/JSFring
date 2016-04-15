# JSFring

Integration of Spring into JSF project.

## Frameworks & APIs & Utils

### Used

Layers :
* Data access
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-data-project.png" height="25" /> [**Spring Data**](http://projects.spring.io/spring-data)
	* Databases :
		* SQL
			* **JPA**
				* <img src="http://static.jboss.org/hibernate/images/hibernate_logo_whitebkg_200px.png" height="25" /> [Hibernate](http://hibernate.org)
			* <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/langfr-540px-Postgresql_elephant.svg.png" height="25" /> [PostgreSQL](http://www.postgresql.org)
		* NoSQL
			* <img src="https://www.mongodb.org/assets/global/mongodb-logo-web-tagline-99280fe76cc002a93d023901c1a05df8b621f1c893084a580dee83de9be96630.png" height="25" /> [MongoDB](https://www.mongodb.org)
	* <img src="http://devrates.com/project/logo/337129" height="25" /> [Querydsl](http://www.querydsl.com)
* Webservice:
	* <img src="http://www.mkyong.com/wp-content/uploads/2011/07/jaxrs-tutorials.gif" height="25" />
		[**JAX-RS**](https://jax-rs-spec.java.net)
		* <img src="https://jersey.java.net/images/jersey_logo.png" height="25" />
			[Jersey](https://jersey.java.net)
	* <img src="http://oauth.net/images/oauth-2-sm.png" height="25" /> <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> [OAuth2](http://oauth.net/2)
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" height="25" /> [Spring security](http://projects.spring.io/spring-security)
* Webapp:
	* Java
		* <img src="https://upload.wikimedia.org/wikipedia/en/thumb/7/75/20110510-jsf-logo.tiff/lossless-page1-320px-20110510-jsf-logo.tiff.png" height="25" /> **JSF**
			* <img src="http://www.primefaces.org/images/logo.png" height="25" /> [**PrimeFaces**](http://primefaces.org)
		* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" height="25" /> [Spring security](http://projects.spring.io/spring-security)
			* Taglib (JSF integration)
	* <img src="https://angularjs.org/img/AngularJS-large.png" height="25" /> [**AngularJS**](https://angularjs.org)
		* <img src="http://www.webjars.org/assets/logo.png" height="25" /> [WebJars](http://www.webjars.org)
		* <img src="https://upload.wikimedia.org/wikipedia/fr/1/13/TwitterBootstrap.png" height="25" /> [**Bootstrap**](http://getbootstrap.com)
		* AngularJS modules:
			* <img src="http://angular-ui.github.io/bootstrap/assets/favicon.ico" height="25" /> [UI Bootstrap](https://angular-ui.github.io/bootstrap)
			* <img src="https://angular-translate.github.io/img/logo/angular-translate-alternative/angular-translate_alternative_medium2.png" height="25" /> [Angular Translate](https://angular-translate.github.io)
			* [ngDialog](http://ngmodules.org/modules/ngDialog)
			* [UI Grid](http://ui-grid.info)
* Batch:
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> <img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-batch-project.png" height="25" /> [Spring batch](http://projects.spring.io/spring-batch)
	* [Spring batch admin](http://docs.spring.io/spring-batch-admin)

Transverse :
* *Transverse*
	* <img src="https://upload.wikimedia.org/wikipedia/en/8/88/Java_logo.png" height="25" /> [**Java**](http://www.oracle.com/technetwork/java/index.html)
	* **Java EE**
		* [CDI](https://docs.oracle.com/javaee/7/tutorial/cdi-basic.htm) (Contexts and Dependency Injection)
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> [**Spring framework**](http://projects.spring.io/spring-framework)
		* <img src="http://g00glen00b.be/wp-content/uploads/2012/08/spring-boot-logo.png" height="25" /> [Spring Boot](http://projects.spring.io/spring-boot)
		* [Caching Data](https://spring.io/guides/gs/caching)
		* [Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks)
* Tests:
	* <img src="http://junit.org/images/junit-logo.png" height="25" /> [**JUnit**](http://junit.org)
	* <img src="http://hamcrest.org/images/logo.jpg" height="25" /> [Hamcrest](http://hamcrest.org/JavaHamcrest)
	* <img src="http://dbunit.sourceforge.net/images/dbunit-logo.jpg" height="25" /> [**DBUnit**](http://dbunit.sourceforge.net)
	* <img src="http://htmlunit.sourceforge.net/images/GargoyleSoftwareLogo.jpg" height="25" /> [**HtmlUnit**](http://htmlunit.sourceforge.net/)
	* [Spring Test DBUnit](http://springtestdbunit.github.io/spring-test-dbunit)
* Logging:
	* <img src="http://www.slf4j.org/images/logos/slf4j-logo.jpg" height="25" /> [SLF4J](http://www.slf4j.org)
	* <img src="http://logging.apache.org/log4j/2.x/images/logo.jpg" height="25" /> [**Log4j2**](http://logging.apache.org/log4j/2.x)
* Mail:
	* <img src="https://upload.wikimedia.org/wikipedia/fr/d/d0/Spring_framework.png" height="25" /> [JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html)
* Reporting:
	* <img src="http://community-static.jaspersoft.com/sites/default/files/styles/project_icon/public/projects/icons/js_reports-library_1_rg2.png?itok=YfQKPh7R" height="25" /> [**Jasper Report**](https://community.jaspersoft.com/project/jasperreports-server)

Utils :
* <img src="https://maven.apache.org/images/maven-logo-black-on-white.png" height="25" /> [**Maven**](https://maven.apache.org)

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

<img src="https://www.versioneye.com/assets/verisoneye-logo-small-09d4dc67649e8bb51adc2c37216a341f01306950d556f4024c82c302f259ebda.png" height="25" />
[VersionEye](https://www.versioneye.com)
[![Dependency Status](https://www.versioneye.com/user/projects/5706c883baf4130009b19b50/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5706c883baf4130009b19b50)

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


<img src="https://d234q63orb21db.cloudfront.net/9fe342f72c9a347589de9022ee335ed24d278355/media/images/pink.png" height="25" />
[Codecov](https://codecov.io)
[![codecov.io](https://codecov.io/github/pinguet62/JSFring/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/JSFring?branch=master)

## Install & Runtime

### SaaS (Software as a Service): Continuous Deployment

Multi web-application deployed on cloud... in continue!

* Webapp:
	* JSF: <img src="https://d3k90kvix375hb.cloudfront.net/assets/logo-purple.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Deploy](https://www.herokucdn.com/deploy/button.png)](http://jsfring-webapp-jsf.herokuapp.com)
	* AngularJS: <img src="https://d3k90kvix375hb.cloudfront.net/assets/logo-purple.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Deploy](https://www.herokucdn.com/deploy/button.png)](http://jsfring-webapp-angularjs.herokuapp.com)
	* Batch-admin: <img src="https://d3k90kvix375hb.cloudfront.net/assets/logo-purple.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Deploy](https://www.herokucdn.com/deploy/button.png)](http://jsfring-batch-admin.herokuapp.com)
* Webservice: <img src="https://d3k90kvix375hb.cloudfront.net/assets/logo-purple.svg" height="25" /> [Heroku](https://www.heroku.com/home) [![Deploy](https://www.herokucdn.com/deploy/button.png)](http://jsfring-webservice.herokuapp.com)

## Documentation

See [**Maven site**](http://pinguet62.github.io/JSFring)

## Licence

<img src="http://mirrors.creativecommons.org/presskit/buttons/88x31/png/by-nc-sa.png" height="25" />
[Creative Commons BY-NC-SA](http://creativecommons.org/licenses/by-nc-sa/4.0)
[![Creative Commons BY-NC-SA](https://img.shields.io/badge/Licence-Creative%20Commons-brightgreen.svg)](http://creativecommons.org/licenses/by-nc-sa/4.0)
