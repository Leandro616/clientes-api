package br.com.clientes.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Usuario {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idusuario")
   private Integer id;

   @Column(name = "login", unique = true)
   @NotBlank(message = "{campo.login.obrigatorio}")
   private String username;

   @Column(name = "senha")
   @NotBlank(message = "{campo.senha.obrigatorio}")
   private String password;
}
