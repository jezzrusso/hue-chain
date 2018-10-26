package br.com.jezzrusso.huechain.wallet;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.com.jezzrusso.huechain.config.HuechainProperties;

@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Wallet {

	private final BigDecimal balance;
	private final String keyPair;
	
	public Wallet(String keyPair) {
		this.balance = HuechainProperties.INIT_BALANCE;
		this.keyPair = keyPair;
	}
	
}
