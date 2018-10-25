package br.com.jezzrusso.huechain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HuechainProperties {

	public static Long MINE_RATE;

	@Value("${huechain.minerate}")
	public void setMineRate(Long mineRate) {
		MINE_RATE = mineRate;
	}
	
}
