package test.br.com.huechain.blockchain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	public void validateAValidChain() {
		BlockChain blockChain = new BlockChain();
		Block secondBlock = blockChain.addBlock("didi mocó");

		assertEquals(Boolean.TRUE, blockChain.isValidChain(blockChain.getBlockChain()));
	}
	
	@Test
	public void invalidateACorruptChain() {
		BlockChain blockChain = new BlockChain();
		blockChain.addBlock("didi mocó");
		
		Block blockGenesis = blockChain.getBlockChain().get(0);
		Block block = blockChain.getBlockChain().get(1);
		Block newBlock = new Block(123L, block.getHash(), "teste", "teste");
		
		List<Block> simulatedChain = new ArrayList<Block>();
		simulatedChain.add(blockGenesis);
		simulatedChain.add(block);
		simulatedChain.add(newBlock);
		
		assertEquals(Boolean.FALSE, blockChain.isValidChain(simulatedChain)); 
		
	}

}
