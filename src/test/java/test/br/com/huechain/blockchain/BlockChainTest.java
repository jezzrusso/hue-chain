package test.br.com.huechain.blockchain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.config.HuechainProperties;
import test.mocks.WSMocks;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { HuechainProperties.class })
@TestPropertySource(properties = { "huechain.minerate=3000", "huechain.initBalance=10.00" })
public class BlockChainTest {

	@Test
	public void firstBlockMustBeGenesis() {
		BlockChain blockChain = new BlockChain(new WSMocks(null, null, null));
		Block block = blockChain.getBlockChain().get(0);

		assertEquals(Block.genesis().getLastHash(), block.getLastHash());
		assertEquals(Block.genesis().getData(), block.getData());
		assertEquals(Block.genesis().getTimestamp(), block.getTimestamp());
		assertEquals(Block.genesis().getHash(), block.getHash());
	}

	@Test
	public void secondBlockMustHaveLastHashEqualsHashGensis() {
		BlockChain blockChain = new BlockChain(new WSMocks(null, null, null));
		Block genesisBlock = blockChain.getBlockChain().get(0);
		Block secondBlock = blockChain.addBlock("primeira mineração");

		assertEquals(genesisBlock.getHash(), secondBlock.getLastHash());
		assertEquals(secondBlock.getData(), "primeira mineração");
		assertEquals(2, blockChain.getBlockChain().size());
	}

	@Test
	public void validateAValidChain() {
		BlockChain blockChain = new BlockChain(new WSMocks(null, null, null));
		Block secondBlock = blockChain.addBlock("didi mocó");

		assertEquals(Boolean.TRUE, blockChain.isValidChain(blockChain.getBlockChain()));
	}

	@Test
	public void invalidateACorruptChain() {
		BlockChain blockChain = new BlockChain(new WSMocks(null, null, null));
		blockChain.addBlock("didi mocó");

		Block blockGenesis = blockChain.getBlockChain().get(0);
		Block block = blockChain.getBlockChain().get(1);
		Block newBlock = new Block(123L, block.getHash(), "teste", "teste", 0L, 1);

		List<Block> simulatedChain = new ArrayList<Block>();
		simulatedChain.add(blockGenesis);
		simulatedChain.add(block);
		simulatedChain.add(newBlock);

		assertEquals(Boolean.FALSE, blockChain.isValidChain(simulatedChain));

	}

	@Test
	public void validateDifficulty() {
		BlockChain blockChain = new BlockChain(new WSMocks(null, null, null));
		blockChain.addBlock("dificuldade");
		blockChain.addBlock("dificuldade");

		assertEquals("Novo bloco deve seguir a dificuldade do resultado da adaptação",
				new String(new char[Block.adjustDifficulty(Block.genesis(),
						blockChain.getBlockChain().get(1).getTimestamp(), HuechainProperties.MINE_RATE)]).replace("\0",
								"0"),
				blockChain.getBlockChain().get(1).getHash().substring(0, Block.adjustDifficulty(Block.genesis(),
						blockChain.getBlockChain().get(1).getTimestamp(), HuechainProperties.MINE_RATE)));

		Integer adjustDifficulty = Block.adjustDifficulty(Block.genesis(),
				blockChain.getBlockChain().get(2).getTimestamp(), HuechainProperties.MINE_RATE);
		assertEquals("O outro bloco também deve seguir a configuração",
				new String(new char[Block.adjustDifficulty(blockChain.getBlockChain().get(1),
						blockChain.getBlockChain().get(2).getTimestamp(), HuechainProperties.MINE_RATE)]).replace("\0",
								"0"),
				blockChain.getBlockChain().get(2).getHash().substring(0, adjustDifficulty));
	}

}
