package br.com.clientes.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.clientes.model.entity.Cliente;
import br.com.clientes.model.entity.ServicoPrestado;
import br.com.clientes.model.repository.ClienteRepository;
import br.com.clientes.model.repository.ServicoPrestadoRepository;
import br.com.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

   private final ClienteRepository clienteRepository;
   private final ServicoPrestadoRepository repository;
   private final BigDecimalConverter bigConverter;
   
   // quando um objeto é usado no RequestBody é preciso que ele tenha um construtor sem parametros 
   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {

      Cliente cliente = clienteRepository
         .findById(dto.getIdCliente())
         .orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.BAD_REQUEST,
               "Cliente Inexistente"));
               
      var servicoPrestado = new ServicoPrestado();
      servicoPrestado.setDescricao(dto.getDescricao());
      servicoPrestado.setData(
         LocalDate.parse(dto.getData(), 
            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      servicoPrestado.setCliente(cliente);
      servicoPrestado.setValor(bigConverter.converter(dto.getPreco()));
      
      return repository.save(servicoPrestado);
   }

   /* desta vez os parametros vão ser passados via url, por isso usamos
      @RequestParam, neste caso os parametros não são obrigatorios de 
      acordo com o atributo required com o valor false
    */
   @GetMapping
   public List<ServicoPrestado> pesquisar(
         @RequestParam(value = "nome", required = false, 
            defaultValue = "") String nome,
         @RequestParam(value = "mes", required = false) Integer mes
   ) {
      
      return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
   }

}
