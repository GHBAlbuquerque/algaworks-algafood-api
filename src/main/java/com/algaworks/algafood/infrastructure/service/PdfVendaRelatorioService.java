package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface PdfVendaRelatorioService {

    byte[] emitirRelatorioEmPdf(VendaDiariaFilter filtro);
}