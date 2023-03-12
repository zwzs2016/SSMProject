package com.bamboo.recommend.engine;

import com.bamboo.recommend.model.MyDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class MySlopeOneRecommender {
	@Autowired
	MyDataModel myDataModel;

	public List<RecommendedItem> mySlopeOneRecommender(long userID,int size){
		List<RecommendedItem> recommendations = null;
		try {
//			DataModel model = new FileDataModel(new File("D:/ml-1m/movie_preferences.txt"));//构造数据模型
			DataModel model = myDataModel.myDataModel();//构造数据模型
			Recommender recommender = new CachingRecommender(new org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender(model));//构造推荐引擎
			recommendations = recommender.recommend(userID, size);//得到推荐的结果，size是推荐接过的数目
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return recommendations;
	}

}
