package com.bamboo.service.impl;

import com.bamboo.constant.request.RedisExecuteStatus;
import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.dto.BambooMusicInfoDTO;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.feign.KafkaFeignService;
import com.bamboo.service.feign.MusicInfoFeignService;
import com.bamboo.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.uwan.common.constant.KafkaSendMessageOperate;
import com.uwan.common.dto.KafkaSendMessageDTO;
import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.entity.MusicInfo;
import com.uwan.common.entity.out.RestPage;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BambooMusicInfoServiceImpl extends ServiceImpl<BambooMusicInfoMapper, BambooMusicInfo> implements BambooMusicInfoService {
    @Autowired
    BambooMusicInfoMapper musicInfoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RBloomFilter bloomFilter;

    @Autowired
    KafkaFeignService kafkaFeignService;

    @Autowired
    MusicInfoFeignService musicInfoFeignService;

    @Value("${custom.expiration.time.bamboomusic_query}")
    private  Integer expireTime;

    @Value("${custom.kafka.topic.musicinfo}")
    private String musicinfoTopic;

    @Override
    public Page<BambooMusicInfo> queryBambooMusic(String search, String page) {
        //current page number
        String pageNumber="1";
        if (!StringUtils.isEmpty(page)){
            pageNumber=page;
        }
        Page<BambooMusicInfo> bambooMusicInfoPage=new Page<>(Integer.parseInt(pageNumber),9);
        return bambooMusicInfoPage;
    }

    @Override
    public int saveToRedis(Page bambooMusicInfoPage, String key) {
        if (bambooMusicInfoPage.getRecords().size()>0){
            redisTemplate.opsForValue().set(key,bambooMusicInfoPage,expireTime, TimeUnit.SECONDS);
        }else {
            //布隆添加
            bloomFilter.add(key);
        }
        return RedisExecuteStatus.INSERT_SUCCESS.getValue();
    }

    @Override
    public Page<BambooMusicInfo> queryFormRedis(String name, String search, String page) throws Exception {
        String key=name+search+page;
        Page<BambooMusicInfo> bambooMusicInfoPage = (Page<BambooMusicInfo>) redisTemplate.opsForValue().get(key);
        if (bambooMusicInfoPage!=null){
            return bambooMusicInfoPage;
        }
        UserVO userVO= (UserVO) redisTemplate.opsForValue().get(name);
        //布隆过滤
        if (userVO==null){
            if (bloomFilter.contains(key)){
               throw new Exception(RedisExecuteStatus.NO_VALUE_EXISTS.getMsg());
            }
        }
        return null;
    }

    @Override
    public void beforeInsertCheck(String author) throws SQLException {
        QueryWrapper<BambooMusicInfo> queryWrapper= Wrappers.query();
        queryWrapper.eq("author",author);
        Integer count = musicInfoMapper.selectCount(queryWrapper);
        if (count!=null && count>0){
            int delete = musicInfoMapper.delete(queryWrapper);
            if (SqlExecuteStatus.DELETE_FAIL.getValue()==delete){
                throw new SQLException(SqlExecuteStatus.DELETE_FAIL.getMsg());
            }
        }
    }

    @Override
    public void addToElasticSearch(BambooMusicInfo bambooMusicInfo) {
        //处理添加的musicinfo
        MusicInfoDTO musicInfoDTO=new MusicInfoDTO();
//        musicInfoDTO.setId(String.valueOf(bambooMusicInfo.getId()));
        musicInfoDTO.setTitle(bambooMusicInfo.getTitle());
        musicInfoDTO.setRoomId(bambooMusicInfo.getRoomId());
        musicInfoDTO.setAuthor(bambooMusicInfo.getAuthor());
        musicInfoDTO.setRemark(bambooMusicInfo.getRemarks());
        musicInfoDTO.setLiveUrl(bambooMusicInfo.getLiveUrl());
        musicInfoDTO.setToken(bambooMusicInfo.getToken());
        musicInfoDTO.setImgFile(bambooMusicInfo.getImgFile());

        //发送的message
        Gson gson=new Gson();
        String sendMessage = gson.toJson(musicInfoDTO);

        //异步kafka消息
        KafkaSendMessageDTO kafkaSendMessageDTO=new KafkaSendMessageDTO();
        kafkaSendMessageDTO.setTopic(musicinfoTopic);
        kafkaSendMessageDTO.setOperate(KafkaSendMessageOperate.ADD_MUSICINFO.getValue());
        kafkaSendMessageDTO.setMessage(sendMessage);
        kafkaFeignService.sendMessage(kafkaSendMessageDTO);
    }

    @Override
    public Page<MusicInfo> queryFormElasticsearch(String search,String page) {
        ResponseEntity<List<MusicInfo>> responseEntity = musicInfoFeignService.query(search, page);
        List<MusicInfo> musicInfoListFormElasticsearch = responseEntity.getBody();

        if (musicInfoListFormElasticsearch.size()==0){
            return null;
        }

        Page<MusicInfo> musicInfoPage=new Page<>(Integer.parseInt(page),9);
        musicInfoPage.setRecords(musicInfoListFormElasticsearch);
//        musicInfoPage.setPages(Long.parseLong(page));
        musicInfoPage.setTotal(musicInfoListFormElasticsearch.size());
        return musicInfoPage;
    }

    @Override
    public void delete(String username) {
        KafkaSendMessageDTO kafkaSendMessageDTO=new KafkaSendMessageDTO();
        kafkaSendMessageDTO.setTopic(musicinfoTopic);
        kafkaSendMessageDTO.setOperate(KafkaSendMessageOperate.DELETE_MUSICINFO.getValue());
        kafkaSendMessageDTO.setMessage(username);
        kafkaFeignService.sendMessage(kafkaSendMessageDTO);
    }

    /**
     * 判断key是否过期
     * @param key
     * @return
     */
    public boolean isExpire(String key) {
        return expire(key) > 1?false:true;
    }


    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     * @param key
     * @return
     */
    public long expire(String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
