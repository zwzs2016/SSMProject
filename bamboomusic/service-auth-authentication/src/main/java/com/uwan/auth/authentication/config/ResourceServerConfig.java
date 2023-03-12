package com.uwan.auth.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    public static final String RESOURCE_ID="WEB_RES";

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resource){
        resource.resourceId(RESOURCE_ID)
                .tokenStore(tokenStore())
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()//授权的请求
                .anyRequest()//任何请求
                .authenticated()//需要身份认证
                .and().csrf().disable();
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices services=new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:3000/oauth/check_token");
        services.setClientId("tao");
        services.setClientSecret("secret");
        return services;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }
}
