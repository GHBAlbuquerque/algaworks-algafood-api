package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurantes/{idRestaurante}/produtos/{idProduto}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long idRestaurante, @PathVariable Long idProduto,
                              @Valid FotoProdutoInputDTO fotoProdutoInputDTO) {

        var arquivo = fotoProdutoInputDTO.getArquivo();
        var nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();

        var pathArquivo = Path.of("/Users/Giovanna/Documents/AlgaWorks/Especialista_Spring_REST/files", nomeArquivo);

        try {
            arquivo.transferTo(pathArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
