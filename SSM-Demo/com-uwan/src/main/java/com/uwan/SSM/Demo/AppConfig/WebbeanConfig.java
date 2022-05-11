package com.uwan.SSM.Demo.AppConfig;

import com.uwan.SSM.Demo.AppBeans.JsonUtils;
import com.uwan.SSM.Demo.AppBeans.Person;
import com.uwan.SSM.Demo.AppBeans.Toyota;
import com.uwan.SSM.Demo.AspectConfig.WebAspectsSatement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration

@ComponentScan(basePackages = "com.uwan.SSM.Demo.AppConfig")
public class WebbeanConfig {
    @Bean
    public Toyota Onecar() {
        return new Toyota();
    }

    @Bean
    public Person onePerson(Toyota toyota) {
        return new Person(toyota, toyota);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setBasename("application");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(2);
        return messageSource;
    }

    @Bean
    public WebAspectsSatement webAspectsSatement() {
        return new WebAspectsSatement();
    }

    @Bean
    public JsonUtils jsonUtils(){
        return new JsonUtils();
    }
}
