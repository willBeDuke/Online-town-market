package com.example.townmarket.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfigurer implements WebSocketMessageBrokerConfigurer {


  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint("/ws")
//        .setAllowedOrigins("http://localhost:8080", "http://127.0.0.1:5500")
        .setAllowedOrigins("https://api.knock-knock.shop", "https://www.knock-knock.shop")
        .withSockJS()
    ;
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.setApplicationDestinationPrefixes("/pub"); // 보내는 쪽
    config.enableSimpleBroker("/sub"); // 받는 쪽
  }

//  @Override
//  public void configureClientInboundChannel(ChannelRegistration registration) {
//    registration.interceptors(stompHandler);
//  }
}
