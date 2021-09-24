package br.com.clientes.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clientes.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
   // O proprio Spring data implemeta esse metodo automaticamente, são chamados queryMethods
   Optional<Usuario> findByUsername(String username);

   boolean existsByUsername(String username);
}
