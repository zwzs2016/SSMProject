package com.bamboo.recommend.service;

import com.uwan.common.dto.RecommenderDTO;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

public interface RecommenderService {
    List<RecommendedItem> getUserBasedRecommender(RecommenderDTO recommenderDTO);
}
