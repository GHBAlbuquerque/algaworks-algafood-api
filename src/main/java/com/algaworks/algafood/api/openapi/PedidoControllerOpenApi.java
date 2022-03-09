package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.model.output.PedidoDTO;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({ //documentando em uma unica operação esse e o endpoint de baixo
            @ApiImplicitParam(value = "Nome da view", name = "view", paramType = "query", dataTypeClass = String.class,
                    allowableValues = "resumo", required = false)})
    public CollectionModel<PedidoDTO> listar();

    @ApiOperation(value = "Listar Pedidos Resumo", hidden = true)
    public CollectionModel<PedidoDTO> listarResumido();

    public PagedModel<PedidoDTO> pesquisar(PedidoFilter filter, @PageableDefault(size = 10) Pageable pageable);

    public PedidoDTO buscar(@PathVariable String codigo);

    public PedidoDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInput);

    public void confirmar(@PathVariable String codigo);

    public void entregar(@PathVariable String codigo);

    public void cancelar(@PathVariable String codigo);
}
