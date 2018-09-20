package test.br.com.huechain.block;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import br.com.jezzrusso.huechain.block.Block;

public class BlockTest {

	@Test
	public void mustPrintTheBlock() {
		Long timeInMillis = Calendar.getInstance().getTimeInMillis();
		
		String stringBlock = "{Block:{timestamp:" + timeInMillis
				+ ",lastHash:0x00000000000000000000000,hash:0xg3n35150000235A231C667,data:Primeiro Bloco}}";
		
		Block block = new Block(timeInMillis, "0x00000000000000000000000",
				"0xg3n35150000235A231C667", "Primeiro Bloco");
		
		assertEquals(stringBlock, block.toString());

	}

}
