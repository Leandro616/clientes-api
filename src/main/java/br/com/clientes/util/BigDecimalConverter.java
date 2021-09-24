package br.com.clientes.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalConverter {
   
   public BigDecimal converter(String valor) {
      if (valor == null)
         return null;

      // 1.000,00 --> 1000.00
      valor = valor.replace(".", "").replace(",", ".");
      return new BigDecimal(valor);
   }
}
