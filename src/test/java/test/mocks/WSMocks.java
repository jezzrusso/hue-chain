package test.mocks;

import java.io.IOException;

import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.blockchain.ws.SocketHandler;
import br.com.jezzrusso.huechain.blockchain.ws.WebSocketOperations;

public class WSMocks extends WebSocketOperations {

	public WSMocks(String wsAdd, BlockChain blockchain, SocketHandler socketHandler) {
		super(wsAdd, blockchain, socketHandler);
	}
	
	@Override
	public void synchChain() throws IOException {
		//do nothing
	}

}
