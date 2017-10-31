package com.histudent.jwsoft.histudent.commen.utils;

import com.histudent.jwsoft.histudent.body.hiworld.bean.AboutMineBean;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.body.homepage.bean.TopicActionBean;
import com.histudent.jwsoft.histudent.body.mine.model.ActivityInfoBean;
import com.histudent.jwsoft.histudent.body.mine.model.CommentsModel;
import com.histudent.jwsoft.histudent.body.mine.model.EssayModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserBlogItemModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserTimeModel;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.PhotoCommentBean;
import com.histudent.jwsoft.histudent.commen.bean.PhotoHeadBean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/8/16.
 * 数据转换器
 */
public class ItemDataExchangeUtils {

    /**
     * 数据源适配器(个人时间轴，hi圈好友动态，hi圈班级动态)
     *
     * @return
     */
    public static List<ActionListBean> dataExchange(List<UserTimeModel.ItemsBean> timeModels) {

        List<ActionListBean> beens = new ArrayList<>();
        for (int i = 0; i < timeModels.size(); i++) {

            ActionListBean bean = new ActionListBean();

            bean.setHtmlUrl(timeModels.get(i).getHtmlUrl());
            bean.setUserId(timeModels.get(i).getUserId());
            bean.setActId(timeModels.get(i).getActId());
            bean.setCreateTime(timeModels.get(i).getLastUpdateTime());
            bean.setSourceId(timeModels.get(i).getSourceId());
            bean.setOwnerId(timeModels.get(i).getOwnerId());
            bean.setActivityItemKey(timeModels.get(i).getActivityItemKey());
            bean.setActionInfo(timeModels.get(i).getActionInfo());
            bean.setLocation(timeModels.get(i).getLocation());
            bean.setPraiseCount(timeModels.get(i).getPraiseCount());
            bean.setForwardCount(timeModels.get(i).getForwardCount());
            bean.setCommentCount(timeModels.get(i).getCommentCount());
            bean.setUserAvatar(timeModels.get(i).getUserAvatar());
            bean.setUserName(timeModels.get(i).getUserName());
            bean.setOwnerName(timeModels.get(i).getOwnerName());
            bean.setLastUpdateTime(timeModels.get(i).getLastUpdateTime());
            bean.setSummary(timeModels.get(i).getSummary());
            bean.setMultiMediaType(timeModels.get(i).getMultiMediaType());
            bean.setTitle(timeModels.get(i).getTitle());
            bean.setLatitude(timeModels.get(i).getLatitude());
            bean.setLongitude(timeModels.get(i).getLongitude());
            bean.setCover(timeModels.get(i).getCover());
            bean.setVideoId(timeModels.get(i).getVideoId());
            bean.setIsPraised(timeModels.get(i).isIsPraised());
            bean.setIsRecommand(timeModels.get(i).isIsRecommand());
            bean.setIsSpecialFollow(timeModels.get(i).isIsSpecialFollow());
            bean.setIsCare(timeModels.get(i).isIsFollowCreator());
            bean.setIsShield(false);
            bean.setOwnerType(timeModels.get(i).getOwnerType());
            bean.setActivityChannel(timeModels.get(i).getActivityChannel());
            bean.setImageCount(timeModels.get(i).getImageCount());
            bean.setAtUserList(timeModels.get(i).getAtUserList());
            bean.setTopicList(timeModels.get(i).getTopicList());
            //评论详情
            List<UserTimeModel.ItemsBean.CommentsBean> itemsBeen = timeModels.get(i).getComments();
            if (itemsBeen != null) {
                List<CommentsModel> itemsBeen1 = new ArrayList<>();
                CommentsModel itemsBean_;
                for (int m = 0; m < itemsBeen.size(); m++) {
                    itemsBean_ = new CommentsModel();
                    itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                    itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                    itemsBean_.setToUserId(itemsBeen.get(m).getToUser().getUserId());
                    itemsBean_.setContent(itemsBeen.get(m).getContent());
                    itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                    itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                    CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                    userBean.setName(itemsBeen.get(m).getUser().getName());
                    userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                    userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                    itemsBean_.setUser(userBean);

                    CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                    toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                    toUserBean.setName(itemsBeen.get(m).getToUser().getName());
                    toUserBean.setAvatar(itemsBeen.get(m).getToUser().getAvatar());
                    toUserBean.setContent(itemsBeen.get(m).getToUser().getContent());
                    itemsBean_.setToUser(toUserBean);

                    itemsBeen1.add(itemsBean_);
                }

                bean.setItemsBeen(itemsBeen1);
            }

            List<UserTimeModel.ItemsBean.ImagesBean> imageModels = timeModels.get(i).getImages();
            if (imageModels != null) {
                List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
                ActionListBean.ImagesBean imagesBean;
                for (int m = 0; m < imageModels.size(); m++) {
                    imagesBean = new ActionListBean.ImagesBean();
                    imagesBean.setImgId(imageModels.get(m).getImgId());
                    imagesBean.setName(imageModels.get(m).getName());
                    imagesBean.setThumbnailUrl(imageModels.get(m).getThumbnailUrl());
                    imagesBean.setBigSizeUrl(imageModels.get(m).getBigSizeUrl());
                    imagesBeens.add(imagesBean);
                }
                bean.setImages(imagesBeens);
            }

            beens.add(bean);
        }

        return beens;
    }

