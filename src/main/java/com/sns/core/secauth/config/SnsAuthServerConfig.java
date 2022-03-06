package com.sns.core.secauth.config;

import com.sns.core.secauth.service.CustomClientDetails;
import com.sns.core.secauth.service.CustomClientsDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.List;
import java.util.Map;

//@Configuration
//@EnableAuthorizationServer
public class SnsAuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("$jwt.key")
    private String jwtKey;

    /**
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        //endpoints.authenticationManager(authenticationManager)
               // .tokenStore(tokenStore())
               // .accessTokenConverter(jwtAccessTokenConverter());
    }
    /**
     * Overrides the configure() method to set
     * up the ClientDetailsService instance
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .accessTokenValiditySeconds(1000)
                .additionalInformation("hello world")
                .scopes("read", "write")
                .resourceIds("api")
                .authorizedGrantTypes("password","authorization_code","client_credentials")
                .redirectUris("http://localhost:9080/home");
    }

    /**
     * creates a token store with an access token converter associated to it
     * @return
     */
   // @Bean
   // public TokenStore tokenStore() {
       // return new JwtTokenStore(jwtAccessTokenConverter());
   // }

    /**
     * configure how the authorizatioon server validates tokens
     * in this case we are using symmetric key
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtKey);
        return converter;
    }


}
