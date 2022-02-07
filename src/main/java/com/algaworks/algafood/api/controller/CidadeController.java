package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeAssembler;
import com.algaworks.algafood.api.model.entrada.CidadeEntradaDTO;
import com.algaworks.algafood.api.model.saida.CidadeDTO;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.entitynotfound.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeAssembler assembler;

    @GetMapping
    public List<CidadeDTO> listar() {
        var cidades = cidadeRepository.findAll();
        return cidades.stream().map(cidade -> assembler.convert(cidade)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable long id) {
        var cidade = cadastroCidadeService.buscar(id);
        return assembler.convert(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeEntradaDTO cidadeEntrada) {
        var cidade = assembler.convert(cidadeEntrada);
        try {
            var cidadeSalva = cadastroCidadeService.salvar(cidade);
            return assembler.convert(cidadeSalva);
        } catch (EstadoNaoEncontradoException ex) {
            throw new EntidadeReferenciadaInexistenteException(ex.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable long id, @RequestBody CidadeEntradaDTO cidadeEntrada) {
        var cidade = assembler.convert(cidadeEntrada);
        var cidadeExistente = cadastroCidadeService.buscar(id);

        BeanUtils.copyProperties(cidade, cidadeExistente, "id");

        try {
            cidade = cadastroCidadeService.salvar(cidadeExistente);
            return ResponseEntity.ok(assembler.convert(cidadeExistente));
        } catch (EstadoNaoEncontradoException ex) {
            var idEstado = cidade.getEstado().getId();
            throw new EntidadeReferenciadaInexistenteException(Estado.class, idEstado);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable long id) {
        cadastroCidadeService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
