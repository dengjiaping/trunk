package com.histudent.jwsoft.histudent.body.find.helper;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupMemberBean;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.GroupNewsModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.RequestManager;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.bean.CreateTopicModel;
import com.histudent.jwsoft.histudent.model.constant.ParamKeys;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/23.
 */

public class GroupHelper {


    /**
     * /获取发现首页数据
     *
     * @param activity
     * @param topClassCount
     * @param topGroupCount
     * @param callBack
     */
    public static void getFindInfor(Activity activity, int topClassCount, int topGroupCount, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("topClassCount", topClassCount);
        map.put("topGroupCount", topGroupCount);

        AddressInforBean userLocationInfor = HiWorldCache.getUserLocationInfor();
        String city = userLocationInfor.getCity();
        String area = userLocationInfor.getAreaStr();
        if(TextUtils.isEmpty(city) && TextUtils.isEmpty(area)){
            map.put(ParamKeys.CITY_NAME,city);
            map.put(ParamKeys.AREA_NAME,area);
        }else{
            map.put(ParamKeys.CITY_NAME,"");
            map.put(ParamKeys.AREA_NAME,"");
        }
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.findInfor, callBack,LoadingType.NONE);


    }

    /**
     * 获取社群标签
     *
     * @param activity
     * @param categoryType
     * @param categoryLevel
     * @param callBack
     */
    public static void getCommunityTag(Activity activity, int categoryType, int categoryLevel, HttpRequestCallBack callBack,LoadingType Default) {
        Map<String, Object> map = new TreeMap<>();
        map.put("categoryType", categoryType);
        map.put("categoryLevel", categoryLevel);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.communityTag, callBack,Default);
    }

    /**
     * 获得安全票据接口
     *
     * @param activity
     * @param phone
     * @param code
     * @param callBack
     */
    public static void getVerfiyCode(BaseActivity activity, String phone, String code, int codeTypeEnum, HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        map.put("phone", phone);
        map.put("code", code);
        map.put("codeTypeEnum", codeTypeEnum);
        HiStudentHttpUtils.postDataByOKHttp( activity, map, HistudentUrl.VerfiyCode, callBack);
    }

    /**
     * @param activity
     * @param pageSize   返回数据的条数--我们自己定义
     * @param pageIndex  社群页码
     * @param childTagId 二级分类标签的ID
     * @param callBack
     */
    public static void getSecondGroupInfo(Activity activity,int pageSize, int pageIndex, String childTagId, HttpRequestCallBack callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put(ParamKeys.PAGE_SIZE, pageSize);
        map.put(ParamKeys.PAGE_INDEX, pageIndex);
        map.put(ParamKeys.CHILD_TAG_ID, childTagId);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.secondGroupInfor, callBack);
    }


    /**
     * 获取社群搜索所有的数据信息
     * @param context
     * @param showSize
     * @param childTagId
     * @param searchContent
     * @param callBack
     */
    public static void getSearchCommunityInformation(Context context,int showSize,String childTagId,String searchContent,HttpRequestCallBack callBack){
        HashMap<String, Object> hashMap = new HashMap<>();
        if(!TextUtils.isEmpty(searchContent))
            hashMap.put("strKey", searchContent);
        hashMap.put("showSize", showSize);
        hashMap.put("childTagId", childTagId);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, hashMap, HistudentUrl.findGroupsList, callBack);
    }

    /**
     * des:社群搜索调用接口
     * @param activity
     * @param strKey     社群名称或者社群编号关键字
     * @param pageSize   返回数据的条数--我们自己定义
     * @param pageIndex  社群页码
     * @param childTagId 二级分类标签的ID
     * @param callBack
     */
    public static void getSecondGroupInfo(Activity activity, String strKey, int pageSize, int pageIndex, String childTagId, HttpRequestCallBack callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put(ParamKeys.STR_KEY,strKey);
        map.put(ParamKeys.PAGE_SIZE, pageSize);
        map.put(ParamKeys.PAGE_INDEX, pageIndex);
        map.put(ParamKeys.CHILD_TAG_ID, childTagId);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.secondGroupInfor, callBack);
    }


    /**
     * @param activity
     * @param groupId
     * @param pageIndex
     * @param pageSize
     * @param callBack
     */
    public static void GroupSignInfo(Activity activity, String groupId, int pageIndex, int pageSize, HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.group_community_sign, callBack);
    }


    /**
     * 创建社群接口
     *
     * @param activity
     * @param resetToken
     * @param objectType
     * @param objectName
     * @param callBack
     */
    public static void createComunity(
            Activity activity, String resetToken, int objectType,
            String objectName, String objectCode, String mgrName,
            String mgrMobile, boolean isLogo, String groupName, String tagId,
            String childTag, boolean isPublic, String groupIntroduce, boolean isIntroImg, Map<String, String> fileMap,
            HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        map.put("resetToken", resetToken);
        map.put("objectType", objectType);
        map.put("objectName", objectName);
        map.put("objectCode", objectCode);
        map.put("mgrName", mgrName);
        map.put("mgrMobile", mgrMobile);
        map.put("isLogo", isLogo);
        map.put("groupName", groupName);
        map.put("tagId", tagId);
        map.put("childTag", childTag);
        map.put("isPublic", isPublic);
        map.put("groupIntroduce", groupIntroduce);
        map.put("isIntroImg", isIntroImg);

        List<String> list = new ArrayList<>();
        if (isLogo) {
            list.add(fileMap.get("logo"));
            map.put(RequestManager.LOG, fileMap.get("logo"));
        }
        if (isIntroImg) {
            for (String tag : fileMap.keySet()) {
                if (!tag.equals("logo")) {
                    list.add(fileMap.get(tag));
                }
            }
        }

        HiStudentHttpUtils.postImageByOKHttp(((BaseActivity) activity), list, map, HistudentUrl.create_group_community, callBack);
    }

    //获取兴趣社群
    public static void getGroupHuoDongList(Activity activity, String groupId, int pageIndex, HttpRequestCallBack callBack,boolean isNeedLoading) {
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getGroupHuoDongList, callBack,isNeedLoading ?LoadingType.FLOWER:LoadingType.NONE);

    }

    //获取用户使用的app
    public static void getParentCategroy(Activity activity, int categoryType, int categoryLevel, String parentId, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("categoryType", categoryType);
        map.put("categoryLevel", categoryLevel);
        map.put("parentId", parentId);

        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getCategory, callBack);


    }


    //创建社群活动
    public static void postGroupHuoDong(Activity activity, String groupId, CreateHuoDongBean activityBean, HttpRequestCallBack callBack) {


        final Map<String, Object> map = new HashMap<>();
        List<String> file = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        if (activityBean.getDeleteImageId() != null && activityBean.getDeleteImageId().size() > 0) {
            for (String id : activityBean.getDeleteImageId()) {
                builder.append(id).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            map.put("excludeImgs", builder.toString());//活动名称//excludeImgs
        } else {

            map.put("excludeImgs", "");//活动类型名称
        }


        if (!StringUtil.isEmpty(activityBean.getImageUrl())) {
            File file1 = new File(activityBean.getImageUrl());
            file.add(activityBean.getImageUrl());
            map.put(RequestManager.COVER, file1.getAbsolutePath());//活动类型名称
        } else {
            map.put(RequestManager.COVER, "");//活动类型名称
        }


        if (activityBean.getImages() != null && activityBean.getImages().size() > 0) {

            for (int i = 0; i < activityBean.getImages().size(); i++) {
                map.put(RequestManager.IMAGE + i, new File(activityBean.getImages().get(i)).getAbsolutePath());
            }
            file.addAll(activityBean.getImages());
        }
        map.put("groupId", groupId);//活动名称
        map.put("name", activityBean.getHuoDongName());//活动名称
        map.put("startTime", activityBean.getStartTime());//活动开始时间
        map.put("endTime", activityBean.getEndTime());//活动截止时间
        map.put("huodongPlace", activityBean.getPalce());//活动所在地区
        map.put("latitude", activityBean.getLat());//活动个位置的经度
        map.put("longitude", activityBean.getLon());//
        map.put("introduction", activityBean.getInstruction());//活动类型id
        map.put("alarmType", activityBean.getAlarmType());//活动类型名称
        map.put("maxUserNum", activityBean.getLimitCount());//活动类型名称

        map.put("maxUserNum", activityBean.getLimitCount());//活动名称
        map.put("userCost", activityBean.getPrice());//活动名称//excludeImgs


        map.put("huodongId", StringUtil.isEmpty(activityBean.getHuoDongId()) ? "" : activityBean.getHuoDongId());

        HiStudentHttpUtils.postImageByOKHttp((BaseActivity) activity, file, map, HistudentUrl.postGroupHuoDong, callBack);
    }

    ///group/huodong/huodongInfo

    //获取社群活动详情
    public static void getGroupHuoDongDetailInfor(Activity activity, String groupId, HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        map.put("huodongId", groupId);

        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getGroupHuoDongDetailInfor, callBack);

    }

    //加入或退出社群活动
    public static void GroupHuoDongSignOrExit(final Activity activity, final String huodongId, boolean isExit, final HttpRequestCallBack callBack) {

        if (isExit) {
            ReminderHelper.getIntentce().showDialog(activity, "提示", "是否取消报名？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                    Map<String, Object> map = new HashMap<>();
                    map.put("huodongId", huodongId);
                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.groupHuoDongSignOrQuit, callBack);
                }
            }, "取消", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                }
            });

        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("huodongId", huodongId);
            HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.groupHuoDongSignOrQuit, callBack);
        }
    }

    /**
     * 获取活动成员
     *
     * @param activity
     * @param huodongId
     * @param pageIndex
     * @param callBack
     */
    public static void getGroupHuoDongMembers(final Activity activity, String huodongId, int pageIndex, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("huodongId", huodongId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getGroupHuoDongMembers, callBack);
    }


    //获取社群中的消息列表
    public static void getGroupNews(final Activity activity, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.group_invitation_list, callBack);
    }


    //获取我的社群列表
    public static void getMyGroupList(final Activity activity, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.group_list_url, callBack);
    }


    ///group/noteCreate

    //获取我的社群列表
    public static void createGroupTopic(final Activity activity, CreateTopicModel model, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        ArrayList<String> file = new ArrayList<>();
        map.put("microblogBody", model.getContent());
        map.put("longitude", model.getLon());
        map.put("latitude", model.getLat());
        map.put("location", model.getLocation());
        map.put("associateId", model.getAssocitionId());
        map.put("associatedUsers", model.getAssociation());

        if (model.getImages() != null && model.getImages().size() > 0) {
            file.addAll(model.getImages());
        }
        HiStudentHttpUtils.postImageByOKHttp((BaseActivity) activity, file, map, HistudentUrl.createGroupTopic, callBack);
    }

    //获取社群中热门的帖子
    public static void getGroupHotTopicList(Activity activity, String groupId,String keyWord, int pageIndex, int pageSize, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("groupId", groupId);
        map.put("keyword",keyWord);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getGroupHotTopicList, callBack);

    }


    public static void getSchoolClasses(Activity activity, String schoolId, int pageIndex, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("schoolId", schoolId);
        map.put("pageSize", 10);
        map.put("pageIndex", pageIndex);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getSchoolClasses, callBack);

    }

    //获取用户没有被邀请进入社群的好友列表

    public static void getGroupnoInvitationMembers(Activity activity, String groupId, String classId, String key, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("classId", classId);
        map.put("key", key);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getGroupnoInvitationMembers, callBack);

    }

    //社群邀请成员

    public static void GroupInvitationClassMates(Activity activity, String groupId, List<SimpleUserModel> userList, HttpRequestCallBack callBack) {

        if (userList != null && userList.size() > 0) {

            StringBuilder builder = new StringBuilder();
            for (SimpleUserModel model : userList) {
                builder.append(model.getUserId()).append(",");
            }

            builder.deleteCharAt(builder.length() - 1);
            Map<String, Object> map = new HashMap<>();
            map.put("groupId", groupId);
            map.put("beInvitationUserId", builder.toString());
            HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.groupInvitation, callBack);
        }
    }


    //处理社群的邀请加入消息

    /**
     * @param activity
     * @param giId     邀请id
     * @param status   1.同意 2.不同意
     * @param callBack
     */
    public static void handleGroupInvitationRequest(final Activity activity, final String giId, final int status, final HttpRequestCallBack callBack) {


        Map<String, Object> map = new HashMap<>();
        map.put("giId", giId);
        map.put("auditStatus", status);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.setInvitation, callBack);

    }


    /**
     * @param activity
     * @param applyId  申请id
     * @param isAgree  是否同意申请
     * @param callBack
     */
    //处理社群的申请加入消息
    public static void handleGroupJoinRequest(final Activity activity,
                                              final String applyId, final boolean isAgree, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "", isAgree ? "是否要同意该申请？" : "是否要拒绝该申请？", "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        }, "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new HashMap<>();
                map.put("applyId", applyId);
                map.put("isAgree", isAgree);
                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.GroupHandleJoinNew, callBack);
            }
        });
    }


    //社群消息的统一处理
    public static void handleGroupNews(Activity activity, Object item, boolean isAgree, HttpRequestCallBack callBack) {

        if (item != null) {
            if (item instanceof GroupNewsModel.InvitationListBean) {

                GroupNewsModel.InvitationListBean bean = ((GroupNewsModel.InvitationListBean) item);
                handleGroupInvitationRequest(activity, bean.getInvitationId(), isAgree ? 1 : 2, callBack);

            } else if (item instanceof GroupNewsModel.ApplyListBean) {
                GroupNewsModel.ApplyListBean bean = ((GroupNewsModel.ApplyListBean) item);
                handleGroupJoinRequest(activity, bean.getApplyId(), isAgree, callBack);

            }
        }
    }


    //设置管理员或者取消管理员
    public static void setGroupAdmin(final Activity activity, final GroupMemberBean.AdminMembersListBean teamMembe, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "", teamMembe.isIsAdmin() ?
                "确定要取消该成员的管理员身份吗？" : "确定要设置该成员的管理员身份吗？", "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        }, "确认", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new TreeMap<>();
                map.put("addOrRemove", !teamMembe.isIsAdmin());
                map.put("groupId", teamMembe.getGroupId());
                map.put("adminUserId", teamMembe.getUserId());
                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.setAdmin_group, callBack);
            }
        });

    }

    //取消社群活动
    public static void cancelGroupHuodong(Activity activity, String huodongId, HttpRequestCallBack callBack) {

        final Map<String, Object> map = new HashMap<>();
        map.put("huodongId", huodongId);
        map.put("notice", true);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.cancelGroupHuoDong, callBack);

    }

}
