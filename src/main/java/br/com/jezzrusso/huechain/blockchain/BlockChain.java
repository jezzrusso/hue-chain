package br.com.jezzrusso.huechain.blockchain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.blockchain.ws.SocketHandler;
import br.com.jezzrusso.huechain.blockchain.ws.WSMessage;
import br.com.jezzrusso.huechain.blockchain.ws.WebSocketOperations;
import br.com.jezzrusso.huechain.enums.MessageType;
import br.com.jezzrusso.huechain.exceptions.UnmodifiedBlockchainException;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BlockChain {

	private static final Logger LOGGER = LoggerFactory.getLogger(BlockChain.class);
	private List<Block> chain;
	private final SocketHandler socketHandler;
	
	@Autowired
	public BlockChain(SocketHandler socketHandler) {
		this.chain = new ArrayList<>();
		this.chain.add(Block.genesis());
		this.socketHandler = socketHandler;
	}

	public Block addBlock(String data) {
		Block newBlock = Block.mineBlock(this.chain.get(this.chain.size() - 1).getHash(), data);
		ArrayList<Block> chain = new ArrayList<>(this.chain);
		chain.add(newBlock);
		try {
			this.replaceBlockchain(chain);
			//TODO: broadcast
			broadcast();
		} catch (UnmodifiedBlockchainException e) {
			LOGGER.info(e.getMessage());
		}
		return newBlock;
	}
	
	private void broadcast() {
		try {
			
			socketHandler.synchChain();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public List<Block> getBlockChain() {
		return Collections.unmodifiableList(this.chain);
	}

	public Boolean isValidChain(final List<Block> chain) {
		if (!Block.genesis().equals((Block) chain.get(0)))
			return Boolean.FALSE;

		for (Integer i = 1; i < chain.size(); i++) {

			final Block block = chain.get(i);
			final Block lastBlock = chain.get(i - 1);

			if (!block.getLastHash().equals(lastBlock.getHash()) || !block.getHash().equals(Block.blockHash(block))) {
				return Boolean.FALSE;
			}

		}

		return Boolean.TRUE;
	}

	private List<Block> generateBlockchain(final List<Block> newBlockChain) {
		if (newBlockChain.size() <= this.getBlockChain().size()) {
			LOGGER.info("A cadeia de blocos recebida não é maior e não precisa ser realocada.");
		} else if (!isValidChain(newBlockChain)) {
			LOGGER.warn("A nova cadeia de blocos não é valida e não será realocada");
			return this.chain;
		}

		return newBlockChain;
	}

	public void replaceBlockchain(final List<Block> newBlockChain) throws UnmodifiedBlockchainException {
		final List<Block> newBlockchain = generateBlockchain(newBlockChain);

		if (this.chain.size() >= newBlockChain.size()) {
			throw new UnmodifiedBlockchainException();
		}
		this.chain = newBlockchain;

	}

}
