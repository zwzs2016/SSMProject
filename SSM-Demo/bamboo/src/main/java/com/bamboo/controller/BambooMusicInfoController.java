package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.feign.KafkaFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uwan.common.dto.KafkaSendMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("bamboomusic")
public class BambooMusicInfoController {
    @Autowired
    BambooMusicInfoMapper musicInfoMapper;

    @Autowired
    BambooMusicInfoService bambooMusicInfoService;

    @Autowired
    KafkaFeignService kafkaFeignService;

    @GetMapping("getAll")
    public ResponseEntity<List<BambooMusicInfo>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(musicInfoMapper.selectList(null));
    }

    @PostMapping("addBambooMusic")
    public ResponseEntity addBambooMusic(@RequestBody BambooMusicInfo bambooMusicInfo) throws SQLException {
        //beforeInsertCheck
        bambooMusicInfoService.beforeInsertCheck(bambooMusicInfo.getAuthor());
        if(SqlExecuteStatus.INSERT_SUCCESS.getValue()==musicInfoMapper.insert(bambooMusicInfo)){
            //异步kafka消息
            KafkaSendMessageDTO kafkaSendMessageDTO=new KafkaSendMessageDTO();
            kafkaSendMessageDTO.setTopic("musicinfosearch");
            kafkaSendMessageDTO.setMessage("add");
            kafkaFeignService.sendMessage(kafkaSendMessageDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(SqlExecuteStatus.INSERT_SUCCESS.getMsg());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(SqlExecuteStatus.INSERT_FAIL.getMsg());

    }

    @GetMapping("queryBambooMusic/{search}")
    public ResponseEntity<Page<BambooMusicInfo>> queryBambooMusicByAuthor(@PathVariable(name = "search",required = true) String search, String page, Authentication authentication) throws Exception {
        Page<BambooMusicInfo> bambooMusicInfoPageFormRedis=bambooMusicInfoService.queryFormRedis(authentication.getName(),search,page);

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