    /**
     * 数据源适配器(动态详情)
     *
     * @return
     */
    public static void dataExchange_ActionInfo(ActionListBean bean, ActivityInfoBean infoBean) {
        bean.setHtmlUrl(infoBean.getHtmlUrl());
        bean.setUserId(infoBean.getUserId());
        bean.setActId(infoBean.getActId());
        bean.setCreateTime(infoBean.getLastUpdateTime());
        bean.setSourceId(infoBean.getSourceId());
        bean.setOwnerId(infoBean.getOwnerId());
        bean.setActivityItemKey(infoBean.getActivityItemKey());
        bean.setActionInfo(infoBean.getActionInfo());
        bean.setLocation(infoBean.getLocation());
        bean.setPraiseCount(infoBean.getPraiseCount());
        bean.setForwardCount(infoBean.getForwardCount());
        bean.setCommentCount(infoBean.getCommentCount());
        bean.setUserAvatar(infoBean.getUserAvatar());
        bean.setUserName(infoBean.getUserName());
        bean.setOwnerName(infoBean.getOwnerName());
        bean.setLastUpdateTime(infoBean.getLastUpdateTime());
        bean.setSummary(infoBean.getSummary());
        bean.setMultiMediaType(infoBean.getMultiMediaType());
        bean.setTitle(infoBean.getTitle());
        bean.setCover(infoBean.getCover());
        bean.setLatitude(infoBean.getLatitude());
        bean.setLongitude(infoBean.getLongitude());
        bean.setVideoId(infoBean.getVideoId());
        bean.setIsPraised(infoBean.isIsPraised());
        bean.setIsRecommand(infoBean.isIsRecommand());
        bean.setIsSpecialFollow(infoBean.isIsSpecialFollow());
        bean.setIsCare(infoBean.isIsFollowCreator());
        bean.setIsShield(false);
        bean.setOwnerType(infoBean.getOwnerType());
        bean.setActivityChannel(infoBean.getActivityChannel());
        bean.setImageCount(infoBean.getImageCount());
        bean.setTopicList(infoBean.getTopicList());
        bean.setAtUserList(infoBean.getAtUserList());

        //评论详情
        List<ActivityInfoBean.CommentsBean> itemsBeen = infoBean.getComments();
        if (itemsBeen != null) {
            List<CommentsModel> itemsBeen1 = new ArrayList<>();
            CommentsModel itemsBean_;
            for (int m = 0; m < itemsBeen.size(); m++) {
                itemsBean_ = new CommentsModel();
                itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                itemsBean_.setToUserId(itemsBeen.get(m).getToUserId());
                itemsBean_.setContent(itemsBeen.get(m).getContent());
                itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                userBean.setName(itemsBeen.get(m).getUser().getName());
                userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                itemsBean_.setUser(userBean);

                CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                toUserBean.setName(itemsBeen.get(m).getToUser().getName());
                toUserBean.setAvatar(itemsBeen.get(m).getToUser().getAvatar());
                toUserBean.setContent(itemsBeen.get(m).getToUser().getContent());
                itemsBean_.setToUser(toUserBean);

                itemsBeen1.add(itemsBean_);
            }
            bean.setItemsBeen(itemsBeen1);
        }

        //点赞头像
        List<ActivityInfoBean.PraiseUsersBean> praiseUsersModels = infoBean.getPraiseUsers();
        if (praiseUsersModels != null) {
            List<ActionListBean.PraiseUsersBean> praiseUsers = new ArrayList<>();
            ActionListBean.PraiseUsersBean praiseUsersBean;
            for (int n = 0; n < praiseUsersModels.size(); n++) {
                praiseUsersBean = new ActionListBean.PraiseUsersBean();
                praiseUsersBean.setName(praiseUsersModels.get(n).getName());
                praiseUsersBean.setAvatar(praiseUsersModels.get(n).getAvatar());
                praiseUsersBean.setUserId(praiseUsersModels.get(n).getUserId());
                praiseUsers.add(praiseUsersBean);
            }
            bean.setPraiseUsers(praiseUsers);
        }

        //图片
        List<ActivityInfoBean.ImagesBean> imageModels = infoBean.getImages();
        if (imageModels != null) {
            List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
            ActionListBean.ImagesBean imagesBean;
            for (int m = 0; m < imageModels.size(); m++) {
                imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setImgId(imageModels.get(m).getImgId());
                imagesBean.setName(imageModels.get(m).getName());
                imagesBean.setThumbnailUrl(imageModels.get(m).getThumbnailUrl());
                imagesBean.setBigSizeUrl(imageModels.get(m).getBigSizeUrl());
                imagesBeens.add(imagesBean);
            }
            bean.setImages(imagesBeens);
        }

    }

