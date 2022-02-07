package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.exception.entitynotfound.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.transaction.Transactional;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private SmartValidator validator;
	
	private static final String MSG_ESTADO_EM_USO = "Estado de id %d não pode ser removido, pois está em uso!";
	
	public Estado buscar(long id) {
		return estadoRepository.findById(id)
		.orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Transactional
	public Estado salvar(Estado estado) {
		validate(estado, "estado");

		estado.setSigla(estado.getSigla().toUpperCase());
		return estadoRepository.save(estado);
	}

	@Transactional
	public void remover(long id) {
		try {
			estadoRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));

		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(id);
		}
	}

	private void validate(Estado estado, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(estado, objectName);
		validator.validate(estado, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}
}
