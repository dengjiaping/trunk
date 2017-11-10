package com.histudent.jwsoft.histudent.commen.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.LogModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.bean.LogDetailModel;
import com.histudent.jwsoft.histudent.comment2.bean.LogDraftModel;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.SuccessCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ZhangYT on 2017/4/12.
 */

public class DataUtils {

    //根据ids获取好友信息集合
    public static List<SimpleUserModel> getUsersListByIds(Activity activity, ArrayList<String> userIds, final SuccessCallBack callBack) {

        final List<SimpleUserModel> userList = new ArrayList<>();
        if (userIds != null && userIds.size() != 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            for (String userid : userIds) {
                builder.append("\"").append(userid).append("\"").append(",");
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]");
            Map<String, Object> map = new HashMap<>();
            map.put("userIds", builder.toString());
            Log.e("userIds", userIds.toString());

            HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.get_user_infor_by_ids_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    List<SimpleUserModel> friendsBean = JSON.parseArray(result, SimpleUserModel.class);
                    if (friendsBean != null && friendsBean.size() > 0) {
                        userList.addAll(friendsBean);
                        callBack.onSuccess();
                    }
                }

                @Override
                public void onFailure(String msg) {
                    callBack.onFailed();
                }
            });
        }


        return userList;
    }


    //从关联信息集合中，获取指定名称的图片，所关联的用户信息集合
    public static ArrayList<SimpleUserModel> getRelationUsers(String PicName, List<Object> infor) {

        ArrayList<SimpleUserModel> userIds = new ArrayList<>();
        if (infor != null && infor.size() > 0 && !StringUtil.isEmpty(PicName)) {
            for (Object o : infor) {
                if (o instanceof RelationPersonModel) {
                    RelationPersonModel model = ((RelationPersonModel) o);
                    if (PicName.equals(model.getPicName())) {
                        if (model.getUsers() != null && model.getUsers().size() > 0) {
                            userIds.addAll(model.getUsers());
                            Log.e("relationPersonModelList", DataUtils.getRelationString1(infor));

                            break;
                        }
                    }
                }

            }
        }

        return userIds;
    }


    //从关联集合中，删除指定图片中，某一个用户的关联信息
    public static void deleteRelationUser(String PicName, List<Object> infor, SimpleUserModel stu) {

        if (infor != null && !StringUtil.isEmpty(PicName) && stu != null && !StringUtil.isEmpty(stu.getUserId())) {

            if (infor.size() > 0) {
                for (Object o : infor) {

                    if (o instanceof RelationPersonModel) {
                        RelationPersonModel model = ((RelationPersonModel) o);
                        if (PicName.equals(model.getPicName())) {//存在时重新设置
                            if (model.getUsers() != null && model.getUsers().size() > 0) {
                                if (model.getUsers().contains(stu)) {
                                    model.getUsers().remove(stu);

                                    Log.e("===", stu.toString());
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

    }


    //从关联集合中，删除某一图片所对应的所有关联信息
    public static void deleteRelationByPicName(String PicName, List<Object> infor) {

        if (infor != null && !StringUtil.isEmpty(PicName)) {

            if (infor.size() > 0) {
                for (Object o : infor) {

                    if (o instanceof RelationPersonModel) {
                        RelationPersonModel model = ((RelationPersonModel) o);
                        if (PicName.equals(model.getPicName())) {//存在时重新设置
                            infor.remove(model);
                            break;
                        }
                    }
                }
            }
        }

    }


    //设置某一张图片的关联用户
    public static void setRelationUsers(String PicName, List<Object> infor, ArrayList<SimpleUserModel> userIds) {

//        ArrayList<String> userIds1 = new ArrayList<>();
        if (infor != null && !StringUtil.isEmpty(PicName) && userIds != null) {

            if (infor.size() > 0) {
                for (Object o : infor) {

                    if (o instanceof RelationPersonModel) {
                        RelationPersonModel model = ((RelationPersonModel) o);
                        if (PicName.equals(model.getPicName())) {//存在时重新设置
                            model.setUsers(userIds);
//                            userIds1.addAll(userIds);
                            return;
                        }
                    }
                }
            }

            //不存在时添加
            RelationPersonModel model = new RelationPersonModel();
            model.setPicName(PicName);
            model.setUsers(userIds);
            infor.add(model);
        }
    }


    //获取默认设置的同步的班级列表
    public static void GetUserSyncClassList(Activity activity, final ArrayList<UserClassListModel> list, final BaseAdapter adapter, final boolean idDefalut) {

        String result = "";
        final String url = HistudentUrl.GetUserSyncClassList;

//        //先过获取本地保存的同步班级数据
//        if (idDefalut)
//            result = HiWorldCache.getDBData(HiCache.getInstance().getLoginUserInfo().getUserId(), url);
//        if (StringUtil.isEmpty(result)) {//没有进行网络请求并且保存到本地

            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());

            if (idDefalut) {
                map.put("isDefaultClass", "" + idDefalut);
            }

            HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);

                    //更新本地默认同步班级数据
                    if (idDefalut)
                        HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), result, url);
                    if (friendsBean != null) {
                        if (list != null) {
                            list.clear();
                            list.addAll(friendsBean);
                            if (adapter != null)
                                adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {

                }
            });

//            //直接取出来
//        } else {
//
//            Log.e("-----------------", result);
//            List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);
//
//            if (friendsBean != null) { //成功从数据库中取出正确的数据
//
//                if (friendsBean != null) {
//                    if (list != null) {
//                        list.clear();
//                        list.addAll(friendsBean);
//                        if (adapter != null)
//                            adapter.notifyDataSetChanged();
//                    }
//                }
//
//            } else {
//                //成功从数据库中取出的数据不正确，再次进行网络请求
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
//
//                if (idDefalut) {
//                    map.put("isDefaultClass", "" + idDefalut);
//                }
//
//                HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, url, new HttpRequestCallBack() {
//                    @Override
//                    public void onSuccess(String result) {
//                        List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);
//
//                        //更新本地默认同步班级数据
//                        if (idDefalut)
//                            HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), result, url);
//                        if (friendsBean != null) {
//                            if (list != null) {
//                                list.clear();
//                                list.addAll(friendsBean);
//                                if (adapter != null)
//                                    adapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//
//                    }
//                });
//            }
//
//        }

    }

    /**
     * 获取默认设置的同步的班级列表,去掉了当前班级
     */
    public static void GetUserSyncClassList(Activity activity, final ArrayList<UserClassListModel> list, final BaseAdapter adapter) {
        final boolean idDefalut = true;
        final String classId = HiCache.getInstance().getClassDetailInfo().getClassId();
        String result = "";
        final String url = HistudentUrl.GetUserSyncClassList;

//        //先过获取本地保存的同步班级数据
//        if (idDefalut)
//            result = HiWorldCache.getDBData(HiCache.getInstance().getLoginUserInfo().getUserId(), url);
//        if (StringUtil.isEmpty(result)) {//没有进行网络请求并且保存到本地

            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());

            if (idDefalut) {
                map.put("isDefaultClass", "" + idDefalut);
            }

            HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);

                    //更新本地默认同步班级数据
                    if (idDefalut)
                        HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), result, url);
                    if (friendsBean != null) {
                        if (list != null) {
                            list.clear();
                            for (int i = 0; i < friendsBean.size(); i++) {
                                if (!friendsBean.get(i).getClassId().equals(classId))
                                    list.add(friendsBean.get(i));
                            }
                            if (adapter != null)
                                adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {

                }
            });

