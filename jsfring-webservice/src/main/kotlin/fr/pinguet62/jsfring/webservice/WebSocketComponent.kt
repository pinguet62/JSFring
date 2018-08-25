package fr.pinguet62.jsfring.webservice

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class WebSocketComponent(val template: SimpMessagingTemplate) {

    fun sendUserRightsUpdatedMessage() {
        template.convertAndSend("/topic/USER_RIGHTS_UPDATED", "Your roles was updated.")
    }

    // Test
    @MessageMapping("/queue")
    fun messageReceived(message: String) {
        println("[backend] Received: $message")
    }

}
