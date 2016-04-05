# Configuration

## Application configuration file

### General configuration

General configuration is set into `src/main/resources/application.properties` file.

Spring Boot auto-detect and load this file.

### Module configuration

The configuration file paths must be unique.
Only 1 `application.properties` can be packaged: if a module contains also this file, only 1 is keeped and other are crushed.

To define a configuration file for a module, filename is suffixed by module name, and configuration file is loaded by Spring with `@PropertySource`.

Example for `mymodule` module:

* Configuration file: `%mymodule%/src/main/resources/application-mymodule.properties`
* Configuration class: in base package:
	```
	package fr.pinguet62.jsfring.mymodule;

	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.PropertySource;

	@Configuration
	@PropertySource("classpath:/application-mymodule.properties")
	public class MymoduleConfig {}
	```

## Environment

The configuration depending on environment is defined in external configuration: command line, SPRING_APPLICATION argument, system variable, ...

https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

### Datasources

Connection string use to connect to **databases**:

| Name                      |
|---------------------------|
| `spring.data.mongodb.uri` |
| `spring.datasource.url`   |

### SMTP

Serveur used to send **emails**:

| Name                      |
|---------------------------|
| `spring.mail.host`        |
| `spring.mail.password`    |
| `spring.mail.port`        |
| `spring.mail.username`    |

### GitHub

Account used to **publish site** to GitHub:

| Name              |
|-------------------|
| `GITHUB_USERNAME` |
| `GITHUB_PASSWORD` |
