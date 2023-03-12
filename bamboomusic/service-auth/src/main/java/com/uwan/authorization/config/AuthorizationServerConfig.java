package com.uwan.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    @Qualifier("dataSource")
//    DataSource dataSource;

//    @Autowired
//    @Qualifier("userDetailsService")
//    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

//    jwt对称加密

    @Override
    public void configure(ClientDetailsServiceConfigurer clinets) throws Exception{
        //基于内存测试
        clinets.inMemory()
                .withClient("tao")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
                .scopes("all","ROLE_ADMIN","ROLE_USER")
                .redirectUris("http://baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints.authenticationManager(authenticationManager);
    }

    /**
     * @Description: 用来配置令牌端点(Token Endpoint)的安全约束
     * Token令牌默认是有签名的，并且资源服务需要验证这个签名，
     * 因此呢，你需要使用一个对称的Key值，用来参与签名计算，
     * 这个Key值存在于授权服务以及资源服务之中。
     * 或者你可以使用非对称加密算法来对Token进行签名，
     * Public Key公布在/oauth/token_key这个URL连接中，
     * 默认的访问安全规则是"denyAll()"，
     * 即在默认的情况下它是关闭的，
     * 你可以注入一个标准的 SpEL 表达式到 AuthorizationServerSecurityConfigurer 这个配置中来将它开启
     * （例如使用"permitAll()"来开启可能比较合适，因为它是一个公共密钥）。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")//开放获取tokenkey(这个是做JWT的时候开放的！也就是资源服可以通过这个请求得到JWT加密的key，目前这里没什么用，可以不用配置)
                .checkTokenAccess("permitAll()")//开放远程token检查   /oauth/check_token  body  token
                .allowFormAuthenticationForClients();//允许client使用form的方式进行authentication的授权

    }

    /**
     * token的持久化
     *
     * @return JwtTokenStore
     */
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
