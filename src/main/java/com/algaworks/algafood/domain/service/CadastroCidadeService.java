package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de id %d não pode ser removida, pois está em uso!";
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cidade cadastrada para o id %s.";
	
	
	public Cidade buscar(long id) {
		return cidadeRepository.findById(id)
		.orElseThrow(() -> new EntidadeNaoEncontradaException
				(String.format(MSG_CIDADE_NAO_ENCONTRADA, id)));
	}

	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
		var estado = cadastroEstadoService.buscar(estadoId);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	public void remover(long id) {
		try {
			cidadeRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, id));

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
		}
	}
}
