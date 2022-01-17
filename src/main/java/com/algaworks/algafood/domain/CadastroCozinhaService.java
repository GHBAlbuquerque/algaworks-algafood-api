package com.algaworks.algafood.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		// qualquer regra de negócio virá aqui
		return cozinhaRepository.save(cozinha);
	}

	public void remover(long id) {
		try {
			cozinhaRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			// tradução da exceção
			throw new EntidadeEmUsoException(
					String.format("Cozinha de id %d não pode ser removida, pois está em uso!", id));

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com id %d!", id));
		}
	}

}
