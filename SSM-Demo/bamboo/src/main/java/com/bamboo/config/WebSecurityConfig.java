package com.bamboo.config;

import com.bamboo.service.UserService;
import com.bamboo.util.filter.CustomLoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //注入用户服务
    @Autowired
    UserService userService;
    //配置用户登录密码需要BCryptPasswordEncoder密文认证
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //基于数据库的用户账号密码、角色、过期、锁定等认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService);
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                //对可访问URL资源进行角色控制
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/db/**")
                .access("hasRole('DBA') and hasRole('ADMIN')")
                //用户注册接口和执行用户注册接口允许访问
                .antMatchers("/user/**","/register.html","/live/**","/tologin","/images/**","/css/**","/js/**")
                .permitAll()
                .antMatchers("/captcha/**","/tologin").anonymous()
                //用户访问其他URL资源都必须认证后访问，即登陆后访问
                .anyRequest()
                .authenticated()
                //开启表单登录，即登录界面，登录URL为/login，登录参数用户名username密码password
                //Ajax或移动端通过POST请求登录，接口为/login，permitAll表示登录不需要认证即可访问
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/tologin")
                .permitAll()
                //成功登录后跳转到hello页面
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
                        response.setContentType("application/json;charset=utf-8");
                        response.sendRedirect("/bamboo.html");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        Map<String, Object> result = new HashMap<String, Object>();
                        result.put("msg", "登录失败: "+exception.getMessage());
                        result.put("status", 500);
                        response.setContentType("application/json;charset=UTF-8");
                        String s = new ObjectMapper().writeValueAsString(result);
                        response.getWriter().println(s);
                    }
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .csrf()
                .disable();
        // 将自定义的过滤器添加进去
        httpSecurity.addFilterAt(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CustomLoginFilter customLoginFilter() throws Exception {
        CustomLoginFilter customLoginFilter = new CustomLoginFilter("/tologin");
        customLoginFilter.setAuthenticationManager(authenticationManagerBean());
        customLoginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
                response.setContentType("application/json;charset=utf-8");
                response.sendRedirect("/bamboo.html");
            }
        });
        customLoginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("msg", "登录失败: "+exception.getMessage());
                result.put("status", 500);
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                String s = new ObjectMapper().writeValueAsString(result);
                response.getWriter().println(s);
            }
        });
        return customLoginFilter;
    }
}
