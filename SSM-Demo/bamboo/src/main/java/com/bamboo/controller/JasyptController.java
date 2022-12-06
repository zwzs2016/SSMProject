package com.bamboo.controller;

import com.bamboo.entity.out.JasyptString;
import com.uwan.common.util.MyStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Jasypt")
public class JasyptController {
    @Qualifier("myStringEncryptor")
    @Autowired
    StringEncryptor stringEncryptor;

    @PostMapping("/getencry")
    public ResponseEntity<JasyptString> getencry(@RequestBody JasyptString jasyptString){
        String str=stringEncryptor.encrypt(jasyptString.getEncryString());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new JasyptString(str,null));
    }

    @PostMapping("/getdecry")
    public ResponseEntity<JasyptString> getdecry(@RequestBody JasyptString jasyptString){
        String str=stringEncryptor.decrypt(jasyptString.getDecryptString());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new JasyptString(null,str));
    }
}
