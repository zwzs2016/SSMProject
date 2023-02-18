package com.bamboo.recommend.controller;

import com.bamboo.recommend.service.RecommenderService;
import com.uwan.common.dto.RecommenderDTO;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("recommender")
public class RecommenderController {
    @Autowired
    RecommenderService recommenderService;

    @PostMapping
    public ResponseEntity recommender(@RequestBody RecommenderDTO recommenderDTO){
        List<RecommendedItem> recommendedItemList = recommenderService.getUserBasedRecommender(recommenderDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