    /**
     * 数据源适配器(图片点赞详情)
     *
     * @return
     */
    public static void dataExchange_PtictureInfo(ActionListBean bean, PhotoCommentBean infoBean, PhotoHeadBean photoHeadBean) {

        bean.setActivityItemKey("Picture");
        bean.setActId(infoBean.getObjectId());
        bean.setPraiseCount(infoBean.getPraiseCount());
        bean.setIsPraised(infoBean.isIsPraise());
        bean.setCommentCount(infoBean.getCommentCount());
        bean.setUserAvatar(photoHeadBean.getUserAvatar());
        bean.setUserName(photoHeadBean.getUserName());
        bean.setUserId(photoHeadBean.getUserId());
        bean.setLastUpdateTime(photoHeadBean.getTime());

        //评论详情
        List<PhotoCommentBean.CommentListBean> itemsBeen = infoBean.getCommentList();
        if (itemsBeen != null) {
            List<CommentsModel> itemsBeen1 = new ArrayList<>();
            CommentsModel itemsBean_;
            for (int m = 0; m < itemsBeen.size(); m++) {
                itemsBean_ = new CommentsModel();
                itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                itemsBean_.setToUserId(itemsBeen.get(m).getToUserId());
                itemsBean_.setContent(itemsBeen.get(m).getContent());
                itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                userBean.setName(itemsBeen.get(m).getUser().getName());
                userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                itemsBean_.setUser(userBean);

                CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                toUserBean.setName(itemsBeen.get(m).getToUser().getName());
                toUserBean.setAvatar(itemsBeen.get(m).getToUser().getAvatar());
                toUserBean.setContent(itemsBeen.get(m).getToUser().getContent());
                itemsBean_.setToUser(toUserBean);

                itemsBeen1.add(itemsBean_);
            }
            bean.setItemsBeen(itemsBeen1);
        }

        //点赞头像
        List<PhotoCommentBean.PraiseListBean> praiseUsersModels = infoBean.getPraiseList();
        if (praiseUsersModels != null) {
            List<ActionListBean.PraiseUsersBean> praiseUsers = new ArrayList<>();
            ActionListBean.PraiseUsersBean praiseUsersBean;
            for (int n = 0; n < praiseUsersModels.size(); n++) {
                praiseUsersBean = new ActionListBean.PraiseUsersBean();
                praiseUsersBean.setName(praiseUsersModels.get(n).getName());
                praiseUsersBean.setAvatar(praiseUsersModels.get(n).getAvatar());
                praiseUsersBean.setUserId(praiseUsersModels.get(n).getUserId());
                praiseUsers.add(praiseUsersBean);
            }
            bean.setPraiseUsers(praiseUsers);
        }

        //图片
        List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
        ActionListBean.ImagesBean imagesBean = new ActionListBean.ImagesBean();
        imagesBean.setThumbnailUrl(photoHeadBean.getPath());
        imagesBean.setBigSizeUrl(photoHeadBean.getPath());
        imagesBeens.add(imagesBean);
        bean.setImages(imagesBeens);
    }

