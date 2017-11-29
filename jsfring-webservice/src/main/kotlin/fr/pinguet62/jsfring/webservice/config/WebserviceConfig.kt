package fr.pinguet62.jsfring.webservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:/application-webservice.properties")
open class WebserviceConfig
