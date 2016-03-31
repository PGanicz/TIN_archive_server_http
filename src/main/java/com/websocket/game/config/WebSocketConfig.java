package com.websocket.game.config;

import com.websocket.game.web.GameHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	

	


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(gameHandler(),"/game").withSockJS();
	}

	@Bean
	public WebSocketHandler gameHandler()
	{
		return new GameHandler();
	}
}