    /**
     * 数据源适配器(话题动态)
     *
     * @return
     */
    public static List<ActionListBean> dataExchange_Topic(List<TopicActionBean.ItemsBean> timeModels) {

        List<ActionListBean> beens = new ArrayList<>();
        ActionListBean bean;
        for (int i = 0; i < timeModels.size(); i++) {
            bean = new ActionListBean();

            bean.setHtmlUrl(timeModels.get(i).getHtmlUrl());
            bean.setUserId(timeModels.get(i).getUserId());
            bean.setActId(timeModels.get(i).getActId());
            bean.setCreateTime(timeModels.get(i).getLastUpdateTime());
            bean.setSourceId(timeModels.get(i).getSourceId());
            bean.setOwnerId(timeModels.get(i).getOwnerId());
            bean.setActivityItemKey(timeModels.get(i).getActivityItemKey());
            bean.setActionInfo(timeModels.get(i).getActionInfo());
            bean.setLocation(timeModels.get(i).getLocation());
            bean.setPraiseCount(timeModels.get(i).getPraiseCount());
            bean.setForwardCount(timeModels.get(i).getForwardCount());
            bean.setCommentCount(timeModels.get(i).getCommentCount());
            bean.setUserAvatar(timeModels.get(i).getUserAvatar());
            bean.setUserName(timeModels.get(i).getUserName());
            bean.setOwnerName(timeModels.get(i).getOwnerName());
            bean.setLastUpdateTime(timeModels.get(i).getLastUpdateTime());
            bean.setSummary(timeModels.get(i).getSummary());
            bean.setTitle(timeModels.get(i).getTitle());
            bean.setCover(timeModels.get(i).getCover());
            bean.setLatitude(timeModels.get(i).getLatitude());
            bean.setLongitude(timeModels.get(i).getLongitude());
            bean.setIsPraised(timeModels.get(i).isIsPraised());
            bean.setIsSpecialFollow(timeModels.get(i).isIsSpecialFollow());
            bean.setIsCare(timeModels.get(i).isIsFollowCreator());
            bean.setMultiMediaType(timeModels.get(i).getMultiMediaType());
            bean.setVideoId(timeModels.get(i).getVideoId());
            bean.setIsShield(false);
            bean.setOwnerType(timeModels.get(i).getOwnerType());
            bean.setActivityChannel(timeModels.get(i).getActivityChannel());
            bean.setAtUserList(timeModels.get(i).getAtUserList());
            bean.setTopicList(timeModels.get(i).getTopicList());
            List<TopicActionBean.ItemsBean.ImagesBean> imageModels = timeModels.get(i).getImages();
            if (imageModels != null) {
                List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
                ActionListBean.ImagesBean imagesBean;
                for (int m = 0; m < imageModels.size(); m++) {
                    imagesBean = new ActionListBean.ImagesBean();
                    imagesBean.setImgId(imageModels.get(m).getImgId());
                    imagesBean.setName(imageModels.get(m).getName());
                    imagesBean.setThumbnailUrl(imageModels.get(m).getThumbnailUrl());
                    imagesBean.setBigSizeUrl(imageModels.get(m).getBigSizeUrl());
                    imagesBeens.add(imagesBean);
                }
                bean.setImages(imagesBeens);
            }

            //评论详情
            List<TopicActionBean.ItemsBean.CommentsBean> itemsBeen = timeModels.get(i).getComments();
            if (itemsBeen != null) {
                List<CommentsModel> itemsBeen1 = new ArrayList<>();
                CommentsModel itemsBean_;
                for (int m = 0; m < itemsBeen.size(); m++) {
                    itemsBean_ = new CommentsModel();
                    itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                    itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                    itemsBean_.setToUserId(itemsBeen.get(m).getToUser().getUserId());
                    itemsBean_.setContent(itemsBeen.get(m).getContent());
                    itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                    itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                    CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                    userBean.setName(itemsBeen.get(m).getUser().getName());
                    userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                    userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                    itemsBean_.setUser(userBean);

                    CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                    toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                    toUserBean.setName(itemsBeen.get(m).getToUser().getName());
                    toUserBean.setAvatar(itemsBeen.get(m).getToUser().getAvatar());
                    toUserBean.setContent(itemsBeen.get(m).getToUser().getContent());
                    itemsBean_.setToUser(toUserBean);

                    itemsBeen1.add(itemsBean_);
                }
                bean.setItemsBeen(itemsBeen1);
            }

            beens.add(bean);
        }

        return beens;
    }

