package com.histudent.jwsoft.histudent.body.myclass.helper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.BodyActivity;
import com.histudent.jwsoft.histudent.body.find.bean.ClassMemberBean;
import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.message.activity.ClassUtils;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.message.model.ClassTemberModel;
import com.histudent.jwsoft.histudent.body.mine.activity.AlbumDetailActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.AlbumsCenterActivity;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.AlbumAuthorityModel;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.RequestManager;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.StarBar;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.AutoScrollImageView;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.commen.utils.SystemUtil.getScreenWind;


/**
 * Created by liuguiyu-pc on 2016/12/23.
 */

public class ClassHelper {

    private static int pageSize = 8;


    private static TopMenuPopupWindow menuWindow;
    public static Handler handle;

    /**
     * 获取班级信息
     *
     * @param activity
     * @param classId
     * @param callBack
     */
    public static void getClassInfo(BaseActivity activity, String classId, final HttpRequestCallBack callBack, LoadingType loadingType) {
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassInfor, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if (loadingType != LoadingType.CLASS) {
                    ClassModel classModel = JSON.parseObject(result, ClassModel.class);
                    if (classModel != null) {
                        HiCache.getInstance().setClassDetailInfo(classModel);
                    }
                }
                callBack.onSuccess(result);


            }

            @Override
            public void onFailure(String errorMsg) {
                callBack.onFailure(errorMsg);
            }
        }, loadingType);
    }


    /**
     * 获取班级信息
     *
     * @param activity
     * @param classId
     * @param callBack
     */
    public static void getClassInfo(BaseActivity activity, String classId, final HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassInfor, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                ClassModel classModel = JSON.parseObject(result, ClassModel.class);
                if (classModel != null) {
                    HiCache.getInstance().setClassDetailInfo(classModel);
                }
                callBack.onSuccess(result);
            }

            @Override
            public void onFailure(String errorMsg) {
                callBack.onFailure(errorMsg);
            }
        });
    }

    /**
     * 获取班级信息
     *
     * @param activity
     * @param classId
     * @param handler
     * @param what
     */
    public static void getClassInfo(BaseActivity activity, String classId, final Handler handler, final int what) {
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassModel_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                ClassModel classModel = JSON.parseObject(result, ClassModel.class);
                if (classModel != null) {
                    HiCache.getInstance().setClassDetailInfo(classModel);
                    handler.sendEmptyMessage(what);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.sendEmptyMessage(-1);
            }
        });
    }

    /**
     * 显示班级菜单
     *
     * @param activity
     */
    public static void showClassMenue(final BaseActivity activity, final PictureTailorHelper clippHelper) {

        final ClassModel classModel = HiCache.getInstance().getClassDetailInfo();
        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();

        if (classModel.isIsClassMaker()) {
            list_name.add("更换封面");
            list_name.add("转让班级");
            list_color.add(Color.rgb(51, 51, 51));
            list_color.add(Color.rgb(51, 51, 51));
        } else if (classModel.isIsAdmin()) {
            list_name.add("更换封面");
            list_name.add("退出班级");
            list_color.add(Color.rgb(51, 51, 51));
            list_color.add(Color.rgb(51, 51, 51));
        } else {
            list_name.add("退出班级");
            list_color.add(Color.rgb(51, 51, 51));
        }

        menuWindow = new TopMenuPopupWindow(activity, new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:

                        if (classModel.isIsClassMaker()) {
                            activity.checkTakePhotoPermission(new IPermissionCallBackListener() {
                                @Override
                                public void doAction() {
                                    clippHelper.showGetPhotoPictureListDialog(activity);
                                }
                            });

                        } else if (classModel.isIsAdmin()) {
                            activity.checkTakePhotoPermission(new IPermissionCallBackListener() {
                                @Override
                                public void doAction() {
                                    clippHelper.showGetPhotoPictureListDialog(activity);
                                }
                            });
                        } else {
                            if (TextUtils.isEmpty(classModel.getClassId())) return;
                            exitClass((BaseActivity) activity, classModel.getClassId(), HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                    BodyActivity.start(activity);

                                }

                                @Override
                                public void onFailure(String msg) {

                                }
                            });
                        }

                        break;
                    case R.id.btn_02:

                        if (classModel.isIsClassMaker()) {
                            ClassUtils.showExchangeClassDialog(activity, classModel);
                        } else if (classModel.isIsAdmin()) {
                            if (TextUtils.isEmpty(classModel.getClassId())) return;
                            exitClass((BaseActivity) activity, classModel.getClassId(), HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                    BodyActivity.start(activity);

                                }

                                @Override
                                public void onFailure(String msg) {

                                }
                            });

                        }

                        break;
                    default:
                        break;
                }
            }
        }, list_name, list_color, false);
        menuWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 退出班级
     *
     * @param classModelId
     */
    private static void exit(String classModelId) {

    }

    /**
     * 退出(移除，添加)班级
     *
     * @param activity
     * @param classId
     * @param userId
     * @param callBack
     */
    public static void exitClass(final BaseActivity activity, final String classId, final String userId, final HttpRequestCallBack callBack) {
        if (TextUtils.isEmpty(classId) || TextUtils.isEmpty(userId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        map.put("userId", userId);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.removeUser, callBack);
    }

    /**
     * 移出(移除，添加)班级
     *
     * @param activity
     * @param classId
     * @param userId
     * @param callBack
     */
    public static void removeClass(final BaseActivity activity, final String classId, final String userId, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "移出班级", "是否移出班级？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                Map<String, Object> map = new TreeMap<>();
                map.put("addOrRemove", false + "");
                map.put("classId", classId);
                JSONArray array = new JSONArray();
                array.put(userId);
                map.put("memberUserIds", array.toString());
                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.setMembers, callBack);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });
    }


    /**
     * 设置班级是否可以申请
     *
     * @param activity
     * @param classId
     * @param flag
     * @param callBack
     */
    public static void setClassApplyAudit(final BaseActivity activity, final boolean flag, final String classId, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, null, flag ? "开启开关后，新的班级成员可通过班级代码或班主任手机号加入！" : "关闭开关后，新的班级成员将无法加入该班级！", "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
            }
        }, "确认", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                Map<String, Object> map = new TreeMap<>();
                map.put("isAllowApply", flag);
                map.put("classId", classId);
                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.setClassApplyAudit, callBack);
            }
        });
    }


    /**
     * 转让班级
     *
     * @param acceptUserMobile
     */
    public static void exchangeClassOwner(final BaseActivity activity, ClassModel classModel, String acceptUserMobile, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classModel.getClassId());
        map.put("acceptUserMobile", acceptUserMobile);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.exchangeClassOwner, callBack);

    }

    /**
     * 加入班级
     *
     * @param classNum
     */
    public static void joinClassOwner(final BaseActivity activity, String classNum, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classNum", classNum);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.applyClass_url, callBack);

    }

    /**
     * 解散班级
     */
    public static void deleteClassOwner(final BaseActivity activity, ClassModel classModel, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classModel.getClassId());
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.classDelete, callBack);

    }

    /**
     * 设置（取消设置）管理员
     *
     * @param activity
     * @param callBack
     */
    public static void setClassAdmin(final BaseActivity activity, final ClassTemberModel teamMembe, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", teamMembe.isIsAdmin() == true ?
                "取消管理员？" : "设置为管理员？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new TreeMap<>();
                map.put("addOrRemove", !teamMembe.isIsAdmin() + "");
                map.put("classId", teamMembe.getClassId());
                map.put("adminUserId", teamMembe.getUserId());

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.setClassAdmin, callBack);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {


            }
        });
    }

    /**
     * 设置（取消设置）管理员
     *
     * @param activity
     * @param callBack
     */
    public static void setClassAdmin(final BaseActivity activity, final ClassMemberBean.StuClassMembersBean teamMembe, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", teamMembe.isIsAdmin() == true ?
                "取消管理员？" : "设置为管理员？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new TreeMap<>();
                map.put("addOrRemove", !teamMembe.isIsAdmin() + "");
                map.put("classId", teamMembe.getClassId());
                map.put("adminUserId", teamMembe.getUserId());

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.setClassAdmin, callBack);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {


            }
        });
    }

    /**
     * 设置（取消设置）管理员
     *
     * @param activity
     * @param callBack
     */
    public static void setClassAdmin(final BaseActivity activity, final ClassMemberBean.TeaClassMembersBean teamMembe, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", teamMembe.isIsAdmin() == true ?
                "取消管理员？" : "设置为管理员？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new TreeMap<>();
                map.put("addOrRemove", !teamMembe.isIsAdmin() + "");
                map.put("classId", teamMembe.getClassId());
                map.put("adminUserId", teamMembe.getUserId());

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.setClassAdmin, callBack);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {


            }
        });
    }

    /**
     * 重置成员密码
     *
     * @param activity
     * @param userId
     * @param callBack
     */
    public static void resetPsw(final BaseActivity activity, final String userId, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否重置密码？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new TreeMap<>();
                map.put("userId", userId);

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.reSetClassPwd, callBack);

            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {


            }
        });

    }


    /**
     * 获取班级动态数据
     *
     * @param activity
     * @param timeCursor
     * @param handler
     * @param what
     */
    public static void getClassActions(BaseActivity activity, int timeCursor, final Handler handler, final int what, LoadingType flag) {

        final Map<String, Object> map = new TreeMap<>();
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
        map.put("timeCursor", timeCursor);
        map.put("pageSize", "8");

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassAction_url, new HttpRequestCallBack() {
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
     * 获取班级日志数据
     *
     * @param activity
     * @param index
     * @param handler
     * @param what
     */
    public static void getClassBlogs(BaseActivity activity, int index, final Handler handler, final int what, LoadingType flag) {
        Map<String, Object> map = new TreeMap<>();
        map.put("pageIndex", index);
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
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
        }, flag);
    }


    /**
     * 获取班级公告
     *
     * @param activity
     * @param handler
     * @param what
     */
    public static void getClassNotices(BaseActivity activity, final Handler handler, final int what, LoadingType flag) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassNoticeList_url, new HttpRequestCallBack() {
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
     * 拨打电话
     *
     * @param activity
     * @param phoneNum
     */
    public static void callPhone(final BaseActivity activity, final String phoneNum) {
        activity.checkCallPermission(new IPermissionCallBackListener() {
            @Override
            public void doAction() {
                activity.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNum)));
            }
        });
    }

    /**
     * 获取班级成员数据
     *
     * @param activity
     * @param handler
     * @param what
     */
    public static void getClassMembers(BaseActivity activity, final Handler handler, final int what) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassTeamber_url, new HttpRequestCallBack() {
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
     * 获取班级成员数据
     *
     * @param activity
     */
    public static void getClassMembers(BaseActivity activity, String keyName, HttpRequestCallBack callBack) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
        map.put("keyName", keyName);
        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.getClassTeamber_url, callBack);

    }

    /**
     * 请求加入班级
     *
     * @param activity
     * @param handler
     * @param what_T
     * @param what_F
     */
    public static void postJoinClass(BaseActivity activity, final Handler handler, final int what_T, final int what_F) {

        Map<String, Object> map = new HashMap<>();
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.applyclass_byQRcode, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                handler.sendEmptyMessage(what_T);
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.sendEmptyMessage(what_F);

            }
        });

    }

    /**
     * 通过手机号添加老师或者学生为班级成员
     *
     * @param activity
     * @param classId
     * @param mobile
     * @param userType
     * @param name
     * @param callBack
     */
    public static void addMembers(BaseActivity activity, String classId, String mobile, int userType, String name, HttpRequestCallBack callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);
        map.put("mobile", mobile);
        map.put("userType", userType);
        map.put("realName", name);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.classSetMemberByPhone, callBack);

    }

    /**
     * 更换班级封面
     *
     * @param activity
     * @param classId
     * @param handler
     * @param what
     */
    public static void setClassCover(final BaseActivity activity, String path, final String classId, final Handler handler, final int what) {
        List<String> urls = new ArrayList<>();
        urls.add(path);
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postImageByOKHttp(activity, urls, map, HistudentUrl.exchangeClassCover_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(activity, "上传成功!", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject object = new JSONObject(result);
                    String url = object.optString("coverUrl");
                    handler.sendEmptyMessage(what);

                } catch (Exception e) {
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
     * 班级主页界面更新
     *
     * @param activity
     * @param view
     * @param classModel
     */
    public static void updateUi(Activity activity, View view, ClassModel classModel) {

        if (classModel != null && view != null && activity != null) {

            HiStudentHeadImageView classLog = view.findViewById(R.id.class_log);
            TextView className = view.findViewById(R.id.class_name);
            TextView classId = view.findViewById(R.id.class_id);
            TextView classMaster = view.findViewById(R.id.class_master);
            HiStudentHeadImageView iv_heart = view.findViewById(R.id.iv_heart);
            TextView redDot = view.findViewById(R.id.tv_heart);
            StarBar bar = view.findViewById(R.id.rating_bar);

//            MyImageLoader.getIntent().displayNetImageWithAnimation(activity, classModel.getClassLogo(), classLog, R.mipmap.default_image);
//            MyImageLoader.getIntent().displayNetImageWithAnimation(activity, classModel.getClassActiviteUserAvatar(), iv_heart, R.mipmap.default_image);

            CommonGlideImageLoader.getInstance().displayNetImage(activity, classModel.getClassLogo(),
                    classLog, ContextCompat.getDrawable(activity, R.mipmap.default_class_style));

            CommonGlideImageLoader.getInstance().displayNetImage(activity, classModel.getClassActiviteUserAvatar(),
                    iv_heart, PhotoManager.getInstance().getDefaultPlaceholderResource());

            className.setText(classModel.getSchoolName() + classModel.getGradeName() + classModel.getCName());
            classId.setText(classModel.getClassNum() + "");
            bar.setStarMark(classModel.getClassRank());

            classMaster.setText(classModel.getClassUserRealName());
            classMaster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PersonCenterActivity.start(activity, classModel.getClassUserId());
                }
            });
            if (!StringUtil.isEmpty(classModel.getClassActiviteUserAvatar())) {
                redDot.setVisibility(View.VISIBLE);
                iv_heart.setVisibility(View.VISIBLE);
            } else {
                redDot.setVisibility(View.GONE);
                iv_heart.setVisibility(View.GONE);
            }
        }
    }


    //班级主页的动画

    public static void showPlayAnimation(final Activity activity, AutoScrollImageView image, final List<ClassModel.BannerListBean> list) {
        image.stopAutoScroll();
        image.setVisibility(View.GONE);
        if (list != null && list.size() > 0) {
            String images[] = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                images[i] = list.get(i).getFilePath();
            }

            image.setTextList(images);
            image.setDuring(3000);
            image.startAutoScroll();
            image.setVisibility(View.VISIBLE);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //gotoAlbumsCenter(activity, HiCache.getIntence().getClassDetailInfo().getClassId(), ActionTypeEnum.CLASS, 11);
                }
            });
        }

    }

    /**
     * 班级主页相册动画
     *
     * @param list     动画图片集合
     * @param activity
     */
    public static void startAimation(final ImageView iv, final List<ClassModel.BannerListBean> list, final Activity activity) {
        iv.clearAnimation();
        if (list != null) {
            final int[] position = new int[1];
            final String[] filePath = new String[1];

            if (handle != null)
                handle.removeMessages(888);

            handle = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    if (position[0] == list.size() - 1) {
                        position[0] = 0;
                    } else {
                        position[0]++;
                    }


                    final ClassModel.BannerListBean bean = list.get(position[0]);
//                        if (bean.getFilePath().equals(filePath[0])&&list.size()!=1)
//                            return;
                    filePath[0] = bean.getFilePath();
                    if (!StringUtil.isEmpty(bean.getFilePath())) {
//                        MyImageLoader.getIntent().displayNetImageWithAnimation(activity, filePath[0], iv, R.mipmap.default_image);
                        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, filePath[0],
                                iv, PhotoManager.getInstance().getDefaultPlaceholderResource());
                    } else {
                        iv.setImageResource(R.drawable.default_placeholder_style_1);
                    }

                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到相册
                            gotoAlbum(activity, list.get(position[0]).getAlbumId());
                        }
                    });

                    playAnimation(activity, iv);
                    sendEmptyMessageDelayed(888, 7500);
                }
            };

            if (list.size() > 0)
                handle.sendEmptyMessageDelayed(888, 500);

        }
    }

    /**
     * 获取班级活动列表
     *
     * @param activity
     * @param pageIndex
     * @param classId
     * @param callBack
     */
    public static void getClassHuoDongList(final Activity activity, int pageIndex, String classId, HttpRequestCallBack callBack, boolean isNeedLoading) {

        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getClassHuoDongList, callBack, isNeedLoading ? LoadingType.FLOWER : LoadingType.NONE);

    }

    /**
     * 创建班级活动
     *
     * @param activity
     * @param classId
     * @param activityBean
     * @param callBack
     */
    public static void postHuoDong(Activity activity, String classId, CreateHuoDongBean activityBean, HttpRequestCallBack callBack) {


        final Map<String, Object> map = new HashMap<>();
        List<String> file = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (activityBean.getDeleteImageId() != null && activityBean.getDeleteImageId().size() > 0) {

            for (String id : activityBean.getDeleteImageId()) {
                builder.append(id).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            map.put("excludeImgs", builder.toString());//活动类型名称
        } else {
            map.put("excludeImgs", "");//活动类型名称
        }

        if (!StringUtil.isEmpty(activityBean.getImageUrl())) {
            File file1 = new File(activityBean.getImageUrl());
            file.add(activityBean.getImageUrl());
            map.put(RequestManager.COVER, file1.getAbsolutePath());//活动类型名称
        }


        if (activityBean.getImages() != null && activityBean.getImages().size() > 0) {
            for (int i = 0; i < activityBean.getImages().size(); i++) {
                map.put(RequestManager.IMAGE + i, new File(activityBean.getImages().get(i)).getAbsolutePath());
            }
            //加入所有的活动介绍文字
            file.addAll(activityBean.getImages());

            Log.e("-----", file.size() + "");
        }

        map.put("classId", classId);//活动名称
        map.put("name", activityBean.getHuoDongName());//活动名称
        map.put("startTime", activityBean.getStartTime());//活动开始时间
        map.put("endTime", activityBean.getEndTime());//活动截止时间
        map.put("huoDongPlace", activityBean.getPalce());//活动所在地区
        map.put("latitude", activityBean.getLat());//活动个位置的经度
        map.put("longitude", activityBean.getLon());//
        map.put("introduction", activityBean.getInstruction());//活动类型id
        map.put("alarmType", activityBean.getAlarmType());//活动类型名称
        map.put("huodongId", StringUtil.isEmpty(activityBean.getHuoDongId()) ? "" : activityBean.getHuoDongId());

        HiStudentHttpUtils.postImageByOKHttp((BaseActivity) activity, file, map, HistudentUrl.createClassHuoDong, callBack);
    }

    /**
     * 进入具体某一个相册入口
     *
     * @param albumId
     * @param
     */
    public static void gotoAlbum(final Activity activity, final String albumId) {
        Map<String, Object> map = new TreeMap<>();
        map.put("albumId", albumId);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getAlbumInfo, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final AlbumInfoModel model = JSON.parseObject(result, AlbumInfoModel.class);

                switch (model.getOwnerType()) {

                    case 1:

                        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(model.getOwnerId())) {
                            AlbumDetailActivity.start(activity, model, ActionTypeEnum.OWNER, new AlbumAuthorityModel(true, model.getOwnerId()));
                        } else {
                            AlbumDetailActivity.start(activity, model, ActionTypeEnum.OWNER, new AlbumAuthorityModel());
                        }
                        break;

                    case 2:

                        //班级相册
                        AlbumDetailActivity.start(activity, model, ActionTypeEnum.CLASS,
                                new AlbumAuthorityModel(model.isIsEditAuth(), model.isIsEditAuth(), model.isIsUploadAuth()));
                        break;

                    case 3:
                        AlbumDetailActivity.start(activity, model, ActionTypeEnum.GROUP,
                                new AlbumAuthorityModel(model.isIsEditAuth(), model.isIsEditAuth(), model.isIsUploadAuth()));

                        break;
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 获取班级活动详情
     *
     * @param activity
     * @param huodongId
     * @param callBack
     */
    public static void getClassHuoDongInfor(final Activity activity, String huodongId, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("huodongId", huodongId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongInfor, callBack);

    }

    /**
     * 获取班级活动成员
     *
     * @param activity
     * @param huodongId
     * @param pageIndex
     * @param callBack
     */
    public static void getClassHuoDongMembers(final Activity activity, String huodongId, int pageIndex, HttpRequestCallBack callBack) {

        Map<String, Object> map = new HashMap<>();
        map.put("huodongId", huodongId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongMembers, callBack);

    }

    /**
     * 取消班级活动
     *
     * @param activity
     * @param huodongId
     * @param callBack
     */
    public static void cancelClassHuoDong(final Activity activity, String huodongId, final HttpRequestCallBack callBack) {

        final Map<String, Object> map = new HashMap<>();
        map.put("huodongId", huodongId);

//        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否提醒成员？", "确定", new DialogButtonListener() {
//            @Override
//            public void setOnDialogButtonListener() {

        map.put("notice", true);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongCancel, callBack);
//            }
//        }, "取消", new DialogButtonListener() {
//            @Override
//            public void setOnDialogButtonListener() {
//
//                map.put("notice", false);
//                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongCancel, callBack);
//            }
//        });


    }

    public static void getClassHuoDongTimeLine(final Activity activity, String classhuodongId, int timeCursor, HttpRequestCallBack callBack, LoadingType loadingType) {

        Map<String, Object> map = new HashMap<>();
        map.put("classhuodongId", classhuodongId);
        map.put("timeCursor", timeCursor);
        map.put("pageSize", 4);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongTimeLine, callBack, loadingType);

    }

    public static void ClassHuoDongSignOrExit(final Activity activity, final String huodongId, boolean isExit, final HttpRequestCallBack callBack) {

        if (isExit) {
            ReminderHelper.getIntentce().showDialog(activity, "提示", "是否取消报名？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                    Map<String, Object> map = new HashMap<>();
                    map.put("huodongId", huodongId);
                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongSignUpOrExit, callBack);
                }
            }, "取消", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                }
            });

        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("huodongId", huodongId);
            HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.ClassHuoDongSignUpOrExit, callBack);
        }
    }

    /**
     * @param type
     * @return
     */
    public static String getNoticeText(int type) {

        String str = "";
        switch (type) {

            case 0:
                str = "不提醒";
                break;
            case 1:
                str = "提前30分钟";
                break;
            case 2:
                str = "提前12小时";
                break;
            case 3:
                str = "提前24小时";
                break;


        }
        return str;
    }


    //进入相册统一入口

    /**
     * @param activity
     * @param id          个人、班级 、社群 id
     * @param albumEnum   相册类型
     * @param requestCode 请求码
     */
    public static void gotoAlbumsCenter(final Activity activity, final String id, final ActionTypeEnum albumEnum, final int requestCode, boolean isShowNewPhotoOnly) {

        if (!StringUtil.isEmpty(id))
            switch (albumEnum) {
                case CLASS:

                    Map<String, Object> map = new TreeMap<>();
                    map.put("classId", id);
                    HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getClassInfor, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            ClassModel classModel = JSON.parseObject(result, ClassModel.class);
                            if (classModel != null) {
                                AlbumAuthorityModel model = new AlbumAuthorityModel();
                                model.setId(id);
                                model.setIsAdmin(classModel.isIsAdmin());
                                model.setIsMasker(classModel.isIsClassMaker());
                                model.setIsMember(classModel.isIsMember());

                                AlbumsCenterActivity.start(activity, albumEnum, model, requestCode, isShowNewPhotoOnly);
                            }
                        }

                        @Override
                        public void onFailure(String errorMsg) {

                        }
                    }, LoadingType.NONE);
                    break;


                //个人相册
                case OWNER:

                    AlbumAuthorityModel model = new AlbumAuthorityModel();
                    model.setId(id);
                    if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(id)) {
                        model.setIsOwner(true);
                    } else {
                        model.setIsOwner(false);
                    }
                    AlbumsCenterActivity.start(activity, albumEnum, model, requestCode, isShowNewPhotoOnly);
                    break;

                //社群相册
                case GROUP:

                    map = new TreeMap<>();
                    map.put("groupId", id);
                    map.put("pageSize", 0);

                    HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.singleGroup, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            GroupBean groupBean = JSON.parseObject(result, GroupBean.class);
                            AlbumAuthorityModel model = new AlbumAuthorityModel();
                            model.setId(id);

                            model.setIsMasker(groupBean.isIsGroupMaker());
                            model.setIsMember(groupBean.isIsMember());
                            model.setIsAdmin(groupBean.isIsManager());

                            AlbumsCenterActivity.start(activity, albumEnum, model, requestCode, isShowNewPhotoOnly);

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    }, LoadingType.NONE);
                    break;

            }
    }

    public static void getGrouthTaskList(Activity activity, int taskType, int ownerType, int pageIndex, int pageSize, HttpRequestCallBack callBack, LoadingType loadingType) {
        Map<String, Object> map = new TreeMap<>();
        map.put("taskType", taskType);
        map.put("ownerType", ownerType);
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getGrouthTaskList, callBack, loadingType);
    }


    public static void getGrouthTaskList(Activity activity, int ownerType, int pageIndex, int pageSize, HttpRequestCallBack callBack, LoadingType loadingType) {
        Map<String, Object> map = new TreeMap<>();
        map.put("ownerType", ownerType);
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getGrouthTaskList, callBack, loadingType);
    }


    public static void getPointRecordList(Activity activity, int action, int pageIndex, int pageSize, HttpRequestCallBack callBack, LoadingType loadingType) {
        Map<String, Object> map = new TreeMap<>();
        map.put("classId", HiCache.getInstance().getClassDetailInfo().getClassId());
        map.put("action", action);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.getPointRecords, callBack, loadingType);
    }

    public static void getAdvertisement(Activity activity, HttpRequestCallBack callBack) {
        Map<String, Object> map = new TreeMap<>();
        HiStudentHttpUtils.postDataByOKHttp(((BaseActivity) activity), map, HistudentUrl.Advertisement, callBack, LoadingType.NONE);
    }

    public static void playAnimation(final Activity activity, final ImageView imageView) {
        final int width, height;
        width = getScreenWind();
        height = SystemUtil.dp2px(220) + SystemUtil.dp2px(70);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new PointF(width, height));
//        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {

                PointF point = new PointF();
                point.x = (width - imageView.getWidth()) / 2;// - 200 * fraction * 4
                point.y = height - imageView.getHeight() - 200 * fraction * 3;
                return point;
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);


            }
        });

        //透明度变化
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.4f, 1f, 0.2f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(valueAnimator, alpha);
        set.setDuration(7000);
        set.start();

    }


    public static void getSingleAlbumDetail(final Activity activity, String albumId, final ActionTypeEnum typeEnum, HttpRequestCallBack callBack) {


        int userType = typeEnum == ActionTypeEnum.CLASS ? 2 : typeEnum == ActionTypeEnum.GROUP ? 3 : 1;
        Map<String, Object> map = new TreeMap<>();
        map.put("albumId", albumId);
        map.put("ownerType", userType);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.getAlbumInfo,
                callBack);
    }

}
