package br.com.jezzrusso.huechain.blockchain.ws;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.google.gson.Gson;

import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.enums.MessageType;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WebSocketOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketOperations.class);

	private String websocketAddress;

	private final BlockChain blockchain;

	private final SocketHandler socketHandler;

	@Autowired
	public WebSocketOperations(@Value("${wsserver.address}") String websocketAddress, BlockChain blockchain,
			SocketHandler socketHandler) {
		this.blockchain = blockchain;
		this.socketHandler = socketHandler;
		this.websocketAddress = websocketAddress;
	}

	public WebSocketSession connect() throws InterruptedException, ExecutionException, TimeoutException {

		SocketHandler handler = socketHandler;
		WebSocketClient client = new StandardWebSocketClient();

		if (!StringUtils.isEmpty(websocketAddress)) {
			return client.doHandshake(handler, websocketAddress).get();
		}
		
		return null;

	}

	public void synchChain() throws IOException {

		for (WebSocketSession webSocketSession : socketHandler.getSessions()) {
			synchronized (webSocketSession) {
				WSMessage wsMessage = new WSMessage();
				wsMessage.setMessageType(MessageType.CHAIN);
				wsMessage.setBlockchain(blockchain.getBlockChain());
				webSocketSession.sendMessage(new TextMessage(new Gson().toJson(wsMessage)));
			}
		}
	}

}
