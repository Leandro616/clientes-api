package br.com.clientes.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {
   
   @Bean
   public FilterRegistrationBean<CorsFilter> corsRegistrationBean() {
      List<String> all = Arrays.asList("*");

      // configuração cors, neste caso permitindo todos os acessos
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(all);
      configuration.setAllowedHeaders(all);
      configuration.setAllowedMethods(all);
      configuration.setAllowCredentials(true);

      // configurando todos as urls de nossa api para receber a cofiguração cors
      UrlBasedCorsConfigurationSource source = 
            new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      

      CorsFilter corsFilter = new CorsFilter(source);
      FilterRegistrationBean<CorsFilter> filter = 
            new FilterRegistrationBean<>(corsFilter);
      // configurando o filtro como principal
      filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
      
      return filter;
   }
}