//            //直接取出来
//        } else {
//
//            Log.e("-----------------", result);
//            List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);
//
//            if (friendsBean != null) { //成功从数据库中取出正确的数据
//
//                if (friendsBean != null) {
//                    if (list != null) {
//                        list.clear();
//                        for (int i = 0; i < friendsBean.size(); i++) {
//                            if (!friendsBean.get(i).getClassId().equals(classId))
//                                list.add(friendsBean.get(i));
//                        }
//                        if (adapter != null)
//                            adapter.notifyDataSetChanged();
//                    }
//                }
//
//            } else {
//                //成功从数据库中取出的数据不正确，再次进行网络请求
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
//
//                if (idDefalut) {
//                    map.put("isDefaultClass", "" + idDefalut);
//                }
//
//                HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, url, new HttpRequestCallBack() {
//                    @Override
//                    public void onSuccess(String result) {
//                        List<UserClassListModel> friendsBean = JSON.parseArray(result, UserClassListModel.class);
//
//                        //更新本地默认同步班级数据
//                        if (idDefalut)
//                            HiWorldCache.saveDataToDB(HiCache.getInstance().getLoginUserInfo().getUserId(), result, url);
//                        if (friendsBean != null) {
//                            if (list != null) {
//                                list.clear();
//                                list.addAll(friendsBean);
//                                if (adapter != null)
//                                    adapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//
//                    }
//                });
//            }
//
//        }

    }

    //获取默认设置的同步的班级列表
    public static void SetDefaultSyncClasses(Activity activity, ArrayList<UserClassListModel> classListModels, HttpRequestCallBack callBack) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("classIds", getSyncClassIds(classListModels).toString());
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.SetDefaultSyncClasses, callBack);

    }

    //获取默认设置的同步的班级列表

    /***
     * @param activity
     * @param idDefalut 是否是默认班级
     * @param callBack

     */
    public static void GetUserSyncClassList(final Activity activity, boolean idDefalut, HttpRequestCallBack callBack, LoadingType loadingType) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", HiCache.getInstance().getLoginUserInfo().getUserId());
        if (idDefalut) {
            map.put("isDefaultClass", "" + idDefalut);
        }
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.GetUserSyncClassList, callBack, loadingType);

    }


    //获取需要提交的相册关联学生字符串
    public static String getRelationString(List<RelationPersonModel> infor) {

        StringBuilder builder = new StringBuilder();
        if (infor != null && infor.size() > 0) {
            builder.append("[");
            for (int i = 0; i < infor.size(); i++) {
                RelationPersonModel model = infor.get(i);
                if (!StringUtil.isEmpty(getUserIsStr(model))) {
                    builder.append(getUserIsStr(model)).append(",");
                }
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]");
            return builder.toString();
        }
        return "";
    }

    public static String getRelationString1(List<Object> infor) {

        StringBuilder builder = new StringBuilder();
        if (infor != null && infor.size() > 0) {
            builder.append("[");
            for (int i = 0; i < infor.size(); i++) {
                RelationPersonModel model = ((RelationPersonModel) infor.get(i));
                if (!StringUtil.isEmpty(getUserIsStr(model))) {
                    builder.append(getUserIsStr(model)).append(",");
                }
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]");
            return builder.toString();
        }

        return "";
    }

    private static String getUserIsStr(RelationPersonModel model) {

        StringBuilder builder = new StringBuilder();

        if (!StringUtil.isEmpty(model.getPicName()) && model.getUsers() != null && model.getUsers().size() > 0) {
            builder.append("{" + "\"" + "PicName" + "\"" + ":" + "\"" + model.getPicName() + "\"" + ",").append("\"" + "UserIds" + "\"" + ":" + "[");
            for (SimpleUserModel id : model.getUsers()) {
                builder.append("\"" + id.getUserId() + "\"" + ",");
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]}");

            return builder.toString();
        }

        return "";

    }


    //获取同步班级的id的集合
    public static String getSyncClassIds(ArrayList<UserClassListModel> classList) {


        StringBuilder builder = new StringBuilder();
        if (classList != null && classList.size() > 0) {
            builder.append("[");
            for (UserClassListModel model : classList) {
                builder.append("\"").append(model.getClassId()).append("\"").append(",");
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]");
            return builder.toString();
        }

        return "";
    }


    //将权限对应的数字转换成字符串
    public static String changeNumber2QualityString(int tag) {
        String str = "公开";

        switch (tag) {
            case 1:
                str = "成员可见";
                break;

            case 2:
                str = "私密";
                break;
            default:
                str = "公开";
                break;
        }
        return str;
    }


    //权限转换成对应的数字
    public static int changeQualityString2Number(String quality) {

        int tag = 0;
        switch (quality) {
            case "成员可见":
                tag = 1;
                break;
            case "私密":
                tag = 2;
                break;
            default:
                tag = 0;
                break;
        }
        return tag;

    }


    //获取话题
    public static void getTopicList(Activity activity, String keyWord, int pageIndex, HttpRequestCallBack callBack) {///microBlog/topicList


        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);
        map.put("keyword", keyWord);


        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.GetTopicList, callBack);
    }


    //获取话题
    public static void getAtMemberList(Activity activity, HttpRequestCallBack callBack) {///microBlog/topicList
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), null, HistudentUrl.getAtMemberList, callBack);
    }


    //返回用户id构成的str
    public static String getAtUserString(List<SimpleUserModel> list) {

        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0) {
            builder.append("[");
            for (Object item : list) {
                if (item instanceof SimpleUserModel) {
                    SimpleUserModel model = ((SimpleUserModel) item);

                    builder.append("{" + "\"" + "realName" + "\"" + ":"
                            + "\"" + model.getName() + "\"" + ",")
                            .append("\"" + "userId" + "\"" + ":");
                    builder.append("\"" + model.getUserId() + "\"" + "}").append(",");
                }
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]");
            return builder.toString();
        }


        return "";
    }


    //获取需要显示的@对象的名称
    public static String getAtUserNameStr(List<SimpleUserModel> list) {

        if (list != null && list.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (SimpleUserModel m : list) {
                builder.append("@" + m.getName());
            }

            return builder.toString();
        }

        return "";
    }


    public static void setAtText(EditText et, int cursorPosition, String atContent) {

        if (ViewUtils.getMaxLength(et) > 0 && et.getText().length() + atContent.length() >= ViewUtils.getMaxLength(et)) {
            Toast.makeText(HTApplication.getInstance(), "文字长度超出范围，请先删除部分内容后再操作！", Toast.LENGTH_LONG).show();
        } else {
            Log.e("cursorPosition===>", et.getSelectionStart() + "----" + cursorPosition);
            if (et != null && cursorPosition >= 0 && !StringUtil.isEmpty(atContent)) {
                String content = et.getText().toString();
                if (content.endsWith("@")) {
                    content = content.substring(0, content.length() - 1);
                    cursorPosition -= 1;
                }
                String right = "", left = "";//记录光标前后的内容

                if (!TextUtils.isEmpty(content)) {
                    if (cursorPosition == 0) {
                        right = content;
                    } else if (cursorPosition == content.length()) {
                        left = content;

                    } else {
                        left = content.substring(0, cursorPosition);
                        right = content.substring(cursorPosition);

                    }
                }


                //除去空格
//                if (!StringUtil.isEmpty(left)) {
//                    cursorPosition = cursorPosition - (left.length() - left.trim().length());
//                    left = left.trim();
//                }

//                if (!StringUtil.isEmpty(right)) {
//                    right = right.trim();
//                }
                StringBuilder builder = new StringBuilder(left + atContent + right);
//            EmotionUtils.convertNormalStringToSpannableString(builder.toString());
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(atContent);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.RED),0,atContent.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                et.setText(EmotionUtils.convertNormalStringToSpannableString(builder.toString()));
//            setBlueText(builder.toString(), cursorPosition, cursorPosition + atContent.length(), et);
//            setBlackText(builder.toString(), cursorPosition + atContent.length(), cursorPosition + atContent.length(), et);
                et.setSelection(cursorPosition + atContent.length());//重新设置光标的位置
//                et.setFocusable(true);
            }

        }
    }

