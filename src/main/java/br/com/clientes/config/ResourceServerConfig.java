package br.com.clientes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/* Configurações de acesso a requisições da api */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
   
   /* essa configuração poderia ser colocada no SecurityConfig.condigure()
   está sendo separado para ficar mais organizado, aqui vai ser a configuração 
   de acesso a api */
   @Override
   public void configure(HttpSecurity http) throws Exception {

      http.authorizeRequests()
         .antMatchers("/api/usuarios", "/h2-console/**").permitAll() // permite todos a cadastrar usuario
         .antMatchers("/api/clientes/**", 
            "/api/servicos-prestados/**").authenticated() // permite somente que esta atutenticado
         .anyRequest().denyAll(); // para qualquer outra requisição negar todas
   }
}
