package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.output.FotoProdutoDTO;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags="restaurante-controller")
@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
                                        @Valid FotoProdutoInputDTO fotoProdutoInputDTO) throws IOException {

        var produto = produtoService.buscar(idRestaurante, idProduto);
        var arquivo = fotoProdutoInputDTO.getArquivo();

        var fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInputDTO.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        var fotoProdutoSalva = catalogoFotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());
        return fotoProdutoAssembler.convertToModel(fotoProdutoSalva);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        var foto = catalogoFotoProdutoService.buscar(idRestaurante, idProduto);
        return fotoProdutoAssembler.convertToModel(foto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        catalogoFotoProdutoService.deletar(idRestaurante, idProduto);
    }

    @GetMapping
    public ResponseEntity<?> recuperar(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
                                       @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            var fotoProduto = catalogoFotoProdutoService.buscar(idRestaurante, idProduto);
            var mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            var mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            //retorno o inputStream (bytes)
            var fotoRecuperada = catalogoFotoProdutoService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getURL())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }

    }

    public void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        var isAceita = mediaTypesAceitas.stream().anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));

        if (!isAceita) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

}