    /**
     * 数据源适配器(首页推荐动态)
     *
     * @return
     */
    public static List<ActionListBean> dataExchange_Home(List<HomePageAllBean.DataBean.RecommendActivitiesBean.ItemsBeanXXX> timeModels) {

        List<ActionListBean> beens = new ArrayList<>();
        ActionListBean bean;
        for (int i = 0; i < timeModels.size(); i++) {
            bean = new ActionListBean();

            bean.setHtmlUrl(timeModels.get(i).getHtmlUrl());
            bean.setUserId(timeModels.get(i).getUserId());
            bean.setActId(timeModels.get(i).getActId());
            bean.setCreateTime(timeModels.get(i).getLastUpdateTime());
            bean.setSourceId(timeModels.get(i).getSourceId());
            bean.setOwnerId(timeModels.get(i).getOwnerId());
            bean.setActivityItemKey(timeModels.get(i).getActivityItemKey());
            bean.setActionInfo(timeModels.get(i).getActionInfo());
            bean.setLocation(timeModels.get(i).getLocation());
            bean.setPraiseCount(timeModels.get(i).getPraiseCount());
            bean.setForwardCount(timeModels.get(i).getForwardCount());
            bean.setCommentCount(timeModels.get(i).getCommentCount());
            bean.setUserAvatar(timeModels.get(i).getUserAvatar());
            bean.setUserName(timeModels.get(i).getUserName());
            bean.setOwnerName(timeModels.get(i).getOwnerName());
            bean.setLastUpdateTime(timeModels.get(i).getLastUpdateTime());
            bean.setSummary(timeModels.get(i).getSummary());
            bean.setTitle(timeModels.get(i).getTitle());
            bean.setCover(timeModels.get(i).getCover());
            bean.setIsPraised(timeModels.get(i).isIsPraised());
            bean.setIsSpecialFollow(timeModels.get(i).isIsSpecialFollow());
            bean.setIsCare(timeModels.get(i).isIsFollowCreator());
            bean.setIsShield(false);
            bean.setMultiMediaType(timeModels.get(i).getMultiMediaType());
            bean.setVideoId(timeModels.get(i).getVideoId());
            bean.setOwnerType(timeModels.get(i).getOwnerType());
            bean.setActivityChannel(timeModels.get(i).getActivityChannel());
            bean.setImageCount(timeModels.get(i).getImageCount());
            bean.setAtUserList(timeModels.get(i).getAtUserList());
            bean.setTopicList(timeModels.get(i).getTopicList());
            List<HomePageAllBean.DataBean.RecommendActivitiesBean.ItemsBeanXXX.ImagesBean> imageModels = timeModels.get(i).getImages();
            if (imageModels != null) {
                List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
                ActionListBean.ImagesBean imagesBean;
                for (int m = 0; m < imageModels.size(); m++) {
                    imagesBean = new ActionListBean.ImagesBean();
                    imagesBean.setImgId(imageModels.get(m).getImgId());
                    imagesBean.setName(imageModels.get(m).getName());
                    imagesBean.setThumbnailUrl(imageModels.get(m).getThumbnailUrl());
                    imagesBean.setBigSizeUrl(imageModels.get(m).getBigSizeUrl());
                    imagesBeens.add(imagesBean);
                }
                bean.setImages(imagesBeens);
            }

            //评论详情
            List<HomePageAllBean.DataBean.RecommendActivitiesBean.ItemsBeanXXX.CommentsBean> itemsBeen = timeModels.get(i).getComments();
            if (itemsBeen != null) {
                List<CommentsModel> itemsBeen1 = new ArrayList<>();
                CommentsModel itemsBean_;
                for (int m = 0; m < itemsBeen.size(); m++) {
                    itemsBean_ = new CommentsModel();
                    itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                    itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                    itemsBean_.setToUserId(itemsBeen.get(m).getToUser().getUserId());
                    itemsBean_.setContent(itemsBeen.get(m).getContent());
                    itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                    itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                    CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                    userBean.setName(itemsBeen.get(m).getUser().getName());
                    userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                    userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                    itemsBean_.setUser(userBean);

                    CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                    toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                    toUserBean.setName(itemsBeen.get(m).getToUser().getName());
                    toUserBean.setAvatar(itemsBeen.get(m).getToUser().getAvatar());
                    itemsBean_.setToUser(toUserBean);

                    itemsBeen1.add(itemsBean_);
                }
                bean.setItemsBeen(itemsBeen1);
            }

            beens.add(bean);
        }

        return beens;
    }

