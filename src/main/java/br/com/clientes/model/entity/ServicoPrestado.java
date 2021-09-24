package br.com.clientes.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class ServicoPrestado {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idservico")
   private Integer id;

   @Column(nullable = false, length = 150)
   private String descricao;

   // @ManyToOne relacionamento muitos para um: muitos serviços para um cliente
   // @JoinColumn 
   @ManyToOne
   @JoinColumn(name = "id_cliente")
   private Cliente cliente;

   @Column
   private BigDecimal valor;

   @Column
   @JsonFormat(pattern = "dd/MM/yyyy")
   private LocalDate data;

}
