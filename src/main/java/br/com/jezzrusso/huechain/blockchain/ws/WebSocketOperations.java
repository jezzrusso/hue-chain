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
import br.com.jezzrusso.huechain.blockchain.rest.BlockChainRestController;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WebSocketOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketOperations.class);
	
	private final String websocketAddress;

	private final BlockChain blockchain;

	private final SocketHandler socketHandler;

	private WebSocketSession wsSession;
	
	public WebSocketOperations(@Value("${wsserver.address}") String websocketAddress, @Autowired BlockChain blockchain,
			@Autowired SocketHandler socketHandler) {
		this.websocketAddress = websocketAddress;
		this.blockchain = blockchain;
		this.socketHandler = socketHandler;
	}

	public void sendMessage(WSMessage wsMessage)
			throws IOException, InterruptedException, ExecutionException, TimeoutException {
		String message = new Gson().toJson(wsMessage);
		WebSocketSession session = connect();
		if (session == null) {
			return;
		}
		session.sendMessage(new TextMessage(message));
	}

	public WebSocketSession connect() throws InterruptedException, ExecutionException, TimeoutException {
		if (wsSession != null && wsSession.isOpen()) {
			return wsSession;
		}

		SocketHandler handler = socketHandler;
		WebSocketClient client = new StandardWebSocketClient();

		if (!StringUtils.isEmpty(websocketAddress)) {
			wsSession = client.doHandshake(handler, websocketAddress).get();
		}
		return wsSession;
	}
	
	public void synchChain() {
		
	}

}