    /**
     * 数据源适配器(个人随记)
     *
     * @return
     */
    public static List<ActionListBean> dataExchange_Essay(List<EssayModel.ItemsBean> essayModels) {

        List<ActionListBean> beens = new ArrayList<>();
        ActionListBean bean;
        for (int i = 0; i < essayModels.size(); i++) {
            bean = new ActionListBean();

            bean.setUserName(essayModels.get(i).getUserName());
            bean.setUserAvatar(essayModels.get(i).getUserAvatar());
            bean.setLastUpdateTime(essayModels.get(i).getLastUpdateTime());
            bean.setUserId(essayModels.get(i).getUserId());
            bean.setActId(essayModels.get(i).getActId());
            bean.setOwnerId(essayModels.get(i).getOwnerId());
            bean.setActivityItemKey("CreateMicroBlog");
            bean.setSourceId(essayModels.get(i).getSourceId());
            bean.setPraiseCount(essayModels.get(i).getPraiseCount());
            bean.setForwardCount(essayModels.get(i).getForwardCount());
            bean.setCommentCount(essayModels.get(i).getCommentCount());
            bean.setCreateTime(essayModels.get(i).getLastUpdateTime());
            bean.setSummary(essayModels.get(i).getSummary());
            bean.setIsPraised(essayModels.get(i).isIsPraised());
            bean.setLatitude(essayModels.get(i).getLatitude());
            bean.setLongitude(essayModels.get(i).getLongitude());
            bean.setIsCare(true);
            bean.setIsShield(false);
            bean.setCover(essayModels.get(i).getCover());
            bean.setVideoId(essayModels.get(i).getVideoId());
            bean.setHtmlUrl(essayModels.get(i).getHtmlUrl());
            bean.setMultiMediaType(essayModels.get(i).getMultiMediaType());
            bean.setLocation(essayModels.get(i).getLocation());
            bean.setImageCount(essayModels.get(i).getImageCount());

            List<EssayModel.ItemsBean.ImagesBean> imageModels = essayModels.get(i).getImages();
            if (imageModels != null) {
                List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
                ActionListBean.ImagesBean imagesBean;
                for (int m = 0; m < imageModels.size(); m++) {
                    imagesBean = new ActionListBean.ImagesBean();
                    imagesBean.setThumbnailUrl(imageModels.get(m).getThumbnailUrl());
                    imagesBean.setBigSizeUrl(imageModels.get(m).getBigSizeUrl());
                    imagesBeens.add(imagesBean);
                }
                bean.setImages(imagesBeens);
            }

            //评论详情
            List<EssayModel.ItemsBean.CommentsBean> itemsBeen = essayModels.get(i).getComments();
            if (itemsBeen != null) {
                List<CommentsModel> itemsBeen1 = new ArrayList<>();
                CommentsModel itemsBean_;
                if (itemsBeen != null && itemsBeen.size() > 0)
                    for (int m = 0; m < itemsBeen.size(); m++) {
                        itemsBean_ = new CommentsModel();
                        itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                        itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                        itemsBean_.setToUserId(itemsBeen.get(m).getToUser().getUserId());
                        itemsBean_.setContent(itemsBeen.get(m).getContent());
                        itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                        itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                        CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                        userBean.setName(itemsBeen.get(m).getUser().getName());
                        userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                        userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                        itemsBean_.setUser(userBean);

                        CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                        toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                        itemsBean_.setToUser(toUserBean);

                        itemsBeen1.add(itemsBean_);
                    }
                bean.setItemsBeen(itemsBeen1);
            }

            beens.add(bean);
        }

        return beens;
    }


