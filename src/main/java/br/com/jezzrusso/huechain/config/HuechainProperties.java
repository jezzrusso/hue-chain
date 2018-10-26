package br.com.jezzrusso.huechain.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HuechainProperties {

	public static Long MINE_RATE;
	public static BigDecimal INIT_BALANCE;

	@Value("${huechain.minerate}")
	public void setMineRate(Long mineRate) {
		MINE_RATE = mineRate;
	}
	
	@Value("${huechain.initBalance}")
	public void setMineRate(BigDecimal initBalance) {
		INIT_BALANCE = initBalance;
	}
}
