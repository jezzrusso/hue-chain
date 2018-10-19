package br.com.jezzrusso.huechain.blockchain.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.enums.MessageType;

public class WSMessage implements Serializable {

	private static final long serialVersionUID = 4181623028206456214L;

	private MessageType messageType;
	private List<Block> blockchain;

	public WSMessage() {

	}

	public WSMessage(MessageType messageType, ArrayList<Block> blockchain) {
		this.messageType = messageType;
		this.blockchain = blockchain;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public List<Block> getBlockchain() {
		return blockchain;
	}

	public void setBlockchain(List<Block> blockchain) {
		this.blockchain = blockchain;
	}

}