    /**
     * 数据源适配器(个人日志)
     *
     * @return
     */
    public static List<ActionListBean> dataExchange_Blog(List<UserBlogItemModel.ItemsBean> blogModels) {

        List<ActionListBean> beens = new ArrayList<>();
        ActionListBean bean;
        for (int i = 0; i < blogModels.size(); i++) {
            bean = new ActionListBean();

            bean.setUserAvatar(blogModels.get(i).getUserAvatar());
            bean.setUserName(blogModels.get(i).getUserName());
            bean.setHtmlUrl(blogModels.get(i).getHtmlUrl());
            bean.setCover(blogModels.get(i).getCover());
            bean.setUserId(blogModels.get(i).getUserId());
            bean.setActId(blogModels.get(i).getActId());
            bean.setSourceId(blogModels.get(i).getSourceId());
            bean.setOwnerId(blogModels.get(i).getOwnerId());
            bean.setActivityItemKey("CreateBlog");
            bean.setPraiseCount(blogModels.get(i).getPraiseCount());
            bean.setForwardCount(blogModels.get(i).getForwardCount());
            bean.setCommentCount(blogModels.get(i).getCommentCount());
            bean.setCreateTime(blogModels.get(i).getLastUpdateTime());
            bean.setSummary(blogModels.get(i).getSummary());
            bean.setTitle(blogModels.get(i).getTitle());
            bean.setIsPraised(blogModels.get(i).isIsPraised());
            bean.setImageCount(blogModels.get(i).getImageCount());
            bean.setIsCare(true);
            bean.setIsShield(false);

            List<UserBlogItemModel.ItemsBean.PraiseUsersBean> praiseUsersModels = blogModels.get(i).getPraiseUsers();
            if (praiseUsersModels != null) {
                List<ActionListBean.PraiseUsersBean> praiseUsers = new ArrayList<>();
                ActionListBean.PraiseUsersBean praiseUsersBean;
                for (int n = 0; n < praiseUsersModels.size(); n++) {
                    praiseUsersBean = new ActionListBean.PraiseUsersBean();
                    praiseUsersBean.setName(praiseUsersModels.get(n).getName());
                    praiseUsersBean.setAvatar(praiseUsersModels.get(n).getAvatar());
                    praiseUsersBean.setUserId(praiseUsersModels.get(n).getUserId());
                    praiseUsers.add(praiseUsersBean);
                }
                bean.setPraiseUsers(praiseUsers);
            }

            //评论详情
            List<UserBlogItemModel.ItemsBean.CommentsBean> itemsBeen = blogModels.get(i).getComments();
            if (itemsBeen != null) {
                List<CommentsModel> itemsBeen1 = new ArrayList<>();
                CommentsModel itemsBean_;
                if (itemsBeen != null && itemsBeen.size() > 0)
                    for (int m = 0; m < itemsBeen.size(); m++) {
                        itemsBean_ = new CommentsModel();
                        itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                        itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                        itemsBean_.setToUserId(itemsBeen.get(m).getToUser().getUserId());
                        itemsBean_.setContent(itemsBeen.get(m).getContent());
                        itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());
                        itemsBean_.setToCommentContent(itemsBeen.get(m).getToCommentContent());

                        CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                        userBean.setName(itemsBeen.get(m).getUser().getName());
                        userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                        userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                        itemsBean_.setUser(userBean);

                        CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                        toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                        itemsBean_.setToUser(toUserBean);

                        itemsBeen1.add(itemsBean_);
                    }
                bean.setItemsBeen(itemsBeen1);
            }

            beens.add(bean);
        }

