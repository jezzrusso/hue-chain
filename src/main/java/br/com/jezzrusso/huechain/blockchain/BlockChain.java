package br.com.jezzrusso.huechain.blockchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.jezzrusso.huechain.block.Block;

public class BlockChain {
	
	private final List<Block> chain;;
	
	public BlockChain() {
		this.chain = new ArrayList<>();
		this.chain.add(Block.genesis());
	}
	
	public Block addBlock(String data) {
		Block newBlock = Block.mineBlock(this.chain.get(this.chain.size() - 1).getHash(), data);
		this.chain.add(newBlock);
		return newBlock;
	}
	
	public List<Block> getBlockChain() {
		return Collections.unmodifiableList(this.chain);
	}

}
