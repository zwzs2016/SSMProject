package com.bamboo.controller;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.UserService;
import com.bamboo.service.feign.KafkaFeignService;
import com.bamboo.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.uwan.common.constant.KafkaSendMessageOperate;
import com.uwan.common.dto.KafkaSendMessageDTO;
import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.entity.MusicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    UserService userService;

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
            //处理添加的musicinfo
            MusicInfoDTO musicInfoDTO=new MusicInfoDTO();
            musicInfoDTO.setId(String.valueOf(bambooMusicInfo.getId()));
            musicInfoDTO.setTitle(bambooMusicInfo.getTitle());
            musicInfoDTO.setRoomId(bambooMusicInfo.getRoomId());
            musicInfoDTO.setAuthor(bambooMusicInfo.getAuthor());
            musicInfoDTO.setRemark(bambooMusicInfo.getRemarks());

            //发送的message
            Gson gson=new Gson();
            String sendMessage = gson.toJson(musicInfoDTO);

            //异步kafka消息
            KafkaSendMessageDTO kafkaSendMessageDTO=new KafkaSendMessageDTO();
            kafkaSendMessageDTO.setTopic("musicinfosearch");
            kafkaSendMessageDTO.setOperate(KafkaSendMessageOperate.ADD_MUSICINFO.getValue());
            kafkaSendMessageDTO.setMessage(sendMessage);
            kafkaFeignService.sendMessage(kafkaSendMessageDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(SqlExecuteStatus.INSERT_SUCCESS.getMsg());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(SqlExecuteStatus.INSERT_FAIL.getMsg());

    }

    @GetMapping("queryBambooMusic/{search}/{page}")
    public ResponseEntity<Page> queryBambooMusicByAuthor(@PathVariable(name = "search",required = true) String search, @PathVariable(name = "page",required = true) String page, Authentication authentication) throws Exception {
        Page bambooMusicInfoPageFormRedis=bambooMusicInfoService.queryFormRedis(authentication.getName(),search,page);

        if (bambooMusicInfoPageFormRedis!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bambooMusicInfoPageFormRedis);
        }

        Page<MusicInfo> bambooMusicInfoPageFormElasticsearch=bambooMusicInfoService.queryFormElasticsearch(search,page);

        if (bambooMusicInfoPageFormElasticsearch!=null){
            bambooMusicInfoService.saveToRedis(bambooMusicInfoPageFormElasticsearch,authentication.getName()+search+page);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bambooMusicInfoPageFormElasticsearch);
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

    @PostMapping("/queryCurrentBambooMusic")
    public ResponseEntity<UserVO> queryCurrentBambooMusic(Authentication authentication){
        String username=authentication.getName();
        if (username!=null){
            //是当前用户
            QueryWrapper<BambooMusicInfo> queryWrapper=Wrappers.query();
            queryWrapper.eq("author",username);
            int count = bambooMusicInfoService.count(queryWrapper);
            if (count==1){
                //如果是当前用户，则返回userVO信息
                UserVO userVO=userService.getUserVo(username);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(userVO);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }


}
