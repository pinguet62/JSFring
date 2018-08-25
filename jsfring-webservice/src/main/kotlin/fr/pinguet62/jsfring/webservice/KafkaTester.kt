package fr.pinguet62.jsfring.webservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaTester(val controller: KafkaComponent) {

    @GetMapping("/test/kafka")
    fun test() {
        controller.sendMessage("test...")
    }

}
