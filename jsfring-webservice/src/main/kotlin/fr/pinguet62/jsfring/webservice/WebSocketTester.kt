package fr.pinguet62.jsfring.webservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebSocketTester(val webSocket: WebSocketComponent) {

    @GetMapping("/test/websocket")
    fun test() {
        webSocket.sendUserRightsUpdatedMessage()
    }

}
