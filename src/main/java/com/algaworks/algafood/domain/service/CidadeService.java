package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.exception.entitynotfound.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.transaction.Transactional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private SmartValidator validator;

    private static final String MSG_CIDADE_EM_USO = "Cidade de id %d não pode ser removida, pois está em uso!";

    public Cidade buscar(long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        validate(cidade, "cidade");
        Long estadoId = cidade.getEstado().getId();
        var estado = estadoService.buscar(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void remover(long id) {
        try {
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        }
    }

    private void validate(Cidade cidade, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(cidade, objectName);
        validator.validate(cidade, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }


}
