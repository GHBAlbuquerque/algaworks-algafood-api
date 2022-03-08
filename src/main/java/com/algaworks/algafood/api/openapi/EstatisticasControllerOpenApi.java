package com.algaworks.algafood.api.openapi;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.view.VendaDiaria;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);

    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro);

}
