package com.algaworks.algafood.domain.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.entitynotfound.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de id %d não pode ser removido, pois está em uso!";
	
	public Restaurante buscar(long id) {
		return restauranteRepository.findById(id)
		.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		var cozinha = cadastroCozinhaService.buscar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public void remover(long id) {
		try {
			restauranteRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_RESTAURANTE_EM_USO, id));

		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);
		}
	}

	public Restaurante atualizarParcial(long id, Map<String, Object> campos, HttpServletRequest request) {
		var restaurante = buscar(id);

		merge(campos, restaurante, request);
		return salvar(restaurante);
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

}
