package br.com.jezzrusso.huechain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.blockchain.ws.SocketHandler;

@Configuration
@EnableWebSocket
public class BlockChainWebsocketConfig implements WebSocketConfigurer {

	@Autowired
	private SocketHandler socketHandler;
	
	@Value("${wsserver.address}")
	private String websocketAddress;
	
			
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(socketHandler, "/ws");
		
	}
	
}
