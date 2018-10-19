package test.mocks;

import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.blockchain.ws.SocketHandler;
import br.com.jezzrusso.huechain.blockchain.ws.WebSocketOperations;

public class WSMocks extends WebSocketOperations {

	public WSMocks(String wsAdd, BlockChain blockchain, SocketHandler socketHandler) {
		super(wsAdd, blockchain, socketHandler);
	}
	
	
	
	

}
