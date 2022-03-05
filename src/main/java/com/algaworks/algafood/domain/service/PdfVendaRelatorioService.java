package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface PdfVendaRelatorioService {

    byte[] emitirRelatorioEmPdf(VendaDiariaFilter filtro);
}