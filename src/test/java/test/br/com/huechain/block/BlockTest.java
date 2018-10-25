package test.br.com.huechain.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.jezzrusso.huechain.block.Block;

public class BlockTest {

	@Test
	public void mustPrintTheBlock() {
		Long timeInMillis = Calendar.getInstance().getTimeInMillis();

		String stringBlock = "{Block:{timestamp:" + timeInMillis
				+ ",lastHash:0x00000000000000000000000,hash:0xg3n35150000235A231C667,data:Primeiro Bloco,nonce:1}}";

		Block block = new Block(timeInMillis, "0x00000000000000000000000", "0xg3n35150000235A231C667",
				"Primeiro Bloco", 1L, 1);

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
	
	
	

}
