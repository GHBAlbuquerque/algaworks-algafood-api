package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.model.output.PedidoDTO;
import com.algaworks.algafood.api.model.output.PedidoSimpleDTO;
import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoAssembler assembler;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<PedidoSimpleDTO> listar() {
		var pedidos = pedidoRepository.findAll();
		return assembler.convertListToSimpleModel(pedidos);
	}

	@GetMapping("/{id}")
	public PedidoDTO buscar(@PathVariable long id) {
		var pedido = pedidoService.buscar(id);
		return assembler.convertToModel(pedido);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInput) {
		try {
			var status = StatusPedidoEnum.getFromName(pedidoInput.getStatus());

			var pedido = assembler.convertToEntity(pedidoInput);
			pedido = pedidoService.salvar(pedido);
			return assembler.convertToModel(pedido);
		} catch (EntidadeNaoEncontradaException ex) {
			throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoDTO> atualizar(@PathVariable long id, @RequestBody @Valid PedidoInputDTO pedidoInput) {
		var pedidoExistente = pedidoService.buscar(id);
		assembler.copyToInstance(pedidoInput, pedidoExistente);

		var pedido = pedidoService.salvar(pedidoExistente);
		return ResponseEntity.ok(assembler.convertToModel(pedido));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		pedidoService.remover(id);
		return ResponseEntity.noContent().build();
	}

}
