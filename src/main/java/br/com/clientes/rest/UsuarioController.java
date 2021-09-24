package br.com.clientes.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.clientes.exception.UsuarioCadastradoException;
import br.com.clientes.model.entity.Usuario;
import br.com.clientes.model.repository.UsuarioRepository;
import br.com.clientes.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor // cria um construtor com os argumentos final 
public class UsuarioController {
   
   private final UsuarioService service;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public void salvar(@RequestBody @Valid Usuario usuario) {
      try {
         service.salvar(usuario);

      } catch (UsuarioCadastradoException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      }
   }
}
