package br.com.clientes.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* para fazer o maven baixar a documentação das dependencias:
      mvn dependency:sources
      mvn dependency:resolve -Dclassifier=javadoc

lobomk gera codigo em tempo de compilação
      @Data gera construtores, getters, setters e outros
      @Getter e @Setter gera geters e seters
      @NoArgsConstructor gera um construtor sem argumentos
      @AllArgsConstructor gera um construtor com todos argumentos
      @Builder gera um builder  

   @PrePersist vai executar antes que toda vez que essa entidade for persistir no banco
   Bean validation: @NotNull e NotEmpty
   Hibernate já tem validação de CPF brasileiro @CPF
*/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idcliente")
   private Integer id;

   // not null e tamanho
   @Column(nullable = false, length = 150)
   @NotEmpty(message = "{campo.nome.obrigatorio}")
   private String nome;

   @Column(nullable = false, length = 11)
   @NotNull(message = "{campo.cpf.obrigatorio}")
   @CPF(message = "{campo.cpf.invalido}")
   private String cpf;

   // não modificavel
   @Column(name = "data_cadastro", updatable = false)
   @JsonFormat(pattern = "dd/MM/yyyy")
   private LocalDate dataCadastro;

   @PrePersist
   public void prePersist() {
      setDataCadastro(LocalDate.now());
   }
}
