package com.histudent.jwsoft.histudent.info.persioninfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.uikit.session.SessionHelper;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/21.
 */

public class PersionHelper {

    private static PersionHelper helper;
    private int pageSize = 8;
    private TopMenuPopupWindow menuWindow;

    private PersionHelper() {
    }

    public static synchronized PersionHelper getInstance() {

        if (helper == null) {
            helper = new PersionHelper();
        }
        return helper;
    }

    /**
     * 显示标题菜单
     *
     * @param activity
     * @param handler
     * @param what
     */
    public void showPesonMenue(final BaseActivity activity, final PictureTailorHelper helper, final Handler handler, final int what) {
        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();
        final CurrentUserDetailInfoModel userDetailInfo = HiCache.getInstance().getUserDetailInfo();
        final boolean flag_attention = userDetailInfo.isIsFollowed();
        final boolean flag_black = userDetailInfo.isIsStopped();
        final String userId = userDetailInfo.getUserId();

        if (SystemUtil.isOneselfIn(userId)) {
            list_name.add("更换封面");
            list_color.add(Color.rgb(51, 51, 51));

        } else {
            list_name.add("举报");
            list_name.add(flag_black ? "取消黑名单" : "加入黑名单");
            list_name.add(flag_attention ? "取消关注" : "关注");
            list_color.add(Color.rgb(51, 51, 51));
            list_color.add(Color.rgb(51, 51, 51));
            list_color.add(Color.rgb(255, 88, 88));
        }
        menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:

                        if (SystemUtil.isOneselfIn(userId)) {
                            activity.checkTakePhotoPermission(new IPermissionCallBackListener() {
                                @Override
                                public void doAction() {
                                    helper.showGetPhotoPictureListDialog(activity);
                                }
                            });
                        } else {
                            ReportActivity.start(activity, userId, ReportType.OTHER);
                        }

                        break;
                    case R.id.btn_02:

                        blackSomebody(activity, flag_black, userId, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                userDetailInfo.setIsStopped(!flag_black);
                                HiCache.getInstance().setUserDetailInfo(userDetailInfo);
                                handler.sendEmptyMessage(what);
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });

                        break;
                    case R.id.btn_03:

                        attention(activity, flag_attention ? 0 : 1, userId, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                userDetailInfo.setIsFollowed(!flag_attention);
                                HiCache.getInstance().setUserDetailInfo(userDetailInfo);
                                handler.sendEmptyMessage(what);
                            }

