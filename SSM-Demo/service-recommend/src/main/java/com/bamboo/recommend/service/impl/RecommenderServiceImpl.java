package com.bamboo.recommend.service.impl;

import com.bamboo.recommend.engine.UserBasedRecommender;
import com.bamboo.recommend.service.RecommenderService;
import com.uwan.common.dto.RecommenderDTO;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RecommenderServiceImpl implements RecommenderService {
    @Autowired
    UserBasedRecommender userBasedRecommender;

    @Override
    public List<RecommendedItem> getUserBasedRecommender(@RequestBody RecommenderDTO recommenderDTO) {
        Integer userId = recommenderDTO.getUserId();
        Integer size = recommenderDTO.getSize();
        List<RecommendedItem> recommendedItemList = userBasedRecommender.userBasedRecommender(userId, size);
        return recommendedItemList;
    }
}
