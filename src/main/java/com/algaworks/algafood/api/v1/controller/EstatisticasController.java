package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.model.output.EstatisticaDTO;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.api.v1.utils.LinkGenerator;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.view.VendaDiaria;
import com.algaworks.algafood.domain.service.PdfVendaRelatorioService;
import com.algaworks.algafood.domain.service.VendaDiariaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    @Autowired
    private VendaDiariaQueryService vendaDiariaQueryService;

    @Autowired
    private PdfVendaRelatorioService pdfVendaRelatorioService;

    @Autowired
    private LinkGenerator linkGenerator;

    @CheckSecurity.Estatisticas.PodeConsultar
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticaDTO estatisticas() {
        var estatisticasModel = new EstatisticaDTO();

        estatisticasModel.add(linkGenerator.linkToEstatisticasVendasDiarias());

        return estatisticasModel;
    }

    //venda diaria é usado apenas para leitura, não tem constraints de validação.
    // Se necessário, pode ter um DTO e um assembler.
    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendaDiariaQueryService.consultarVendasDiarias(filtro);
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(value = "/vendas-diarias/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro) {

        var bytesPdf = pdfVendaRelatorioService.emitirRelatorioEmPdf(filtro);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }
}

