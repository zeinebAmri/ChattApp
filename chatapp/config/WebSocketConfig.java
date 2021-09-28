package com.kth.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

//reference: https://spring.io/guides/gs/messaging-stomp-websocket/

@Configuration
@EnableWebSocketMessageBroker //enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    //Configures the message broker.
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	//enable a simple memory-based message broker
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    //This method registers the /chat endpoint, enabling SockJS fallback options
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint("/chat");
         registry.addEndpoint("/chat").withSockJS();
    }
}


