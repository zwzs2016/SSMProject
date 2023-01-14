package com.bamboo.search.controller;

import com.bamboo.search.constant.request.ElasticsearchExecuteStatus;
import com.bamboo.search.service.MusicInfoService;
import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.entity.MusicInfo;
import com.uwan.common.entity.out.ResponseEntityResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PostMapping("query/{search}/{page}")
    public ResponseEntity<List<MusicInfo>> query(@PathVariable(name = "search") String search, @PathVariable(name = "page") String page){
        List<MusicInfo> musicInfoList=musicInfoService.query(search,page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(musicInfoList);
    }

    @PostMapping("delete/{author}")
    public ResponseEntity delete(@PathVariable(name = "author") String author){
        int result=musicInfoService.delete(author);
        if (result==ElasticsearchExecuteStatus.DELETE_SUCCESS.getValue()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseEntityResult(String.valueOf(HttpStatus.OK.value()),ElasticsearchExecuteStatus.DELETE_SUCCESS.getMsg(),null,true));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseEntityResult(String.valueOf(HttpStatus.BAD_REQUEST.value()),ElasticsearchExecuteStatus.DELETE_FAIL.getMsg(),null,false));
    }

}
