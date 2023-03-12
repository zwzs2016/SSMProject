package com.uwan.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123456")).roles("USER","ADMIN").authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("p1,p2"));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager()throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers("/test/*")
                .hasAnyAuthority("p1")
                .antMatchers("/test1/*")
                .permitAll()
                .antMatchers("/test2/*")
                .denyAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin();
    }


}
