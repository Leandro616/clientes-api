package br.com.clientes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.clientes.service.UsuarioService;

/* Configurações de acesso do usuário */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private UsuarioService usuarioService;

   @Bean
   public AuthenticationManager authenticationManager() throws Exception {
      return super.authenticationManager();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return NoOpPasswordEncoder.getInstance();
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) 
         throws Exception {
      // configuração em memória 
      /* auth.inMemoryAuthentication()
         .withUser("fulano")
         .password("123")
         .roles("USER"); */

      auth
         .userDetailsService(usuarioService)
         .passwordEncoder(passwordEncoder());
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      // geralmente em apis, onde a aplicação springboot é 
      // separada da aplicação web, nós desabilitamos o csrf
      http.csrf().disable()
         .cors()
         .and()
         // esses metodos desabilitam a criação de seções 
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
   }
}
