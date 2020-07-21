package com.uwan.SSM.AppView;

import com.uwan.SSM.Student;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

@Controller
public class PageController {
    @GetMapping("/hh")
    public String index(){
        return "index";
    }
    @GetMapping("/home")
    public String home(Map<String,String> map){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        String todaytime = dateFormat.format(cal.getTime());
        map.put("today",todaytime);
        return "home";
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
}
