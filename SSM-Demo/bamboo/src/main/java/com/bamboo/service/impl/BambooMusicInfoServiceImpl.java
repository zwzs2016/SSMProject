package com.bamboo.service.impl;

import com.bamboo.constant.request.RedisExecuteStatus;
import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Service
public class BambooMusicInfoServiceImpl extends ServiceImpl<BambooMusicInfoMapper, BambooMusicInfo> implements BambooMusicInfoService {
    @Autowired
    BambooMusicInfoMapper musicInfoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${custom.expiration.time.bamboomusic_query}")
    private  Integer expireTime;

    @Autowired
    RBloomFilter bloomFilter;

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
    public int saveToRedis(Page<BambooMusicInfo> bambooMusicInfoPage, String key) {
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
