package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bamboomusic")
public class BambooMusicInfoController {
    @Autowired
    BambooMusicInfoMapper musicInfoMapper;

    @Autowired
    BambooMusicInfoService bambooMusicInfoService;

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

    @GetMapping("queryBambooMusic/{search}")
    public ResponseEntity<Page<BambooMusicInfo>> queryBambooMusicByAuthor(@PathVariable(name = "search",required = true) String search, String page, Authentication authentication) throws Exception {
        Page<BambooMusicInfo> bambooMusicInfoPageFormRedis=bambooMusicInfoService.queryFormRedis(authentication.getName()+search+page);

        if (bambooMusicInfoPageFormRedis!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bambooMusicInfoPageFormRedis);
        }

        QueryWrapper<BambooMusicInfo> queryWrapper= Wrappers.query();
        queryWrapper.eq("room_id",search)
                .or()
                .likeRight("author",search)
                .or()
                .likeRight("title",search);

        Page<BambooMusicInfo> bambooMusicInfoPage=bambooMusicInfoService.queryBambooMusic(search,page);
        bambooMusicInfoService.page(bambooMusicInfoPage,queryWrapper);
        bambooMusicInfoService.saveToRedis(bambooMusicInfoPage,authentication.getName()+search+page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bambooMusicInfoPage);
    }


}