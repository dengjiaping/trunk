package com.histudent.jwsoft.histudent.info;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/28.
 */

public class InfoHelper {

    /**
     * 访问个人主页
     *
     * @param activity    上下文对象
     * @param userId      访问的个人id
     * @param isCodeApply 是否由扫码进入
     */
    public static void gotoPersonHome(final BaseActivity activity, final String userId, final boolean isCodeApply) {
        PersonCenterActivity.start(activity);

//        CurrentUserDetailInfoModel userModel = HiCache.getInstance().getUserDetailInfo();
//        if (userModel != null && userModel.getUserId().equals(userId) && SystemUtil.isOneselfIn(userModel.getUserId())) {
//            PersonCenterActivity.start(activity, isCodeApply);
//        } else {
//
//            PersionHelper.getInstance().getUserInfo(activity, userId, new HttpRequestCallBack() {
//                @Override
//                public void onSuccess(String result) {
//
//                    CurrentUserDetailInfoModel userModel = HiCache.getInstance().getUserDetailInfo();
//                    if (userModel != null && SystemUtil.isOneselfIn(userModel.getUserId())) {
//                        PersonCenterActivity.start(activity, isCodeApply);
//                    } else {
//                        Map<String, Object> map = new TreeMap<>();
//                        map.put("userId", userId);
//                        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.visitFace, new HttpRequestCallBack() {
//                            @Override
//                            public void onSuccess(String result) {
//
//                                VisitFaceModel model = JSONObject.parseObject(result, VisitFaceModel.myclass);
//
//                                switch (model.getIsVisit()) {
//                                    case 0://通过
//                                        PersonCenterActivity.start(activity, isCodeApply);
//                                        break;
//                                    case 1://回答问题
////                                        AnswerQuestionActivity.start(activity, userId, model.getQuestion(), isCodeApply);
//                                        break;
//                                    case 2://拒绝
////                                        RefuseVisitActivity.start(activity, userId, isCodeApply);
//                                        break;
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(String errorMsg) {
//
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onFailure(String errorMsg) {
//
//                }
//            });
//        }
    }

    /**
     * 访问班级主页
     *
     * @param activity    上下文对象
     * @param classId     访问的班级id
     * @param isCodeApply 是否由扫码进入
     */
    public static void gotoClassHome(final BaseActivity activity, String classId, final boolean isCodeApply) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassModel_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                ClassModel classModel = JSON.parseObject(result, ClassModel.class);
                if (classModel != null) {
                    HiCache.getInstance().setClassDetailInfo(classModel);
//                    ClassCenterActivity.start(activity, isCodeApply);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });

    }


}
