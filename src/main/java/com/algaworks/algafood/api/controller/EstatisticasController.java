package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.view.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaDiariaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaDiariaQueryService vendaDiariaQueryService;

    //venda diaria é usado apenas para leitura, não tem constraints de validação.
    // Se necessário, pode ter um DTO e um assembler.
    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro){
        return vendaDiariaQueryService.consultarVendasDiarias(filtro);
    }
}
