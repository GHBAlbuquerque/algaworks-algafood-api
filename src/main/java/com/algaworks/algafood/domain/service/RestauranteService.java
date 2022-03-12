package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.exception.entitynotfound.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.entitynotfound.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.entitynotfound.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
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
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoRepository produtoRepository;

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

        if (ObjectUtils.isNotEmpty(restaurante.getEndereco())
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

    // ativação/abertura de restaurantes

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
    //com anotação do transactional, a operação EM massa fica dentro da transação e só é commitada se tudo funcionar
    public void ativar(List<Long> restaurantesIds) {
        try {
            restaurantesIds.forEach(this::ativar);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Transactional
    public void desativar(List<Long> restaurantesIds) {
        try {
            restaurantesIds.forEach(this::desativar);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @Transactional
    public void abrir(long id) {
        var restaurante = buscar(id);
        restaurante.abrir();
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void fechar(long id) {
        var restaurante = buscar(id);
        restaurante.fechar();
        restauranteRepository.save(restaurante);
    }

    // serviços referentes à forma de pagamento

    @Transactional
    public void removerFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
        var restaurante = buscar(idRestaurante);
        var formaPagamento = formaPagamentoService.buscar(idFormaPagamento);

        if (!restaurante.getFormasPagamento().contains(formaPagamento))
            throw new NegocioException("Restaurante não possui a forma de pagamento solicitada para remoção.");

        restaurante.getFormasPagamento().remove(formaPagamento);
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void adicionarFormaPagamento(Long idRestaurante, Long idFormaPagamento) {
        var restaurante = buscar(idRestaurante);
        var formaPagamento = formaPagamentoService.buscar(idFormaPagamento);

        if (restaurante.getFormasPagamento().contains(formaPagamento))
            throw new NegocioException("Restaurante já possui a forma de pagamento solicitada para adição.");

        restaurante.getFormasPagamento().add(formaPagamento);
        restauranteRepository.save(restaurante);
    }

    // serviços referentes a produtos

    public List<Produto> listarProdutosPorRestaurante(Long idRestaurante, boolean incluirInativos) {
        var restaurante = buscar(idRestaurante);

        if (incluirInativos) {
            return produtoRepository.getByRestaurante(restaurante);
        } else {
            return produtoRepository.findAtivosByRestaurante(restaurante);
        }
    }

    public Produto buscarProdutoPorRestaurante(Long idRestaurante, Long idProduto) {
        var restaurante = buscar(idRestaurante);
        var produto = produtoRepository.getByIdAndRestaurante(idProduto, restaurante);


        if (produto.isEmpty()) {
            throw new ProdutoNaoEncontradoException(idProduto, idRestaurante);
        }

        return produto.get();
    }

    @Transactional
    public Produto adicionarProduto(Long idRestaurante, Produto produto) {
        var restaurante = buscar(idRestaurante);
        produto.setRestaurante(restaurante);
        produtoRepository.save(produto);
        return produto;
    }

    @Transactional
    public void atualizarProduto(Long idRestaurante, Produto produto) {
        produtoRepository.save(produto);
    }

    @Transactional
    public void removerProduto(Long idRestaurante, Long idProduto) {
        var restaurante = buscar(idRestaurante);
        var produto = buscarProdutoPorRestaurante(idRestaurante, idProduto);

        produtoRepository.deleteByIdAndRestaurante(idProduto, restaurante);
    }

    // ativação/desativação de produtos

    @Transactional
    public void ativar(Long idProduto, Long idRestaurante) {
        var restaurante = buscar(idRestaurante);
        var optional = produtoRepository.getByIdAndRestaurante(idProduto, restaurante);

        if (optional.isPresent()) {
            var produto = optional.get();
            produto.ativar();
            produtoRepository.save(produto);
        }
    }

    @Transactional
    public void desativar(Long idProduto, Long idRestaurante) {
        var restaurante = buscar(idRestaurante);
        var optional = produtoRepository.getByIdAndRestaurante(idProduto, restaurante);

        if (optional.isPresent()) {
            var produto = optional.get();
            produto.desativar();
            produtoRepository.save(produto);
        }
    }

    // serviços referentes a usuários

    @Transactional
    public void removerResponsavel(Long idRestaurante, Long idUsuario) {
        var restaurante = buscar(idRestaurante);
        var usuario = usuarioService.buscar(idUsuario);

        if (!restaurante.getResponsaveis().contains(usuario))
            throw new NegocioException("Restaurante não possui o usuário solicitado para remoção.");

        restaurante.getResponsaveis().remove(usuario);
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void adicionarResponsavel(Long idRestaurante, Long idUsuario) {
        var restaurante = buscar(idRestaurante);
        var usuario = usuarioService.buscar(idUsuario);

        if (restaurante.getResponsaveis().contains(usuario))
            throw new NegocioException("Restaurante já possui o usuário solicitado para adição.");

        restaurante.getResponsaveis().add(usuario);
        restauranteRepository.save(restaurante);
    }
}
