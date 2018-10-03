package br.com.jezzrusso.huechain.blockchain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.com.jezzrusso.huechain.block.Block;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BlockChain {

	private final List<Block> chain;

	@Autowired
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

	public Boolean isValidChain(final List<Block> chain) {
		if (!Block.genesis().equals(chain.get(0)))
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

}
