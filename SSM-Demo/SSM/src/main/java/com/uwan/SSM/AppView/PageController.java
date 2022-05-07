package com.uwan.SSM.AppView;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.uwan.SSM.AppBeans.JsonUtils;
import com.uwan.SSM.AppBeans.Person;
import com.uwan.SSM.AppEntity.User;
import com.uwan.SSM.AppService.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.uwan.SSM.AppConfig.WebbeanConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    @Autowired
    JsonUtils jsonUtils;
    @Autowired
    UserService userService;
    @Autowired
    Person person;
    @GetMapping("/")
    public String index(){
        return "home";
    }
    @GetMapping("/home")
    public String home(Map<String,String> map){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        String todaytime = dateFormat.format(cal.getTime());
        map.put("today",todaytime);
        return "";
    }
    @RequestMapping("/HelloHtml")
    public String helloHtml(Map<String,String> map){
        map.put("hello","from TemplateController.helloHtml");
        return "/HelloHtml";
    }
    @GetMapping("/Userinfo")
    public String Userinfo(){
        return "Useradd";
    }

    @CrossOrigin
    @RequestMapping(value = "/select/user",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView userinfo(@RequestParam int id){
        List<User> userList = userService.getUserList(id);
        Map<String,List> map=new HashMap<>();
        map.put("value:",userList);
//        String s = jsonUtils.objectToJson(userList);
        ApplicationContext atx=new AnnotationConfigApplicationContext(WebbeanConfig.class);
        return new ModelAndView(new MappingJackson2JsonView(),map);
    }
    @CrossOrigin
    @RequestMapping(value = "/postsome",method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject postsome(@RequestParam(value = "name") String name, @RequestParam(value = "age") int age,String call){
        JSONPObject jsonpObject=new JSONPObject(call,"ok");
        return jsonpObject;
    }
}