        return beens;
    }

    /**
     * 数据源适配器(与我相关)
     *
     * @return
     */
    public static List<ActionListBean> dataExchange_AboutMine(List<AboutMineBean.ItemsBean> mineModels) {

        List<ActionListBean> beens = new ArrayList<>();
        ActionListBean bean;
        for (int i = 0; i < mineModels.size(); i++) {
            bean = new ActionListBean();
            bean.setHtmlUrl(mineModels.get(i).getHtmlUrl());
            bean.setUserId(mineModels.get(i).getUserId());
//            bean.setActId(mineModels.get(i).getActivityItemKey().equals("UploadPhoto") ? mineModels.get(i).getActId() : mineModels.get(i).getSourceId());
            bean.setActId(mineModels.get(i).getActId());
            bean.setCreateTime(mineModels.get(i).getLastUpdateTime());
            bean.setSourceId(mineModels.get(i).getSourceId());
            bean.setOwnerId(mineModels.get(i).getOwnerId());
            bean.setTitle(mineModels.get(i).getTitle());
            bean.setActivityItemKey(mineModels.get(i).getActivityItemKey());
            bean.setActionInfo(mineModels.get(i).getActionInfo());
            bean.setLocation(mineModels.get(i).getLocation());
            bean.setCover(mineModels.get(i).getCover());
            bean.setPraiseCount(mineModels.get(i).getPraiseCount());
            bean.setForwardCount(mineModels.get(i).getForwardCount());
            bean.setCommentCount(mineModels.get(i).getCommentCount());
            bean.setUserAvatar(mineModels.get(i).getUserAvatar());
            bean.setOwnerName(mineModels.get(i).getOwnerName());
            bean.setUserName(mineModels.get(i).getUserName());
            bean.setLastUpdateTime(mineModels.get(i).getLastUpdateTime());
            bean.setSummary(mineModels.get(i).getSummary());
            bean.setIsPraised(mineModels.get(i).isIsPraised());
            bean.setIsCare(true);
            bean.setIsShield(false);
            bean.setActivityChannel(mineModels.get(i).getActivityChannel());
            bean.setOwnerType(mineModels.get(i).getOwnerType());

            //点赞头像
            List<AboutMineBean.ItemsBean.PraiseUsersBean> praiseUsersModels = mineModels.get(i).getPraiseUsers();
            List<ActionListBean.PraiseUsersBean> praiseUsers = new ArrayList<>();
            ActionListBean.PraiseUsersBean praiseUsersBean;
            for (int n = 0; n < praiseUsersModels.size(); n++) {
                praiseUsersBean = new ActionListBean.PraiseUsersBean();
                praiseUsersBean.setName(praiseUsersModels.get(n).getName());
                praiseUsersBean.setAvatar(praiseUsersModels.get(n).getAvatar());
                praiseUsersBean.setUserId(praiseUsersModels.get(n).getUserId());
                praiseUsers.add(praiseUsersBean);
            }
            bean.setPraiseUsers(praiseUsers);

            //照片地址
            List<AboutMineBean.ItemsBean.ImagesBean> imageModels = mineModels.get(i).getImages();
            List<ActionListBean.ImagesBean> imagesBeens = new ArrayList<>();
            ActionListBean.ImagesBean imagesBean;
            for (int m = 0; m < imageModels.size(); m++) {
                imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setName(imageModels.get(m).getName());
                imagesBean.setThumbnailUrl(imageModels.get(m).getThumbnailUrl());
                imagesBean.setBigSizeUrl(imageModels.get(m).getBigSizeUrl());
                imagesBeens.add(imagesBean);
            }
            bean.setImages(imagesBeens);

            //评论详情
            List<AboutMineBean.ItemsBean.CommentsBean> itemsBeen = mineModels.get(i).getComments();
            List<CommentsModel> itemsBeen1 = new ArrayList<>();
            CommentsModel itemsBean_;
            for (int m = 0; m < itemsBeen.size(); m++) {
                itemsBean_ = new CommentsModel();
                itemsBean_.setCommentId(itemsBeen.get(m).getCommentId());
                itemsBean_.setParentId(itemsBeen.get(m).getParentId());
                itemsBean_.setToUserId(itemsBeen.get(m).getToUser().getUserId());
                itemsBean_.setContent(itemsBeen.get(m).getContent());
                itemsBean_.setCreateTime(itemsBeen.get(m).getCreateTime());

                CommentsModel.UserBean userBean = new CommentsModel.UserBean();
                userBean.setName(itemsBeen.get(m).getUser().getName());
                userBean.setAvatar(itemsBeen.get(m).getUser().getAvatar());
                userBean.setUserId(itemsBeen.get(m).getUser().getUserId());
                itemsBean_.setUser(userBean);

                CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
                toUserBean.setUserId(itemsBeen.get(m).getToUser().getUserId());
                toUserBean.setName(itemsBeen.get(m).getToUser().getName());
                toUserBean.setAvatar(itemsBeen.get(m).getToUser().getAvatar());
                itemsBean_.setToUser(toUserBean);

                itemsBeen1.add(itemsBean_);
            }
            bean.setItemsBeen(itemsBeen1);

            beens.add(bean);
        }

        return beens;
    }

    /**
     * **************************** 排序 ***********************************
     */
    public static void sortRecentContacts(List<ActionListBean> list) {
//        if (list.size() == 0) {
//            return;
//        }
//        Collections.sort(list, comp);
    }

    private static Comparator<ActionListBean> comp = new Comparator<ActionListBean>() {

        @Override
        public int compare(ActionListBean o1, ActionListBean o2) {
            // 先比较置顶tag
            long time = com.histudent.jwsoft.histudent.comment2.utils.TimeUtils.getTimeLong(o1.getCreateTime()) - com.histudent.jwsoft.histudent.comment2.utils.TimeUtils.getTimeLong(o2.getCreateTime());
            return time == 0 ? 0 : (time > 0 ? -1 : 1);
        }
    };

}
