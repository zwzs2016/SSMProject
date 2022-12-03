package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bamboomusic")
public class BambooMusicController {
    @Autowired
    BambooMusicInfoMapper musicInfoMapper;

    @GetMapping("getAll")
    public ResponseEntity<List<BambooMusicInfo>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(musicInfoMapper.selectList(null));
    }

    @PostMapping("addBambooMusic")
    public ResponseEntity addBambooMusic(@RequestBody BambooMusicInfo bambooMusicInfo){
        if(SqlExecuteStatus.INSERT_SUCCESS.getValue()==musicInfoMapper.insert(bambooMusicInfo)){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(SqlExecuteStatus.INSERT_SUCCESS.getMsg());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(SqlExecuteStatus.INSERT_FAIL.getMsg());

    }

    @GetMapping("queryBambooMusic/{author}")
    public ResponseEntity<BambooMusicInfo> queryBambooMusicByAuthor(@PathVariable(name = "author",required = true) String author){
        QueryWrapper<BambooMusicInfo> queryWrapper= Wrappers.query();
        queryWrapper.eq("author",author);
        return ResponseEntity.status(HttpStatus.OK)
                .body(musicInfoMapper.selectOne(queryWrapper));
    }


}
