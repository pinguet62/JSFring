package fr.pinguet62.jsfring.webservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaComponent(
        val kafkaTemplate: KafkaTemplate<String, String>,
        @Value("\${KAFKA_TOPIC_PREFIX}") val kafkaTopicPrefix: String,
        val webSocket: WebSocketComponent
) {

    @KafkaListener(topics = ["\${KAFKA_TOPIC_PREFIX}default"])
    fun messageReseived(message: String) {
        println("[backend] Received: $message")
        webSocket.sendUserRightsUpdatedMessage()
    }

    // Test
    fun sendMessage(message: String) {
        println("[backend] Sent: $message")
        kafkaTemplate.send("${kafkaTopicPrefix}default", message)
    }

}
