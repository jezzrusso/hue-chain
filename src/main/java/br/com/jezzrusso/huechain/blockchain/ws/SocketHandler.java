package br.com.jezzrusso.huechain.blockchain.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.enums.MessageType;
import br.com.jezzrusso.huechain.exceptions.UnmodifiedBlockchainException;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SocketHandler extends TextWebSocketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
	
	private final List<WebSocketSession> sessions;

	private final BlockChain blockchain;
	
	@Autowired
	public SocketHandler(BlockChain blockchain) {
		this.blockchain = blockchain;
		this.sessions = new ArrayList<>();
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		try {
			WSMessage wsMessage = new Gson().fromJson(message.getPayload(), WSMessage.class);

			if (wsMessage.getMessageType().equals(MessageType.CHAIN)) {

					blockchain.replaceBlockchain(wsMessage.getBlockchain());
					synchChain();

			}
		} catch (IllegalStateException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (UnmodifiedBlockchainException e) {
			LOGGER.info(e.getMessage());
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		WSMessage wsMessage = new WSMessage();
		wsMessage.setBlockchain(blockchain.getBlockChain());
		wsMessage.setMessageType(MessageType.CHAIN);

		LOGGER.info(session.getRemoteAddress().getHostName().concat(":")
				.concat(String.format("%d", session.getRemoteAddress().getPort()).concat(" conectou.")));

		session.sendMessage(new TextMessage(new Gson().toJson(wsMessage)));
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.removeIf(s -> session.getId().equals(s.getId()));
		LOGGER.info(session.getRemoteAddress().getHostName().concat(":")
				.concat(String.format("%d", session.getRemoteAddress().getPort()).concat(" desconectou.")));

	}

	public List<WebSocketSession> getSessions() {
		return Collections.unmodifiableList(this.sessions);
	}

	private void synchChain() throws IOException {

		for (WebSocketSession webSocketSession : sessions) {
			synchronized (webSocketSession) {
				WSMessage wsMessage = new WSMessage();
				wsMessage.setMessageType(MessageType.CHAIN);
				wsMessage.setBlockchain(blockchain.getBlockChain());
				webSocketSession.sendMessage(new TextMessage(new Gson().toJson(wsMessage)));
			}
		}
	}
}
