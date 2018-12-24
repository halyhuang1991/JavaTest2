package com.test.Webserver.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 允许连接的域,只能以http或https开头
        String[] allowsOrigins = { "*" };

        // WebIM WebSocket通道
        registry.addHandler(chatWebSocketHandler(), "/webSocketIMServer").setAllowedOrigins(allowsOrigins)
                .addInterceptors(myInterceptor());
        registry.addHandler(chatWebSocketHandler(), "/sockjs/webSocketIMServer").setAllowedOrigins(allowsOrigins)
                .addInterceptors(myInterceptor()).withSockJS();
    }

    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }

    @Bean
    public WebSocketHandshakeInterceptor myInterceptor() {
        return new WebSocketHandshakeInterceptor();
    }
}