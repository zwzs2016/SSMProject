package com.bamboo.service.impl;

import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.dto.RoomPreferencesDTO;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.entity.RoomPreferences;
import com.bamboo.entity.User;
import com.bamboo.mapper.BambooMusicInfoMapper;
import com.bamboo.mapper.RoomPreferencesMapper;
import com.bamboo.mapper.UserMapper;
import com.bamboo.service.RoomPreferencesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoomPreferencesServiceImpl extends ServiceImpl<RoomPreferencesMapper, RoomPreferences> implements RoomPreferencesService {
    @Qualifier("myStringEncryptor")
    @Autowired
    StringEncryptor stringEncryptor;

    @Autowired
    RoomPreferencesMapper roomPreferencesMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BambooMusicInfoMapper bambooMusicInfoMapper;

    @Override
    public int saveToPreferences(RoomPreferencesDTO roomPreferencesDTO) {
        //用户对房间的评分
        String liveCode= roomPreferencesDTO.getLiveCode();
        //被评价的username
        String name = stringEncryptor.decrypt(liveCode);
        //查询出userid
        QueryWrapper queryWrapper= Wrappers.query();
        queryWrapper.eq("name",name);
        queryWrapper.select("id");

        User user = userMapper.selectOne(queryWrapper);

        //查询出roomid
        queryWrapper.clear();
        queryWrapper.eq("author",name);
        queryWrapper.select("room_id");

        BambooMusicInfo bambooMusicInfo = bambooMusicInfoMapper.selectOne(queryWrapper);

        RoomPreferences roomPreferences=new RoomPreferences();
        roomPreferences.setUserId(user.getId());
        roomPreferences.setRoomId(bambooMusicInfo.getRoomId());
        roomPreferences.setPreference(roomPreferencesDTO.getPreference());

        boolean save = this.save(roomPreferences);
        if (save){
            return SqlExecuteStatus.INSERT_SUCCESS.getValue();
        }
        return SqlExecuteStatus.INSERT_FAIL.getValue();
    }
}