//    public static void setDeleteAction(EditText et, int cursorPosition) {
//
//    }


    //日志保存草稿到本地
    public static void LogSaveAsDraftToLocal(Activity activity, String content, SuccessCallBack callBack) {


        HiWorldCache.saveDataToDB(HiCache.getInstance().getUserDetailInfo().getUserId(), content, "LogSaveAsDraftToLocal");
    }


    //获取本地保存的日志草稿
    public static String getLocalDraft(SuccessCallBack callBack) {


        String content = HiWorldCache.getDBData(HiCache.getInstance().getUserDetailInfo().getUserId(), "LogSaveAsDraftToLocal");

        return content;
    }


    //获取网络上日志保存的草稿
    public static LogDraftModel getLogDraftDetail(Activity activity, String logId, final SuccessCallBack callBack) {
        final LogDraftModel[] logDraftModel = {new LogDraftModel()};
        if (StringUtil.isEmpty(logId)) {
            Toast.makeText(activity, "日志id不能为空！", Toast.LENGTH_LONG).show();
        } else {

            Map<String, Object> map = new HashMap<>();
            map.put("blogid", logId);

            HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getDraftDetail, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    Log.e("getLogDraftDetail", Html.fromHtml(result).toString());
                    LogDetailModel friendsBean = JSON.parseObject(result, LogDetailModel.class);
                    if (friendsBean != null) {

                        logDraftModel[0] = ExchangData(friendsBean);
                        callBack.onSuccess();
                    }
                }

                @Override
                public void onFailure(String msg) {
                    callBack.onFailed();
                }
            });
        }

        return logDraftModel[0];
    }


    //获取日志草稿列表
    public static void getDraftList(final Activity activiy, final List<LogModel.ItemsBean> list, final int pageIndex, final SuccessCallBack callBack) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("ownerId", HiCache.getInstance().getLoginUserInfo().getUserId());
        map.put("ownerType", "1");
        map.put("categoryId", "");
        map.put("keyword", "");
        map.put("isDraft", "" + true);
        map.put("pageIndex", "" + pageIndex);
        map.put("pageSize", "" + 8);
        map.put("sort", "" + 0);
        map.put("year", "" + 0);
        map.put("month", "" + 0);


        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activiy), map, HistudentUrl.getDraftList, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                LogModel logModel = JSON.parseObject(result, LogModel.class);
                if (logModel.getItems() != null) {
                    if (list != null) {
                        if (pageIndex == 0) {
                            list.clear();
                        }
                        list.addAll(logModel.getItems());
                    }
                    callBack.onSuccess();

                }
            }

            @Override
            public void onFailure(String errorMsg) {
                callBack.onFailed();
            }
        });

    }


    //删除日志草稿
    public static void deleteDraft(final Activity activiy, String blogId, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("blogId", blogId);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activiy), map, HistudentUrl.getDeleteDraft, callBack);

    }


    //将获取的日志详情数据转换数据类型，便于更新ui
    private static LogDraftModel ExchangData(LogDetailModel model) {
        LogDraftModel logDraftModel = new LogDraftModel();
        return logDraftModel;
    }

