package br.com.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientesApplication {
   public static void main(String[] args) {
      SpringApplication.run(ClientesApplication.class, args);
   }

   /* Quando um comandLineRunner é anotado com @Bean ele é executado 
         assim que a aplicação iniciar.
      @Autowired está injetando uma instancia de ClienteRepository

   @Bean
   public CommandLineRunner run(@Autowired ClienteRepository repository) {
      return args -> {
         Cliente cliente = Cliente.builder()
            .cpf("11111111111")
            .nome("fulano")
            .build();

         // A classe ClienteRepository já tem os metodos prontos herdados de JpaRepository
         repository.save(cliente);
      };
   }
   */
}
