package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.dto.RoomPreferencesDTO;
import com.bamboo.service.RoomPreferencesService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roompreferences")
@Api("房间欢迎程度")
public class RoomPreferencesController {
    @Qualifier("myStringEncryptor")
    @Autowired
    StringEncryptor stringEncryptor;

    @Autowired
    RoomPreferencesService roomPreferencesService;


    @Operation(summary = "用户房间评分")
    @PostMapping("/preferences")
    public ResponseEntity roomPreferences(@RequestBody RoomPreferencesDTO roomPreferencesDTO, Authentication authentication){
        String username=authentication.getName();
        if (username!=null){
            int status = roomPreferencesService.saveToPreferences(roomPreferencesDTO);
            if (status== SqlExecuteStatus.INSERT_SUCCESS.getValue()){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
