package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.model.entrada.FormaPagamentoEntradaDTO;
import com.algaworks.algafood.api.model.saida.FormaPagamentoDTO;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoService formaPagamentoService;

	@Autowired
	private FormaPagamentoAssembler assembler;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<FormaPagamentoDTO> listar() {
		var formasPagamento = formaPagamentoRepository.findAll();

		return formasPagamento.stream().map(formaPagamento -> assembler.convertToModel(formaPagamento)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public FormaPagamentoDTO buscar(@PathVariable long id) {
		var formaPagamento = formaPagamentoService.buscar(id);
		return assembler.convertToModel(formaPagamento);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoEntradaDTO formaPagamentoEntrada) {
		var formaPagamentoRecebida = assembler.convertToEntity(formaPagamentoEntrada);
		var formaPagamento = formaPagamentoService.salvar(formaPagamentoRecebida);
		return assembler.convertToModel(formaPagamento);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable long id, @RequestBody FormaPagamentoEntradaDTO formaPagamentoEntrada) {
		var formaPagamentoExistente = formaPagamentoService.buscar(id);
		assembler.copyToInstance(formaPagamentoEntrada, formaPagamentoExistente);

		var formaPagamento = formaPagamentoService.salvar(formaPagamentoExistente);
		return ResponseEntity.ok(assembler.convertToModel(formaPagamento));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable long id) {
		formaPagamentoService.remover(id);
		return ResponseEntity.noContent().build();
			
	}

}
