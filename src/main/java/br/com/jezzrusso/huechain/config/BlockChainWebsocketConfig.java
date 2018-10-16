package br.com.jezzrusso.huechain.config;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.blockchain.ws.SocketHandler;

@Configuration
@EnableWebSocket
public class BlockChainWebsocketConfig implements WebSocketConfigurer {

	@Autowired
	private BlockChain blockchain;
	
	@Autowired
	private SocketHandler socketHandler;
	
	@Value("${wsserver.address}")
	private String websocketAddress;
	
//	@LocalServerPort   
//	private String selfPort;
			
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new SocketHandler(blockchain), "/ws");
		
	}
	
	public WebSocketSession createSession() throws InterruptedException, ExecutionException {
		if(StringUtils.isEmpty(websocketAddress)) {
			WebSocketClient client = new StandardWebSocketClient();
			return client.doHandshake(socketHandler, String.format("ws://localhost:8080/huechain/v1/ws")).get();
		}
		WebSocketClient client = new StandardWebSocketClient();
		return client.doHandshake(socketHandler, websocketAddress).get();
	}
	
	



}
