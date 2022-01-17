package com.algaworks.algafood.domain;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		var cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha com id %d!", cozinhaId)));

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public void remover(long id) {
		try {
			restauranteRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Restaurante de id %d não pode ser removida, pois está em uso!", id));

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante com id %d!", id));
		}
	}

	public Restaurante atualizarParcial(long id, Map<String, Object> campos) {
		var restaurante = restauranteRepository.findById(id).orElseThrow(() -> 
			new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante com id %d!", id)));

		merge(campos, restaurante);
		return salvar(restaurante);
	}
	

	private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
		 var objMapper = new ObjectMapper();
		 var restauranteOrigem = objMapper.convertValue(campos, Restaurante.class);
		 
		 campos.forEach((prop, value) -> {
			var field = ReflectionUtils.findField(Restaurante.class, prop);
			field.setAccessible(true);
			
			var valorConvertido = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, valorConvertido); //não posso usar o value aqui, porque não está convertido e gerará erro
		 });
	}

}
