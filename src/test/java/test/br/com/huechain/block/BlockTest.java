package test.br.com.huechain.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.config.HuechainProperties;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { HuechainProperties.class })
@TestPropertySource(properties = {
	    "huechain.minerate=3000",
	})
public class BlockTest {

	@Autowired
	private HuechainProperties initialized;

	@Test
	public void mustPrintTheBlock() {
		Long timeInMillis = Calendar.getInstance().getTimeInMillis();

		String stringBlock = "{Block:{timestamp:" + timeInMillis
				+ ",lastHash:0x00000000000000000000000,hash:0xg3n35150000235A231C667,data:Primeiro Bloco,nonce:1,difficulty:3}}";

		Block block = new Block(timeInMillis, "0x00000000000000000000000", "0xg3n35150000235A231C667", "Primeiro Bloco",
				1L, 3);

		assertEquals(stringBlock, block.toString());

	}

	@Test
	public void genesisMustBeEternal() {
		String genesisOne = Block.genesis().toString();
		String genesisTwo = Block.genesis().toString();

		assertEquals(genesisTwo, genesisOne);
	}

	@Test
	public void mineBlockMustWorks() {
		Block genesis = Block.genesis();
		Block hueBlock = Block.mineBlock(genesis, "hue");
		assertEquals("lastHash must be equals gensis block in second block in blockchain", genesis.getHash(),
				hueBlock.getLastHash());
		assertNotNull("hash of block should be not empty", hueBlock.getHash());

	}

	@Test
	public void addDifficulty() {
		Block genesis = Block.genesis();

		assertEquals(Block.adjustDifficulty(genesis, genesis.getTimestamp(), 10000L),
				new Integer(genesis.getDifficulty() + 1));
	}

	@Test
	public void decreaseDifficulty() {
		Block genesis = Block.genesis();

		assertEquals(Block.adjustDifficulty(genesis, genesis.getTimestamp() + 10001, 10000L),
				new Integer(genesis.getDifficulty() - 1));
	}

	@Test
	public void hashMustMatchDifficulty() {
		Block genesis = Block.genesis();
		Block hueBlock = Block.mineBlock(genesis, "hue");
		assertEquals(hueBlock.getHash().substring(0, hueBlock.getDifficulty()),
				new String(new char[hueBlock.getDifficulty()]).replace("\0", "0"));

	}

}
