package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.view.VendaDiaria;
import com.algaworks.algafood.infrastructure.service.PdfVendaRelatorioService;
import com.algaworks.algafood.infrastructure.service.VendaDiariaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaDiariaQueryService vendaDiariaQueryService;

    @Autowired
    private PdfVendaRelatorioService pdfVendaRelatorioService;

    //venda diaria é usado apenas para leitura, não tem constraints de validação.
    // Se necessário, pode ter um DTO e um assembler.
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro){
        return vendaDiariaQueryService.consultarVendasDiarias(filtro);
    }

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

