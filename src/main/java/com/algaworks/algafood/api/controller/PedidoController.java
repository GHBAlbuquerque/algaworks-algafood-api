package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.model.output.PedidoDTO;
import com.algaworks.algafood.api.model.view.PedidoView;
import com.algaworks.algafood.api.openapi.PedidoControllerOpenApi;
import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.StatusPedidoService;
import com.algaworks.algafood.infrastructure.spec.PedidoSpecs;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private StatusPedidoService statusPedidoService;

    @Autowired
    private PedidoAssembler assembler;

    @JsonView(PedidoView.PedidoSimpleDTO.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PedidoDTO> listar() {
        var pedidos = pedidoRepository.findAll();
        return assembler.convertListToModel(pedidos);
    }

    @JsonView(PedidoView.PedidoIdentificationDTO.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "view=resumo")
    public List<PedidoDTO> listarResumido() {
        return listar();
    }

    //@JsonView(PedidoView.PedidoSimpleDTO.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pesquisar")
    public Page<PedidoDTO> pesquisar(PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        var pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter), pageable);
        var pedidos = assembler.convertListToModel(pedidosPage.getContent());

        var pagina = new PageImpl<>(pedidos, pageable, pedidosPage.getTotalElements());
        return pagina;
    }

    @GetMapping("/{codigo}")
    public PedidoDTO buscar(@PathVariable String codigo) {
        var pedido = pedidoService.buscar(codigo);
        return assembler.convertToModel(pedido);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInput) {
        try {
            var status = StatusPedidoEnum.traduzir(pedidoInput.getStatus());

            var pedido = assembler.convertToEntity(pedidoInput);
            pedido = pedidoService.salvar(pedido);
            return assembler.convertToModel(pedido);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @PutMapping("/{codigo}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigo) {
        statusPedidoService.confirmar(codigo);
    }

    @PutMapping("/{codigo}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigo) {
        statusPedidoService.entregar(codigo);
    }

    @DeleteMapping("/{codigo}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigo) {
        statusPedidoService.cancelar(codigo);
    }

	/*
	@PutMapping("/{id}")
	public ResponseEntity<PedidoDTO> atualizar(@PathVariable long id, @RequestBody @Valid PedidoInputDTO pedidoInput) {
		var pedidoExistente = pedidoService.buscar(id);
		assembler.copyToInstance(pedidoInput, pedidoExistente);

		var pedido = pedidoService.salvar(pedidoExistente);
		return ResponseEntity.ok(assembler.convertToModel(pedido));
	}
	*/

}
