package br.com.clientes.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import br.com.clientes.rest.exception.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
   
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ApiErrors handleValidationErros(MethodArgumentNotValidException e) {

      BindingResult bResult = e.getBindingResult();
      List<String> messages = bResult.getAllErrors()
         .stream()
         .map(objectError -> objectError.getDefaultMessage())
         .collect(Collectors.toList());

      return new ApiErrors(messages);
   }

   // o ResponseStatusException poderá vir com qualquer Status entao retornamos 
   // um ResponseEntity que o metodo se tornará mais dinamico, e não precisamos 
   // usar a anotação ResponseStatus já que o Status já está definido
   @ExceptionHandler(ResponseStatusException.class)
   public ResponseEntity<ApiErrors> 
         handleResponseStatusException(ResponseStatusException e) {
            
      // usando o e.getReason para pegar só a mensagem sem o HttpStatus
      String mensagemErro = e.getReason();
      HttpStatus codigoStatus = e.getStatus();

      ApiErrors apiErrors = new ApiErrors(mensagemErro);

      return new ResponseEntity<ApiErrors>(apiErrors, codigoStatus);
   }
}
