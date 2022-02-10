package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeReferenciadaInexistenteException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.exception.entitynotfound.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Map;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private SmartValidator validator;

    private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de id %d não pode ser removido, pois está em uso!";

    public Restaurante buscar(long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cozinhaService.buscar(cozinhaId);
        restaurante.setCozinha(cozinha);

        if(ObjectUtils.isNotEmpty(restaurante.getEndereco())
            && ObjectUtils.isNotEmpty(restaurante.getEndereco().getCidade())) {
            var cidadeId = restaurante.getEndereco().getCidade().getId();
            var cidade = cidadeService.buscar(cidadeId);
            restaurante.getEndereco().setCidade(cidade);
        }

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void remover(long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_RESTAURANTE_EM_USO, id));

        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);
        }
    }

    @Transactional
    public Restaurante atualizarParcial(long id, Map<String, Object> campos, HttpServletRequest request) {
        var restaurante = buscar(id);

        merge(campos, restaurante, request);
        validate(restaurante, "restaurante");
        return salvar(restaurante);
    }

    private void validate(Restaurante restaurante, String objectName) {
        //object name é o nome que vai aparecer na validacao
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }


    private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest request) {

        var serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            var objMapper = new ObjectMapper();
            objMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            var restauranteOrigem = objMapper.convertValue(campos, Restaurante.class);

            campos.forEach((prop, value) -> {
                var field = ReflectionUtils.findField(Restaurante.class, prop);
                field.setAccessible(true);

                var valorConvertido = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, valorConvertido); //não posso usar o value aqui, porque não está convertido e gerará erro
            });
        } catch (IllegalArgumentException ex) {
            var rootCause = ExceptionUtils.getRootCause(ex);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest); //cairá no exception handler
        }
    }

    @Transactional
    public void ativar(Long id) {
        var restaurante = buscar(id);
        restaurante.ativar();
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void desativar(Long id) {
        var restaurante = buscar(id);
        restaurante.desativar();
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void removerFormaPagamento(Long idRestaurante, Long idFormaPagamento){
            var restaurante = buscar(idRestaurante);
            var formaPagamento = formaPagamentoService.buscar(idFormaPagamento);

            if(!restaurante.getFormasPagamento().contains(formaPagamento))
                throw new NegocioException("Restaurante não possui a forma de pagamento solicitada para remoção.");

            restaurante.getFormasPagamento().remove(formaPagamento);
    }

    @Transactional
    public void adicionarFormaPagamento(Long idRestaurante, Long idFormaPagamento){
        var restaurante = buscar(idRestaurante);
        var formaPagamento = formaPagamentoService.buscar(idFormaPagamento);

        if(restaurante.getFormasPagamento().contains(formaPagamento))
            throw new NegocioException("Restaurante já possui a forma de pagamento solicitada para adição.");

        restaurante.getFormasPagamento().add(formaPagamento);
    }

}
