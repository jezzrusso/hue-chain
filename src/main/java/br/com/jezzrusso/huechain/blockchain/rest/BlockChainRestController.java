package br.com.jezzrusso.huechain.blockchain.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jezzrusso.huechain.block.Block;
import br.com.jezzrusso.huechain.blockchain.BlockChain;
import br.com.jezzrusso.huechain.domain.Request;
import br.com.jezzrusso.huechain.domain.Response;

@RestController
public class BlockChainRestController {

	private final BlockChain blockChain;

	@Autowired
	public BlockChainRestController(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	@RequestMapping(value = "/blocks", method = RequestMethod.GET)
	public ResponseEntity<Response<List<Block>>> getBlocks() {
		return ResponseEntity.ok().body(new Response<List<Block>>(blockChain.getBlockChain()));
	}

	@RequestMapping(value = "/block", method = RequestMethod.POST)
	public ResponseEntity<Response<String>> postBlock(@Valid @NotNull @RequestBody Request<String> req,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			Response<String> response = new Response<String>(req.getData());
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);

		}
		blockChain.addBlock(req.getData());
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, "/huechain/v1/blocks").build();
	}

}
