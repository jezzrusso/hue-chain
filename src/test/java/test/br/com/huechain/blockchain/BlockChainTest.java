package test.br.com.huechain.blockchain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.blockchain.BlockChain;

public class BlockChainTest {
	
	@Test
	public void firstBlockMustBeGenesis() {
		BlockChain blockChain = new BlockChain();
		Block block = blockChain.getBlockChain().get(0);
		
		assertEquals(Block.genesis().getLastHash(), block.getLastHash());
		assertEquals(Block.genesis().getData(), block.getData());
		assertEquals(Block.genesis().getTimestamp(), block.getTimestamp());
		assertEquals(Block.genesis().getHash(), block.getHash());
	}
	
	@Test
	public void secondBlockMustHaveLastHashEqualsHashGensis() {
		BlockChain blockChain = new BlockChain();
		Block genesisBlock = blockChain.getBlockChain().get(0);
		Block secondBlock = blockChain.addBlock("primeira mineração");
		
		assertEquals(genesisBlock.getHash(), secondBlock.getLastHash());
		assertEquals(secondBlock.getData(), "primeira mineração");
		assertEquals(2, blockChain.getBlockChain().size());
	}

}
