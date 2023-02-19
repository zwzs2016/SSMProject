package com.bamboo.recommend.engine;

import com.bamboo.recommend.model.MyDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class UserBasedRecommender {
    @Autowired
    MyDataModel myDataModel;

    public List<RecommendedItem> userBasedRecommender(long userID, int size) {
        // step:1 构建模型 2 计算相似度 3 查找k紧邻 4 构造推荐引擎
        List<RecommendedItem> recommendations = null;
        try {
            DataModel model = myDataModel.myDataModel();//构造数据模型
//            DataModel model = new FileDataModel(new File("/home/zw/文档/doc/mydraft/test1.txt"));//加载数据文件
//            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);//用PearsonCorrelation 算法计算用户相似度
//            UserNeighborhood neighborhood = new NearestNUserNeighborhood(1, similarity, model);//计算用户的“邻居”，这里将与该用户最近距离为 3 的用户设置为该用户的“邻居”。
//            Recommender recommender = new CachingRecommender(new GenericUserBasedRecommender(model, neighborhood, similarity));//采用 CachingRecommender 为 RecommendationItem 进行缓存
//            recommendations = recommender.recommend(userID, size);//得到推荐的结果，size是推荐结果的数目

            UserSimilarity similarity = new PearsonCorrelationSimilarity (model);  //建立推荐模型
            UserNeighborhood neighborhood =  new NearestNUserNeighborhood (2, similarity, model);
            Recommender recommender = new GenericUserBasedRecommender ( model, neighborhood, similarity);
            recommendations =    recommender.recommend(userID, size); //给用户1推荐2个项目
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return recommendations;
    }
}
