package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly=true) //abro uma transacao que nao altera nada e assim garanto o entity manager aberto para buscar grupos e permissoes.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Usuário não encontrado com o email informado."));

        return new AuthUser(usuario, getAuthorities(usuario));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        Collection<SimpleGrantedAuthority> permissoes = usuario.getGrupos()
                .stream()
                .flatMap(grupo -> grupo
                        .getPermissoes()
                        .stream())
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome()))
                .collect(Collectors.toList());

        return permissoes;
    }
}
