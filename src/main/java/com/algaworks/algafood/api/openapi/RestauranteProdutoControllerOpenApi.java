package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.model.input.update.ProdutoUpdateDTO;
import com.algaworks.algafood.api.model.output.ProdutoDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    public List<ProdutoDTO> listar(@PathVariable Long idRestaurante,
                                   @RequestParam(required = false) boolean incluirInativos);

    public ProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto);

    public ProdutoDTO adicionar(@PathVariable Long idRestaurante, @RequestBody @Valid ProdutoInputDTO produtoInputDTO);

    public void atualizar(@PathVariable Long idRestaurante, @PathVariable Long idProduto, @RequestBody @Valid ProdutoUpdateDTO produtoInputDTO);

    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idProduto);
}


