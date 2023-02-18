package com.bamboo.service;

import com.bamboo.dto.RoomPreferencesDTO;
import com.bamboo.entity.RoomPreferences;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoomPreferencesService extends IService<RoomPreferences> {
    int saveToPreferences(RoomPreferencesDTO roomPreferencesDTO);
}
