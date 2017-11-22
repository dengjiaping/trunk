package com.histudent.jwsoft.histudent.model.convert.main;

import android.text.TextUtils;

import com.histudent.jwsoft.histudent.model.bean.main.HomeEntity;
import com.histudent.jwsoft.histudent.model.bean.main.HomeItemType;
import com.histudent.jwsoft.histudent.model.bean.main.HomePageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/11/14.
 * desc:
 */

public class HomeFragmentDataConvert {

    private HomePageEntity mHomePageEntity;

    private HomeFragmentDataConvert(HomePageEntity homePageEntity) {
        this.mHomePageEntity = homePageEntity;
    }


    public static final HomeFragmentDataConvert create(HomePageEntity homePageEntity) {
        return new HomeFragmentDataConvert(homePageEntity);
    }


    public List<HomeEntity> convert() {
        final List<HomeEntity> list = new ArrayList<>();
        HomeEntity homeEntity;
        if (mHomePageEntity != null) {
            //轮播图
            final List<HomePageEntity.BannerEntity> slideShows = mHomePageEntity.getSlideShows();
            homeEntity = new HomeEntity();
            homeEntity.setType(HomeItemType.BANNER);
            homeEntity.setBannerEntities(slideShows);
            list.add(homeEntity);

            //班级
            final HomePageEntity.RecommendClassEntity recommendClasses = mHomePageEntity.getRecommendClasses();
            final List<HomePageEntity.RecommendClassEntity.SubClassEntity> subClassEntities = recommendClasses.getItems();
            homeEntity = new HomeEntity();
            homeEntity.setType(HomeItemType.SUB_CLASS);
            homeEntity.setSubClassEntities(subClassEntities);
            list.add(homeEntity);

            //活跃用户
            final HomePageEntity.ActiveUserEntity recommendUsers = mHomePageEntity.getRecommendUsers();
            final List<HomePageEntity.ActiveUserEntity.SubActiveUserEntity> subActiveUserEntities = recommendUsers.getItems();
            homeEntity = new HomeEntity();
            homeEntity.setType(HomeItemType.ACTIVE_USER);
            homeEntity.setSubActiveUserEntities(subActiveUserEntities);
            list.add(homeEntity);


            //热门话题
            final HomePageEntity.HotTopicEntity recommendTags = mHomePageEntity.getRecommendTags();
            final List<HomePageEntity.HotTopicEntity.SubHotTopicEntity> subHotTopicEntities = recommendTags.getItems();
            homeEntity = new HomeEntity();
            homeEntity.setType(HomeItemType.HOT_TOPIC);
            homeEntity.setSubHotTopicEntities(subHotTopicEntities);
            list.add(homeEntity);

            //推荐动态(上传图片、日志、随记)
            final HomePageEntity.RecommendDynamicEntity recommendActivities = mHomePageEntity.getRecommendActivities();
            final List<HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity> recommendActivitiesItems = recommendActivities.getItems();
            if (recommendActivitiesItems != null && recommendActivitiesItems.size() > 0) {
                for (HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity item : recommendActivitiesItems) {
                    homeEntity = new HomeEntity();
                    final String activityItemKey = item.getActivityItemKey();
                    if (!TextUtils.isEmpty(activityItemKey)) {
                        switch (activityItemKey) {
                            case "UploadPhoto":
                                homeEntity.setUploadPhotoEntity(item);
                                homeEntity.setType(HomeItemType.UPLOAD_PHOTO);
                                break;
                            case "CreateMicroBlog":
                                homeEntity.setEssayEntity(item);
                                homeEntity.setType(HomeItemType.CREATE_MICROBLOG);
                                break;
                            case "CreateBlog":
                                homeEntity.setActionLogEntity(item);
                                homeEntity.setType(HomeItemType.CREATE_BLOG);
                                break;
                            default:
                                break;
                        }
                    }

                    list.add(homeEntity);
                }
            }

        }

        return list;
    }
}
