package br.com.clientes.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.clientes.model.entity.Cliente;
import br.com.clientes.model.repository.ClienteRepository;

/* @RestController anota nossa classe como um controlador e tambem
   vem com a anotação @ResponseBody (Anotação que indica que um 
   valor de retorno do método deve ser vinculado ao corpo da resposta da web. ).

   @PostMapping faz o mapeamento para o metodo POST do form, quando for feito 
   um Post no ClienteSide vai ser chamado o método com essa anotação

   @ResponseStatus é o status que vai restornar para o cliente na execução do 
   método, o padrão é HttpStatus.OK (não precisa configurar)

   @RequestBody indica que o objeto vai vim do corpo da requisição, e o spring
   converte para o objeto anotado

   @GetMapping faz o mapeamento para o metodo GET, usei as chaves para indicar que
   é uma variavel porque vai ser o id a ser pesquisado

   @PathVariable retorna o valor da variavel do mapeamento, se a variavel tiver um 
   nome diferente basta usar @PathVariable("nome")

   findById() retorna um Optional, ou seja, pode está vazio ou não

   metodo deletar retorna uma response vazia de acordo com o padrão restfull

   o método save de jpaRepository cria um registro se não tiver registro ou atualiza 
   se já existir

   @Valid vai validar de acordo com os beans validations que a gente configurou

   @CrossOrigin serve para configurar que pode acessar a api, isso é uma proteção
   do browser (CORS), para usar no postman não precisou desta configuração. Nesse caso
   passamos o dominio da aplicação do angular, o dominio não inclui a / no final
*/
@RestController
@RequestMapping("/api/clientes")
//@CrossOrigin("http://localhost:4200") // configuramos com a classe WebConfig
public class ClienteController {

   private ClienteRepository repository;

   @Autowired
   public ClienteController(ClienteRepository repository) {
      this.repository = repository;
   }


   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente salvar(@RequestBody @Valid Cliente cliente) {
      return repository.save(cliente);
   }

   @GetMapping
   public List<Cliente> buscarTodos() {
      return repository.findAll();
   }

   @GetMapping("{id}")
   public Cliente buscarPorId(@PathVariable Integer id) {
      return repository
         .findById(id)
         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cliente não encontrado"));
   }

   @DeleteMapping("{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deletar(@PathVariable Integer id) {
      repository
         .findById(id)
         .map(cliente -> {
            repository.delete(cliente);
            return Void.TYPE;
         })
         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cliente não encontrado"));
   }

   @PutMapping("{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void atualizar(@PathVariable Integer id,
         @RequestBody @Valid Cliente clienteAtualizado) {

      repository
         .findById(id)
         .map(cliente -> {
            clienteAtualizado.setId(cliente.getId());
            repository.save(clienteAtualizado);
            return Void.TYPE;
         })
         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cliente não encontrado"));
   }

}
