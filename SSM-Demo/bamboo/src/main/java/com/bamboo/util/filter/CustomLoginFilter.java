package com.bamboo.util.filter;

import com.bamboo.service.own.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    //private static final String defaultFilterProcessesUrl = "/login";

    @Autowired
    SysLoginService sysLoginService;

    public CustomLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String method = request.getMethod();

        // 仅使用POST方法提交
        if(!"POST".equalsIgnoreCase(method)){
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 从Session中取出验证码
        String verify = (String) request.getSession().getAttribute("verify");
        // 从请求中获取验证码
        String code = request.getParameter("code");
        String uuid = request.getParameter("uuid");
        // 检查验证码 aj
//        checkCode(code,verify);



        // 从请求中获取 用户名及命名
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        username = username==null?"":username.trim();
        password = password==null?"":password.trim();

        sysLoginService.login(username, password, code, uuid);

        // 生成 username+password 形式的 token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 设置token详细信息
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));

        // 交给内部 AuthenticationManager 去认证，并返回 Authentication
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    private void checkCode(String code,String verify){
        if(code==null || verify==null || "".equals(code) || verify.equalsIgnoreCase(code)){
            throw new AuthenticationServiceException("验证码错误："+code);
        }
    }
}


