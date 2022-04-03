package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PedidoAssembler;
import com.algaworks.algafood.api.v1.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.v1.model.output.PedidoDTO;
import com.algaworks.algafood.api.v1.model.output.PedidoSingletonDTO;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.ResourceUriHelper;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.enums.StatusPedidoEnum;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.StatusPedidoService;
import com.algaworks.algafood.infrastructure.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private StatusPedidoService statusPedidoService;

    @Autowired
    private PedidoAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private AlgaSecurity algaSecurity;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CollectionModel<PedidoDTO> listar() {
        var pedidos = pedidoRepository.findAll();
        return assembler.toCollectionModel(pedidos);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "view=resumo")
    public CollectionModel<PedidoDTO> listarResumido() {
        return listar();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pesquisar")
    public PagedModel<PedidoDTO> pesquisar(PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        var pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter), pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, assembler);
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigo}")
    public PedidoSingletonDTO buscar(@PathVariable String codigo) {
        var pedido = pedidoService.buscar(codigo);
        return assembler.toSingletonModel(pedido);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoSingletonDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInput) {
        try {
            var status = StatusPedidoEnum.traduzir(pedidoInput.getStatus());
            var pedido = assembler.toEntity(pedidoInput);

            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(algaSecurity.getUsuarioId());

            pedido = pedidoService.salvar(pedido);
            var model = assembler.toSingletonModel(pedido);

            ResourceUriHelper.addUriInResponseHeader(model.getCodigo());
            return model;
        } catch (EntidadeNaoEncontradaException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }
    }

    @PutMapping("/{codigo}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String codigo) {
        statusPedidoService.confirmar(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigo}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String codigo) {
        statusPedidoService.entregar(codigo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{codigo}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String codigo) {
        statusPedidoService.cancelar(codigo);
        return ResponseEntity.noContent().build();
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
