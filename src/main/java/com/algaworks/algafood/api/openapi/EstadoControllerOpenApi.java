package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import com.algaworks.algafood.api.model.output.EstadoDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    public List<EstadoDTO> listar();

    public EstadoDTO buscar(@PathVariable long id);

    public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInput);

    public ResponseEntity<EstadoDTO> atualizar(@PathVariable long id, @RequestBody @Valid EstadoInputDTO estadoInput);

    public ResponseEntity<?> deletar(@PathVariable long id);

}
