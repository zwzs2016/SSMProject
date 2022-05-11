package com.uwan.SSM.Demo.AspectConfig;

import com.uwan.SSM.Demo.AppBeans.Person;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class WebAspectsSatement {
//    @After("execution(* com.uwan.SSM.*.*(..))")
//    public void daAccessCheck(){
//        System.out.println("Aspect成功");
//    }
    @Before("within(com.uwan.SSM.Demo.AppBeans.Person)")
    public void beforesug(){
        System.out.println("Person执行之前");
    }
    @Before("this(person)")
    public void bind(Person person){
        System.out.println("--------绑定代理对象【开始】--------");
        System.out.println(person.getClass().getName());
        System.out.println("--------绑定代理对象【结束】--------");
    }
//    @Around("within(com.uwan.SSM.Demo.AppBeans.Person)")
//    public void dosome(){
//        System.out.println("---------获取连接点对象【开始】---------");
//    }
}
