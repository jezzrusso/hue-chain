package br.com.jezzrusso.huechain;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.WebSocketSession;

import br.com.jezzrusso.huechain.blockchain.ws.WSMessage;
import br.com.jezzrusso.huechain.blockchain.ws.WebSocketOperations;
import br.com.jezzrusso.huechain.enums.MessageType;

@SpringBootApplication
public class Huechain {

	private static final Logger LOGGER = LoggerFactory.getLogger(Huechain.class);

	@Autowired
	private WebSocketOperations wsOperations;
	
	public static void main(String[] args) {
		SpringApplication.run(Huechain.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void init() throws InterruptedException, ExecutionException, TimeoutException, IOException {
		wsOperations.connect();
	}


}
