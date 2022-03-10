package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.api.model.output.RestauranteDTO;
import com.algaworks.algafood.api.model.output.RestauranteSingletonDTO;
import com.algaworks.algafood.validation.OrderedChecksTaxaFrete;
import io.swagger.annotations.Api;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    public CollectionModel<RestauranteDTO> listar();

    public MappingJacksonValue buscar(@PathVariable long id, @RequestParam(required = false) String campos);

    public ResponseEntity<CollectionModel<RestauranteDTO>> buscarPorNomeECozinha(@PathParam(value = "nome") String nome,
                                                                      @PathParam(value = "cozinha_id") Long cozinhaId);

    public ResponseEntity<RestauranteDTO> buscarPorNome(@PathParam(value = "nome") String nome);

    public int quantidadePorCozinhaId(Long cozinhaId);

    public CollectionModel<RestauranteDTO> queryPorSpecification(String nome);

    public RestauranteSingletonDTO adicionar(@RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class})
                                                     RestauranteInputDTO restauranteInput);

    public ResponseEntity<RestauranteSingletonDTO> atualizar(@PathVariable long id,
                                                             @RequestBody @Validated({OrderedChecksTaxaFrete.class, Default.class})
                                                                     RestauranteInputDTO restauranteInput);

    public ResponseEntity<?> deletar(@PathVariable long id);

    public ResponseEntity<RestauranteSingletonDTO> atualizarParcial(@PathVariable long id,
                                                                    @RequestBody Map<String, Object> campos,
                                                                    HttpServletRequest request);

    public ResponseEntity<Void> ativar(@PathVariable long id);

    public ResponseEntity<Void> desativar(@PathVariable long id);

    public void ativarMultiplos(@RequestBody List<Long> ids);

    public void desativarMultiplos(@RequestBody List<Long> ids);

    public void abrir(@PathVariable long id);

    public void fechar(@PathVariable long id);

}