//    public static void setText(Activity activity, SimpleUserModel simpleUserModel, EditText editText, String context) {
//
//        SpannableString spannableString = new SpannableString(simpleUserModel.getName());
//        spannableString.setSpan(new ATSpan(simpleUserModel.getUserId()), 0, simpleUserModel.getName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.red))
//                , 0, simpleUserModel.getName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        editText.setText(spannableString + context);
//    }


    //检查文本内容，确定@的好友，删除多余的用户信息
    public static List<SimpleUserModel> deleteUser(String content, List<SimpleUserModel> userLists) {

        List<SimpleUserModel> list = new ArrayList<>();

        if (!StringUtil.isEmpty(content) && userLists.size() > 0) {
            if (content.contains("@")) {
                String[] strArr = content.split("\\@");

                if (strArr.length > 0) {
                    for (String str : strArr) {
                        if (!StringUtil.isEmpty(str)) {
                            for (SimpleUserModel model : userLists) {
                                /**
                                 * //名字相同，或者@后面以用户的名字开始的，都是有效的@对象
                                 */
                                String name = model.getName();
                                if ((str.startsWith(name) | str.equals(name))) {
                                    list.add(model);
                                    userLists.remove(model);
                                    break;
                                }
                            }
                        }
                    }

                    userLists.clear();
                    userLists.addAll(list);
                }
            }
        }

        return userLists;
    }


    //关键字搜索话题
    public static void SearchTopicByKeyWord(Activity activiy, String keyWord, int pageIndex, HttpRequestCallBack callBack) {


        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyWord);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activiy), map, HistudentUrl.GetTopicList, callBack);

    }


    //设置话题
    public static void setTopicText(EditText et, TopicModel topicModel, int cursorPosition) {

        if (topicModel == null) return;
        if (ViewUtils.getMaxLength(et) > 0 && et.getText().length() + topicModel.getTagName().length() >= ViewUtils.getMaxLength(et)) {
            Toast.makeText(HTApplication.getInstance(), "文字长度超出范围，请先删除部分内容后再操作！", Toast.LENGTH_LONG).show();
        } else {

            if (et != null && cursorPosition >= 0 && !StringUtil.isEmpty(topicModel.getTagName())) {
                String content = et.getText().toString();
                if (content.endsWith("#")) {
                    content = content.substring(0, content.length() - 1);
                    cursorPosition -= 1;
                }
                String right = "", left = "";//记录光标前后的内容

                if (!TextUtils.isEmpty(content)) {
                    if (cursorPosition == 0) {
                        right = content;
                    } else if (cursorPosition == content.length()) {
                        left = content;
                    } else {
                        left = content.substring(0, cursorPosition);
                        right = content.substring(cursorPosition);
                    }
                }

                //除去空格
//                if (!StringUtil.isEmpty(left)) {
//                    left = left.trim();
//                }
//
//                if (!StringUtil.isEmpty(right)) {
//                    right = right.trim();
//                }
                StringBuilder builder = new StringBuilder(left + "#" + topicModel.getTagName() + "#" + right);
                et.setText(EmotionUtils.convertNormalStringToSpannableString(builder.toString()));
                content = et.getText().toString();

                //新话题光标定位在中间，话题内容为编辑
                if (StringUtil.isEmpty(topicModel.getTagId())) {
                    et.setSelection(cursorPosition + 1, cursorPosition + topicModel.getTagName().length() + 1);

                    //非新话题光标等位定位在话题后，可以连续输入
                } else {
                    et.setSelection(cursorPosition + topicModel.getTagName().length() + 2);
                }

            }
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    public static void setColorText(String content, int start, int end, TextView editText, int colorRes) {

        Spannable word = new SpannableString(content);
        word.setSpan(new RelativeSizeSpan((float) 1), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(colorRes), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        editText.setText(word);

    }


    public static void changeAtUserColor(final List<SimpleUserModel> userModels, final EditText et, final Activity activity) {


        if (userModels == null || userModels.size() == 0)
            return;

        List<SimpleUserModel> listTem = new ArrayList<>();

        String content = StringUtil.isEmpty(et.getText().toString()) ? "" : et.getText().toString();
        SpannableString ss = new SpannableString(content);
        boolean isContain = false;


        String[] names = content.split("@");
        if (names != null && names.length > 0) {
            int index = 0;
            for (String name1 : names) {

                if (StringUtil.isEmpty(name1)) continue;

                for (SimpleUserModel m : userModels) {

                    String name = m.getName();
                    if (!StringUtil.isEmpty(name1) && name1.contains(name)) {
                        name = "@" + name;
                        final Bitmap bmp = getNameBitmap(name, activity);

                        // 这里会出现删除过的用户，需要做判断，过滤掉
                        if (content.indexOf(name, index) >= 0
                                && (content.indexOf(name, index) + name.length()) <= content.length()) {

                            // 把取到的要@的人名，用DynamicDrawableSpan代替
                            ss.setSpan(
                                    new DynamicDrawableSpan(
                                            DynamicDrawableSpan.ALIGN_BASELINE) {

                                        @Override
                                        public Drawable getDrawable() {
                                            // TODO Auto-generated method stub
                                            BitmapDrawable drawable = new BitmapDrawable(
                                                    activity.getResources(), bmp);
                                            drawable.setBounds(0, 0,
                                                    bmp.getWidth(),
                                                    bmp.getHeight());

                                            return drawable;
                                        }
                                    }, content.indexOf(name, index),
                                    content.indexOf(name, index) + name.length(),
                                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        break;
                    }

                }
                index = index + name1.length() + 1;
                et.setTextKeepState(ss);

            }
        }

    }

    private static Bitmap getNameBitmap(String name, Activity activity) {

        /* 把@相关的字符串转换成bitmap 然后使用DynamicDrawableSpan加入输入框中 */

        Paint paint = new Paint();
        paint.setColor(activity.getResources().getColor(R.color.green_color));
        paint.setAntiAlias(true);
        paint.setTextSize(activity.getResources().getDimensionPixelSize(R.dimen.text_size_15));
        Rect rect = new Rect();

        paint.getTextBounds(name, 0, name.length(), rect);

        // 获取字符串在屏幕上的长度
        int width = (int) (paint.measureText(name));

        final Bitmap bmp = Bitmap.createBitmap(width, rect.height(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        canvas.drawText(name, rect.left, rect.height() - rect.bottom, paint);

        return bmp;
    }


}
