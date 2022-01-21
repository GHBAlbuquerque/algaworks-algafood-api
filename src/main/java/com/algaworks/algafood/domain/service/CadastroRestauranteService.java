package com.algaworks.algafood.domain.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de id %d não pode ser removido, pois está em uso!";
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe restaurante cadastrado para o id %s.";
	
	
	public Restaurante buscar(long id) {
		return restauranteRepository.findById(id)
		.orElseThrow(() -> new EntidadeNaoEncontradaException
				(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
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
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id));
		}
	}

	public Restaurante atualizarParcial(long id, Map<String, Object> campos) {
		var restaurante = buscar(id);

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