                            @Override
                            public void onFailure(String msg) {

                            }
                        });
                        break;
                    default:
                        break;
                }
            }

        }, list_name, list_color, false);
        menuWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 发送消息
     *
     * @param activity
     */
    public void sendMsg(final BaseActivity activity) {

        CurrentUserDetailInfoModel currentUserModel = HiCache.getInstance().getUserDetailInfo();
        final String imId = SystemUtil.UserIdToIMAccont(currentUserModel.getUserId());

        if (!TextUtils.isEmpty(imId)) {

            if (currentUserModel.isIsBindIM()) {

                gotoChatView(activity, imId);

            } else {
                Map<String, Object> map = new TreeMap();
                map.put("userId", currentUserModel.getUserId());

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.registIMaccount, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        gotoChatView(activity, imId);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

            }
        }
    }

    /**
     * p2p发送消息
     *
     * @param activity
     * @param imId
     */
    private void gotoChatView(final BaseActivity activity, final String imId) {
        NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(imId);
        if (userInfo != null) {
            SessionHelper.startP2PSession(activity, imId);
        } else {

            NimUserInfoCache.getInstance().getUserInfoFromRemote(imId, new RequestCallback<NimUserInfo>() {
                @Override
                public void onSuccess(NimUserInfo nimUserInfo) {
                    SessionHelper.startP2PSession(activity, imId);
                }

                @Override
                public void onFailed(int i) {

                }

                @Override
                public void onException(Throwable throwable) {

                }
            });
        }
    }

    /**
     * 获取用户信息(详细)
     */
    public void getUserInfo(BaseActivity activity, String userId, final Handler handler, final int what) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", userId);
        map.put("fullInfo", true);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getUserInfo, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                CurrentUserDetailInfoModel userModel = DataParser.getUserinfoParser(result);
                if (userModel != null) {
                    HiCache.getInstance().setUserDetailInfo(userModel);
                }
                handler.sendEmptyMessage(what);
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.sendEmptyMessage(-1);
            }
        },LoadingType.NONE);
    }

    /**
     * 获取用户信息(简单)
     */
    public void getEssayUserInfo(BaseActivity activity, String userId, final Handler handler, final int what) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", userId);
        map.put("fullInfo", false);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getUserInfo, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                CurrentUserDetailInfoModel userModel = DataParser.getUserinfoParser(result);
                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = userModel;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.sendEmptyMessage(-1);
            }
        });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(BaseActivity activity, String userId, final HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", userId);
        map.put("fullInfo", true);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getUserInfo, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                CurrentUserDetailInfoModel userModel = DataParser.getUserinfoParser(result);
                if (userModel != null) {
                    HiCache.getInstance().setUserDetailInfo(userModel);
                }
                if (callBack != null)
                    callBack.onSuccess(result);
            }

            @Override
            public void onFailure(String errorMsg) {
                callBack.onFailure(errorMsg);
            }
        },LoadingType.NONE);
    }

    /**
     * 关注/取消关注
     *
     * @param flag
     * @param userId
     * @param callBack
     */
    public void attention(Context context, int flag, String userId, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("action", flag + "");
        map.put("objectId", userId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map, HistudentUrl.doAction_url, callBack);

    }

    /**
     * 个人时间轴
     *
     * @param activity
     * @param timeCursor
     */
    public void getPersionTimeline(BaseActivity activity, int timeCursor, LoadingType flag, HttpRequestCallBack callBack) {
        CurrentUserDetailInfoModel userDetailInfoModel = HiCache.getInstance().getUserDetailInfo();
        if (userDetailInfoModel == null) return;
        final Map<String, Object> map = new TreeMap<>();
        map.put("userId", userDetailInfoModel.getUserId());
        map.put("timeCursor", timeCursor);
        map.put("pageSize", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getTimeThingsInfo_url, callBack, flag);
    }

    /**
     * 个人时间轴
     *
     * @param activity
     * @param timeCursor
     * @param handler
     * @param what
     */
    public void getPersionTimeline(BaseActivity activity, int timeCursor, final Handler handler, final int what, LoadingType flag) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getUserDetailInfo().getUserId());
        map.put("timeCursor", timeCursor);
        map.put("pageSize", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getTimeThingsInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        }, flag);
    }

    /**
     * 获取随记数据
     *
     * @param activity
     * @param timeCursor
     * @param handler
     * @param what
     */
    public void getPersionEssays(BaseActivity activity, int timeCursor, final Handler handler, final int what, LoadingType flag) {

        Map<String, Object> map = new TreeMap<>();
        map.put("userId", HiCache.getInstance().getUserDetailInfo().getUserId());
        map.put("timeCursor", timeCursor);
        map.put("pagesize", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getEsseyInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {
                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);
            }
        }, flag);
    }

    /**
     * 删除随记
     *
     * @param activity
     * @param essayId
     * @param handler
     * @param what
     */
    public void deletePersionEssay(BaseActivity activity, String essayId, final Handler handler, final int what, final int positon) {

        Map<String, Object> map = new TreeMap<>();
        map.put("microblogid", essayId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.deleteMicroBlog, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.arg1 = positon;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        });

    }


    /**
     * 获取日志数据
     *
     * @param activity
     * @param timeCursor
     * @param handler
     * @param what
     */
    public void getPersionBlogs(BaseActivity activity, int timeCursor, final Handler handler, final int what, boolean flag,boolean isNeedLoading) {

        Map<String, Object> map = new TreeMap<>();
        map.put("timeCursor", timeCursor);
        map.put("ownerType", 1);
        map.put("ownerId", HiCache.getInstance().getUserDetailInfo().getUserId());
        map.put("pageSize", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getBlogInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        },isNeedLoading ?LoadingType.FLOWER:LoadingType.NONE);

    }

    /**
     * 删除日志
     *
     * @param activity
     * @param blogId
     * @param handler
     * @param what
     */
    public void deletePersionBlog(BaseActivity activity, String blogId, final Handler handler, final int what, final int positon) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("blogid", blogId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.deleteBlog_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.arg1 = positon;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        });

    }


    /**
     * 获取相册列表
     *
     * @param activity
     * @param pageIndex
     * @param handler
     * @param what
     */
    public void getPersionAibumLists(BaseActivity activity, int pageIndex, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("pageIndex", pageIndex);
        map.put("userId", HiCache.getInstance().getUserDetailInfo().getUserId());
        map.put("pageSize ", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getlbumInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        });
    }

    /**
     * 获取最近照片
     *
     * @param activity
     * @param pageIndex
     * @param handler
     * @param what
     */
    public void getPersionRecentlyPictures(BaseActivity activity, int pageIndex, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("pageIndex", pageIndex);
        map.put("userId", HiCache.getInstance().getUserDetailInfo().getUserId());
        map.put("pageSize ", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getRecentlyPictureInfo_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        });

    }

    /**
     * 关注/取消关注
     *
     * @param activity
     * @param flag
     * @param userId
     * @param handler
     * @param what
     */
    public void attention(BaseActivity activity, final boolean flag, String userId, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("action", flag ? 0 : 1);
        map.put("objectId", userId);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.doAction_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = !flag;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String errorMsg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = errorMsg;
                handler.sendMessage(message);

            }
        });

    }

    /**
     * 拉入/取消黑名单
     *
     * @param activity
     * @param flag
     * @param userId
     * @param handler
     * @param what
     */
    public void blackSomebody(BaseActivity activity, boolean flag, String userId, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("isCancel", flag);
        map.put("userId", userId);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.blackSomebody, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {


                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String errorMsg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = errorMsg;
                handler.sendMessage(message);

            }
        });

    }

    /**
     * 拉入/取消黑名单
     *
     * @param flag
     * @param userId
     * @param callBack
     */
    public void blackSomebody(BaseActivity activity, boolean flag, String userId, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("isCancel", flag);
        map.put("userId", userId);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.blackSomebody, callBack);

    }

    /**
     * 封面点赞
     *
     * @param activity
     * @param userId
     * @param handler
     * @param what
     */
    public void addLikeInCover(BaseActivity activity, String userId, final Handler handler, final int what) {
        Map<String, Object> map = new TreeMap<>();
        map.put("doOrUndo", true);
        map.put("objectType", "0");
        map.put("objectId", userId);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.praise_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = what;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        },LoadingType.NONE);
    }

    /**
     * 更换个人封面
     *
     * @param activity
     * @param handler
     * @param what
     */
    public void setPersionCover(final BaseActivity activity, final Handler handler, final int what, String path) {
        List<String> urls = new ArrayList<>();
        urls.add(path);
        HiStudentHttpUtils.postImageByOKHttp(activity, urls, null, HistudentUrl.exchangePersionCover_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(activity, "上传成功!", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(result);
                    String path = object.optString("imgUrl");
                    HiCache.getInstance().getUserDetailInfo().setAppPageBanner(path);
                    Message message = handler.obtainMessage();
                    message.what = what;
                    message.obj = path;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String msg) {
                Message message = handler.obtainMessage();
                message.what = -1;
                message.obj = msg;
                handler.sendMessage(message);

            }
        });
    }

    /**
     * 上传头像并展示于控件
     *
     * @param activity
     * @param callBack
     */
    public void setPicToView(BaseActivity activity, String path, HttpRequestCallBack callBack) {
        List<String> urls = new ArrayList<>();
        urls.add(path);
        HiStudentHttpUtils.postImageByOKHttp(activity, urls, null, HistudentUrl.uploadAvatar, callBack);
    }

}
