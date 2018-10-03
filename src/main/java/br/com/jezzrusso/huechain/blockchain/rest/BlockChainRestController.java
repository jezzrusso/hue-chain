package br.com.jezzrusso.huechain.blockchain.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.blockchain.BlockChain;

@RestController
public class BlockChainRestController {

	private final BlockChain blockChain;

	@Autowired
	public BlockChainRestController(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	@RequestMapping(value = "/blocks", method = RequestMethod.GET)
	public ResponseEntity<List<Block>> getBlocks() {
		return ResponseEntity.ok().body(blockChain.getBlockChain());
	}

}
