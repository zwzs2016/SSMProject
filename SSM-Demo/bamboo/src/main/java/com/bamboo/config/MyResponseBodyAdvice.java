package com.bamboo.config;

import com.bamboo.entity.out.ResponseEntityResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ControllerAdvice
@ResponseBody
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body !=null&& body instanceof String==false) {
            ResponseEntityResult responseEntity = new ResponseEntityResult();
            responseEntity.setData(body);
            //为了测试这里直接用ture了，实际中根据业务方法返回进行设置
            responseEntity.setSuccess(true);
            responseEntity.setCode(String.valueOf(HttpStatus.OK.value()));
            responseEntity.setMessage(HttpStatus.OK.getReasonPhrase());
            //若是没有配置configureMessageConverters，本方法必须返回字符串，否则报类型转换异常（不能转为字符串）
            //return JSON.toJSONString(responseEntity);
            //若是配置了自定义类型转换器configureMessageConverters，本方法可以返回任意类型
            //也可以在ResponseBodyAdvice接口加泛型
            return responseEntity;
        }
        return body;
    }
}
