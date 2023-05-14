package com.bamboo.payment.config;

import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SeataFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest ;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String xid = request.getHeader(RootContext.KEY_XID) ;
        if(StringUtils.isNoneBlank(xid)){
            /*将这个xid绑定到，seata上下文中。当新的事务使用当前这个xid*/
            RootContext.bind(xid);
        }
        try {
            filterChain.doFilter(request , response);
        }finally {
            RootContext.unbind();
        }
    }
}
