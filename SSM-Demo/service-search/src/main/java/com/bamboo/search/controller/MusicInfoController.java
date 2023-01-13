package com.bamboo.search.controller;

import com.bamboo.search.constant.request.ElasticsearchExecuteStatus;
import com.bamboo.search.dto.MusicInfoDTO;
import com.bamboo.search.entity.MusicInfo;
import com.bamboo.search.service.MusicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("musicinfo")
public class MusicInfoController {
    @Autowired
    MusicInfoService musicInfoService;


    @PostMapping("add")
    public ResponseEntity musicInfo(@RequestBody @Validated MusicInfoDTO musicInfoDTO){
        int status = musicInfoService.addMusicInfo(musicInfoDTO);
        if (status== ElasticsearchExecuteStatus.INSERT_SUCCESS.getValue()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }

    @PostMapping("query/{search}")
    public ResponseEntity<List<MusicInfo>> query(@PathVariable(name = "search") String search){
        List<MusicInfo> musicInfoList=musicInfoService.query(search);
        return ResponseEntity.status(HttpStatus.OK)
                .body(musicInfoList);
    }

}
