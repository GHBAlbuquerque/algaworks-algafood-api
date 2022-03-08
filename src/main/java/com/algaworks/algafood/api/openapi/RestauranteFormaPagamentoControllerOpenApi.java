package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.output.FormaPagamentoDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    public List<FormaPagamentoDTO> listar(@PathVariable Long idRestaurante);

    public void adicionar(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento);

    public void remover(@PathVariable Long idRestaurante, @PathVariable Long idFormaPagamento);
}
