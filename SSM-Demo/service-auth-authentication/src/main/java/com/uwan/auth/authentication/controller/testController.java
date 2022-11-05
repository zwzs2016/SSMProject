package com.uwan.auth.authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @PreAuthorize("hasAuthority('p1')")
    @GetMapping("/test/auth_p1")
    public String init(){
        return "auth_p1_ok";
    }

    @PreAuthorize("hasAuthority('p8')")
    @GetMapping("/test1/auth_p8")
    public String init1(){
        return "auth_p8";
    }

    @GetMapping("/test2/auth_public")
    public String init2(){
        return "auth_public";
    }
}
