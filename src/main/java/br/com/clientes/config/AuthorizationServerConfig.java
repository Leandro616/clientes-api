package br.com.clientes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/* Essas configurações são para o acesso da aplicação Angular
   Essa classe é responsavel para gerar os Tokens */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig 
      extends AuthorizationServerConfigurerAdapter {
   
   @Autowired
   private AuthenticationManager authenticationManager;

   // injetando o valor da chave que veio do arquivo properties
   @Value("${security.jwt.signing-key}")
   private String signingKey;

   @Bean 
   public TokenStore tokenStore() {
      /* retornando um token em memoria
      return new InMemoryTokenStore(); */

      // usando o jwt para construir o token
      return new JwtTokenStore(accessTokenConverter());
   }

   @Bean 
   public JwtAccessTokenConverter accessTokenConverter() {
      JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
      // passando a chave criada
      tokenConverter.setSigningKey(signingKey);
      return tokenConverter;
   }

   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints) 
         throws Exception {
      endpoints.tokenStore(tokenStore())
         .accessTokenConverter(accessTokenConverter())
         .authenticationManager(authenticationManager);
   }

   @Override
   public void configure(ClientDetailsServiceConfigurer clients) 
         throws Exception {
      
      clients.inMemory()
         .withClient("my-angular-app") // ClienteID 
         .secret("@321") // client-secret 
         .scopes("read", "write") 
         .authorizedGrantTypes("password")
         .accessTokenValiditySeconds(1800); // duração do token
   }
}
