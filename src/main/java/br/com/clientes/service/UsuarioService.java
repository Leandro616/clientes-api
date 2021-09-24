package br.com.clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.clientes.exception.UsuarioCadastradoException;
import br.com.clientes.model.entity.Usuario;
import br.com.clientes.model.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

   @Autowired
   private UsuarioRepository repository;

   // esse metodo vai carregar o usuario do banco de dados e vai ser usado para ser autenticado
   @Override
   public UserDetails loadUserByUsername(String username) 
         throws UsernameNotFoundException {
      
      Usuario usuario = repository
         .findByUsername(username)
         .orElseThrow(() -> 
            new UsernameNotFoundException("Usuário não encontrado!"));

      // a classe User tem um builder que constroi um UserDatails
      return User.builder()
         .username(usuario.getUsername())
         .password(usuario.getPassword())
         .roles("USER")
         .build();
   }

   public Usuario salvar(Usuario usuario) {
      boolean usuarioExiste = repository
         .existsByUsername(usuario.getUsername());

      if (usuarioExiste) {
         throw new UsuarioCadastradoException(usuario.getUsername());
      }
      
      return repository.save(usuario);
   }
}
