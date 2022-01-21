package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	private static final String MSG_ESTADO_EM_USO = "Estado de id %d não pode ser removido, pois está em uso!";
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe estado cadastrado para o id %s.";
	
	
	public Estado buscar(long id) {
		return estadoRepository.findById(id)
		.orElseThrow(() -> new EntidadeNaoEncontradaException
				(String.format(MSG_ESTADO_NAO_ENCONTRADO, id)));
	}

	public Estado salvar(Estado estado) {
		estado.setSigla(estado.getSigla().toUpperCase());
		return estadoRepository.save(estado);
	}

	public void remover(long id) {
		try {
			estadoRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
		}
	}
}
