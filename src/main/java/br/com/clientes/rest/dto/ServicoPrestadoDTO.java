package br.com.clientes.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// classe criada para receber alguns parametros diferentes da classe ServicoPrestado
public class ServicoPrestadoDTO {

   @NotEmpty(message = "{campo.descricao.obrigatorio}")
   private String descricao;

   @NotEmpty(message = "{campo.preco.obrigatorio}")
   private String preco;

   @NotEmpty(message = "{campo.data.obrigatorio}")
   private String data;

   // a classe ServicoPrestado tem um atributo Cliente, mas eu só quero receber o Id
   @NotNull(message = "{campo.cliente.obrigatorio}")
   private Integer idCliente;
}
