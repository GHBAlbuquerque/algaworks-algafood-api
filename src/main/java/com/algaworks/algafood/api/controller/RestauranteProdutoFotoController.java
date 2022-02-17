package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoAssembler;
import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.api.model.output.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping
    public FotoProdutoDTO buscar(@PathVariable Long idRestaurante, @PathVariable Long idProduto) {
        var foto = catalogoFotoProdutoService.buscar(idRestaurante, idProduto);
        return fotoProdutoAssembler.convertToModel(foto);
    }

    @GetMapping(produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<InputStream> recuperar(@RequestParam(required = true) String nome){
        var inputStream = catalogoFotoProdutoService.recuperar(nome);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).headers(headers).body(inputStream);
    }
}
