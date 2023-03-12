package com.uwan.SSM;

import com.uwan.SSM.Demo.AppBeans.Person;
import com.uwan.SSM.Demo.AppBeans.SaveData;
import com.uwan.SSM.Demo.AppConfig.WebbeanConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.uwan.SSM.ChatRoom.dao")
@RestController
public class SsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsmApplication.class, args);
	}

	@GetMapping("/Hello")
	public String Hello(@RequestParam(value = "name",defaultValue = "World") String name){

		return String.format("Hello %s!",name);
	}
	@GetMapping("/lookbeans")
	public String lookbeans(){
		//beans容器
		ApplicationContext ctx=new AnnotationConfigApplicationContext(WebbeanConfig.class);
		Person person=ctx.getBean(Person.class);
		person.drive();
		System.out.println("执行完成");
		return "beans";
	}
	@PostMapping("/Useradd")
	public String Useradd(@RequestParam(value = "id") Long id,@RequestParam(value = "name") String name,@RequestParam(value = "address") String address) throws IOException {
		System.out.println(id+" "+name+" "+" "+address);
		SaveData.sava(id, name, address);
		return "返回成功";
	}

}
