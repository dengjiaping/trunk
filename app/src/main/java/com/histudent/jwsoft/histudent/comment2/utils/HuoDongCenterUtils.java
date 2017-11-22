package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.ActivityMembers_Activity;
import com.histudent.jwsoft.histudent.body.find.activity.CreateGroupHuoDongFristStep;
import com.histudent.jwsoft.histudent.body.find.bean.GroupHuoDongDetailModel;
import com.histudent.jwsoft.histudent.body.find.bean.HuoDongDetailsModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.hiworld.activity.EssayActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.myclass.activity.CreateHuoDongFirstStep;
import com.histudent.jwsoft.histudent.body.myclass.activity.HuoDongCenterActivity;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ReportType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.*;
import com.histudent.jwsoft.histudent.commen.utils.TimeUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.ReportActivity;
import com.histudent.jwsoft.histudent.comment2.bean.NetImageModel;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYT on 2017/5/16.
 */

public class HuoDongCenterUtils {


    /**
     * @param activity
     * @param detailsModel  班级活动类
     * @param huodongId     活动id
     * @param parentView    父布局
     * @param clickListener 点击事件
     * @param callBack1     操作结果的回调
     */

    public static void updateClassCenterUi(final Activity activity, final HuoDongDetailsModel detailsModel, final String huodongId,
                                           RelativeLayout parentView, View.OnClickListener clickListener, final itemsSuccessCallBack callBack1) {


        TextView huoDongAddress = ((TextView) parentView.findViewById(R.id.address));
        ImageView huoDongCover = ((ImageView) parentView.findViewById(R.id.bg));
        TextView huoDongInstr = ((TextView) parentView.findViewById(R.id.huodong_instr));
        TextView huoDongTime = ((TextView) parentView.findViewById(R.id.start_time));
        TextView huoDongOri = ((TextView) parentView.findViewById(R.id.originator));
        TextView huoDongName = ((TextView) parentView.findViewById(R.id.huodong_name));
        TextView huoDongUserCount = ((TextView) parentView.findViewById(R.id.user_count));
        LinearLayout imageLayout = ((LinearLayout) parentView.findViewById(R.id.imagelayout));
        TextView signButton = ((TextView) parentView.findViewById(R.id.sign));
        LinearLayout act_layout = ((LinearLayout) parentView.findViewById(R.id.act_layout));

        huoDongOri.setText(detailsModel.getCreateName());
        huoDongTime.setText(TimeUtils.format(detailsModel.getStartTime()) + " - " + TimeUtils.format(detailsModel.getEndTime()));
        huoDongInstr.setText(detailsModel.getIntroduction());
        huoDongAddress.setText(detailsModel.getPlace());
        huoDongName.setText(detailsModel.getName());
        huoDongUserCount.setText(detailsModel.getSignUpNum() + "人报名");

        GetDeleteUserCallBack callBack = new GetDeleteUserCallBack() {
            @Override
            public void getDeleteUserModel(SimpleUserModel user) {
                if (!StringUtil.isEmpty(user.getUserId())) {
                    PersonCenterActivity.start(activity, user.getUserId());

                } else {
                    ActivityMembers_Activity.start(activity, huodongId, 1);
                }
            }
        };

        parentView.findViewById(R.id.share).setOnClickListener(clickListener);
        parentView.findViewById(R.id.address).setOnClickListener(clickListener);



        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(detailsModel.getCreateUser())) {
            if (detailsModel.getStatus() != 2) {
                parentView.findViewById(R.id.title_right).setVisibility(View.VISIBLE);
                parentView.findViewById(R.id.title_right).setOnClickListener(clickListener);
            } else {
                parentView.findViewById(R.id.title_right).setVisibility(View.GONE);
            }
        } else {
            parentView.findViewById(R.id.title_right).setVisibility(View.VISIBLE);
            parentView.findViewById(R.id.title_right).setOnClickListener(clickListener);
        }


        if (detailsModel.isIsMember()) {
            //报名入口
            if (detailsModel.getStatus() < 2 &&
                    !HiCache.getInstance().getLoginUserInfo().getUserId().equals(detailsModel.getCreateUser())) {//未报名，同时活动没有结束和报满
                act_layout.setVisibility(View.VISIBLE);
                if (detailsModel.isIsSinuped()) {
                    if (detailsModel.getStatus() == 1) {
                        act_layout.setVisibility(View.GONE);
                    } else {
                        signButton.setText("报名取消");
                    }
                } else {
                    signButton.setText("报名参加");
                }
            }else {
                act_layout.setVisibility(View.GONE);
            }

            act_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //免责声明提示
                    if (!detailsModel.isIsSinuped()) {
                        ReminderHelper.getIntentce().showStatementDialog(activity, new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                                //报名活动
                                ClassHelper.ClassHuoDongSignOrExit(activity, huodongId, detailsModel.isIsSinuped(), new HttpRequestCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                        //成员退出或者加入
                                        callBack1.scuccess(SuccessEnum.HUODONG_SIGN_QUIT);
                                    }

                                    @Override
                                    public void onFailure(String errorMsg) {

                                    }
                                });
                            }
                        }, new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                                //阅读免责声明

                                Log.e("免责声明url==>", HistudentUrl.huodongDisclaimer);
                                HTWebActivity.start(activity, HistudentUrl.huodongDisclaimer);

                            }
                        });

                        //不收费活动正常报名
                    } else {
                        //报名活动
                        ClassHelper.ClassHuoDongSignOrExit(activity, huodongId, detailsModel.isIsSinuped(), new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                //成员退出或者加入
                                callBack1.scuccess(SuccessEnum.HUODONG_SIGN_QUIT);
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });
                    }
                }
            });

            //控制是否能发动态
            if (detailsModel.isIsSinuped() && detailsModel.getStatus() > 0) {
                parentView.findViewById(R.id.edit).setVisibility(View.VISIBLE);
            } else {
//                parentView.findViewById(R.id.edit).setVisibility(View.GONE);
            }
            parentView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EssayActivity.start(activity, BlogEnum.CLASSHUODONG, 222, huodongId);
                }
            });

        } else {
            act_layout.setVisibility(View.GONE);
//            parentView.findViewById(R.id.edit).setVisibility(View.GONE);
        }


        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, detailsModel.getCoverUrl(),
                huoDongCover, PhotoManager.getInstance().getDefaultPlaceholderResource());


        LinearLayout addImageLayout = parentView.findViewById(R.id.layout_user);
        addImageLayout.removeAllViews();
        if (detailsModel.getSinupedUsers() != null && detailsModel.getSinupedUsers().size() > 0) {

            int size = detailsModel.getSinupedUsers().size() <= 5 ? detailsModel.getSinupedUsers().size() : 4;

            for (int i = 0; i < size; i++) {
                HuoDongDetailsModel.SinupedUsersBean b = detailsModel.getSinupedUsers().get(i);

                Log.e(b.getName(), b.getUserId());
                SimpleUserModel userModel = new SimpleUserModel(b.getAvatar(), b.getName(), b.getUserId(), b);
                ComViewUitls.addUserView(activity, addImageLayout, userModel, callBack);
            }

            if (size == 4 && detailsModel.getSinupedUsers().size() != 4) {
                SimpleUserModel userModel = new SimpleUserModel();
                ComViewUitls.addUserView(activity, addImageLayout, userModel, callBack);
            }

            imageLayout.removeAllViews();
            if (detailsModel.getIntroImgList() != null) {
                for (HuoDongDetailsModel.IntroImgListBean model : detailsModel.getIntroImgList()) {

                    NetImageModel imageModel = new NetImageModel();
                    imageModel.setHeight(model.getHeight());
                    imageModel.setId(model.getImgId());
                    imageModel.setWidth(model.getWidth());
                    imageModel.setUrl(model.getSavePath());
                    ComViewUitls.addPicture(activity, imageModel, imageLayout);
                }
            }
        }
    }


    /**
     * @param activity
     * @param detailsModel  社群活动类
     * @param huodongId     活动id
     * @param parentView    父布局
     * @param clickListener 点击事件
     * @param callBack1     操作的回调
     */
    public static void updateGroupCenterUi(final Activity activity, final GroupHuoDongDetailModel detailsModel,
                                           final String huodongId, RelativeLayout parentView,
                                           View.OnClickListener clickListener, final itemsSuccessCallBack callBack1
    ) {


        TextView huoDongAddress = ((TextView) parentView.findViewById(R.id.address));
        ImageView huoDongCover = ((ImageView) parentView.findViewById(R.id.bg));
        TextView huoDongInstr = ((TextView) parentView.findViewById(R.id.huodong_instr));
        TextView huoDongTime = ((TextView) parentView.findViewById(R.id.start_time));
        TextView huoDongName = ((TextView) parentView.findViewById(R.id.huodong_name));
        TextView huoDongUserCount = ((TextView) parentView.findViewById(R.id.user_count));
        LinearLayout imageLayout = ((LinearLayout) parentView.findViewById(R.id.imagelayout));
        TextView signButton = ((TextView) parentView.findViewById(R.id.sign));
        TextView cost = ((TextView) parentView.findViewById(R.id.cost));
        parentView.findViewById(R.id.listview).setVisibility(View.GONE);
        parentView.findViewById(R.id.tv_).setVisibility(View.GONE);
        parentView.findViewById(R.id.line).setVisibility(View.GONE);
        parentView.findViewById(R.id.line0).setVisibility(View.GONE);
//        parentView.findViewById(R.id.edit).setVisibility(View.GONE);
        parentView.findViewById(R.id.layout_originator).setVisibility(View.GONE);
        parentView.findViewById(R.id.layout_cost).setVisibility(View.VISIBLE);
        LinearLayout act_layout = (LinearLayout) parentView.findViewById(R.id.act_layout);


        cost.setText(detailsModel.getUserCost() == 0 ? "免费" : "人均" + detailsModel.getUserCost() + "元");
        huoDongTime.setText(TimeUtils.format(detailsModel.getStartTime()) + " - " + TimeUtils.format(detailsModel.getEndTime()));
        huoDongInstr.setText(detailsModel.getIntroduction());
        huoDongAddress.setText(detailsModel.getPlace());
        huoDongName.setText(detailsModel.getName());
        huoDongUserCount.setText(detailsModel.getSignUpNum() + "人报名");
        GetDeleteUserCallBack callBack = new GetDeleteUserCallBack() {
            @Override
            public void getDeleteUserModel(SimpleUserModel user) {
                if (!StringUtil.isEmpty(user.getUserId())) {
                    PersonCenterActivity.start(activity, user.getUserId());

                } else {
                    ActivityMembers_Activity.start(activity, huodongId, 2);
                }
            }
        };


        if (detailsModel.isIsMember()) {
            if (detailsModel.getStatus() < 2 &&
                    !HiCache.getInstance().getLoginUserInfo().getUserId().equals(detailsModel.getCreateUser())) {
                act_layout.setVisibility(View.VISIBLE);
                parentView.findViewById(R.id.title_right).setVisibility(View.VISIBLE);
                if (detailsModel.isIsSinuped()) {
                    if (detailsModel.getStatus() == 1) {
                        act_layout.setVisibility(View.GONE);
                    } else {
                        signButton.setText("报名取消");
                    }
                } else {
                    signButton.setText("报名参加");
                }
            } else {
                act_layout.setVisibility(View.GONE);
            }

            act_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //免责声明提示
                    if (!detailsModel.isIsSinuped()) {
                        ReminderHelper.getIntentce().showStatementDialog(activity, new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                                //报名活动
                                GroupHelper.GroupHuoDongSignOrExit(activity, huodongId, detailsModel.isIsSinuped(), new HttpRequestCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                        //成员退出或者加入
                                        callBack1.scuccess(SuccessEnum.HUODONG_SIGN_QUIT);
                                    }

                                    @Override
                                    public void onFailure(String errorMsg) {

                                    }
                                });
                            }
                        }, new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                                //阅读免责声明

                                Log.e("免责声明url==>", HistudentUrl.huodongDisclaimer);
                                HTWebActivity.start(activity, HistudentUrl.huodongDisclaimer);

                            }
                        });

                        //不收费活动正常报名
                    } else {
                        GroupHelper.GroupHuoDongSignOrExit(activity, huodongId, detailsModel.isIsSinuped(), new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                //成员退出或者加入
                                callBack1.scuccess(SuccessEnum.HUODONG_SIGN_QUIT);
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });
                    }
                }
            });
        } else {
            act_layout.setVisibility(View.GONE);
        }

        parentView.findViewById(R.id.share).setOnClickListener(clickListener);
        parentView.findViewById(R.id.address).setOnClickListener(clickListener);
        parentView.findViewById(R.id.title_left).setOnClickListener(clickListener);


        if (!HiCache.getInstance().getLoginUserInfo().getUserId().equals(detailsModel.getCreateUser())) {
            if (detailsModel.getStatus() != 2) {
                parentView.findViewById(R.id.title_right).setVisibility(View.VISIBLE);
                parentView.findViewById(R.id.title_right).setOnClickListener(clickListener);
            }
        } else {
            parentView.findViewById(R.id.title_right).setVisibility(View.VISIBLE);
            parentView.findViewById(R.id.title_right).setOnClickListener(clickListener);
        }


        /**
         * 0:报名中
         * 1:进行中
         * 2:已结束
         * 3:已报满
         */
//        int statusType = ((HuoDongCenterActivity) activity).getStatusType();
//        int personCount = ((HuoDongCenterActivity) activity).getJoinPersonCount();
//
//        //1.未报名--报名参加   2.已报名--报名取消
//        if (statusType == 0) {
//            //正在报名中
//            if (detailsModel.isIsMember()) {
////                act_layout.setVisibility(View.VISIBLE);
//            } else {
////                act_layout.setVisibility(View.GONE);
//            }
//            if (personCount != 0) {
//                signButton.setText("报名取消");
//                parentView.findViewById(R.id.iv_icon_status).setVisibility(View.GONE);
//            } else {
//                signButton.setText("报名参加");
//                parentView.findViewById(R.id.iv_icon_status).setVisibility(View.VISIBLE);
//            }
//        } else {
//            //如果不是
//            parentView.findViewById(R.id.title_right).setVisibility(View.INVISIBLE);
//        }


        parentView.findViewById(R.id.edit).setOnClickListener((View v) ->
                EssayActivity.start(activity, BlogEnum.CLASSHUODONG, 222, huodongId));

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, detailsModel.getCoverUrl(),
                huoDongCover, PhotoManager.getInstance().getDefaultPlaceholderResource());


        ((LinearLayout) parentView.findViewById(R.id.layout_user)).removeAllViews();
        if (detailsModel.getSinupedUsers() != null && detailsModel.getSinupedUsers().size() > 0) {

            int size = detailsModel.getSinupedUsers().size() <= 5 ? detailsModel.getSinupedUsers().size() : 4;

            for (int i = 0; i < size; i++) {
                GroupHuoDongDetailModel.SinupedUsersBean b = detailsModel.getSinupedUsers().get(i);

                Log.e(b.getName(), b.getUserId());
                SimpleUserModel userModel = new SimpleUserModel(b.getAvatar(), b.getName(), b.getUserId());
                ComViewUitls.addUserView(activity, ((LinearLayout) parentView.findViewById(R.id.layout_user)), userModel, callBack);
            }

            if (size == 4 && detailsModel.getSinupedUsers().size() != 4) {
                SimpleUserModel userModel = new SimpleUserModel();
                ComViewUitls.addUserView(activity, ((LinearLayout) parentView.findViewById(R.id.layout_user)), userModel, callBack);

            }

            imageLayout.removeAllViews();
            if (detailsModel.getIntroImgList() != null) {
                for (GroupHuoDongDetailModel.IntroImgListBean url : detailsModel.getIntroImgList()) {
                    NetImageModel imageModel = new NetImageModel();
                    imageModel.setHeight(url.getHeight());
                    imageModel.setWidth(url.getWidth());
                    imageModel.setUrl(url.getSavePath());
                    imageModel.setId(url.getImgId());
                    ComViewUitls.addPicture(activity, imageModel, imageLayout);
                }
            }
        }
    }


    //班级活动的弹出框
    public static void initClassBottomMenu(final Activity activity, final HuoDongDetailsModel classHuoDongDetailsModel,
                                           final String huodongId, RelativeLayout parentLayout, final itemsSuccessCallBack callBack) {

        View.OnClickListener noticeTimeItems;
        final TopMenuPopupWindow menuWindow[] = new TopMenuPopupWindow[1];
        noticeTimeItems = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuWindow[0].dismiss();
                switch (v.getId()) {


                    case R.id.btn_01:


                        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(classHuoDongDetailsModel.getCreateUser())) {//创建者取消活动

                            //取消活动
                            if (((HuoDongCenterActivity) activity).getJoinPersonCount() > 1) {
                                //当前处于  非报名状态
                                Toast.makeText(activity, "当前有人参加，不可取消活动..", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定取消活动？", "确定", new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {
                                    ClassHelper.cancelClassHuoDong(activity, huodongId, new HttpRequestCallBack() {
                                        @Override
                                        public void onSuccess(String result) {
                                            Toast.makeText(activity, "取消成功", Toast.LENGTH_SHORT).show();
                                            //取消班级活动
                                            callBack.scuccess(SuccessEnum.HUODONG_CANCEL);

                                        }

                                        @Override
                                        public void onFailure(String errorMsg) {

                                        }
                                    });
                                }
                            }, "取消", new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {

                                }
                            });
                        } else {//一般人举报
                            ReportActivity.start(activity, huodongId, ReportType.OTHER);
                        }

                        break;
                    case R.id.btn_02:

                        if (((HuoDongCenterActivity) activity).getStatusType() != 0) {
                            //当前处于  非报名状态
                            Toast.makeText(activity, "当前不是报名状态，不可编辑..", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(classHuoDongDetailsModel.getCreateUser())) {//编辑活动
                            CreateHuoDongFirstStep.startEditeActivity(activity, classHuoDongDetailsModel);
                        } else if (classHuoDongDetailsModel.isIsSinuped()) {//成员举报
                            ReportActivity.start(activity, huodongId, ReportType.OTHER);
                        }

                        break;
                }
            }
        };


        if (classHuoDongDetailsModel != null) {
            List<String> list_name1 = new ArrayList<>();
            List<Integer> list_color1 = new ArrayList<>();
            if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(classHuoDongDetailsModel.getCreateUser())) {
                list_name1.add("取消活动");
                list_color1.add(Color.rgb(51, 51, 51));
                list_name1.add("编辑活动");
                list_color1.add(Color.rgb(51, 51, 51));
            } else {
                list_name1.add("举报");
                list_color1.add(Color.rgb(51, 51, 51));
            }

            menuWindow[0] = new TopMenuPopupWindow(activity, noticeTimeItems, list_name1, list_color1, false);
            menuWindow[0].showAtLocation(parentLayout.findViewById(R.id.title_right), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }


    }


    //社群活动的弹出框
    public static void initGroupBottomMenu(final Activity activity, final GroupHuoDongDetailModel classHuoDongDetailsModel,
                                           final String huodongId, RelativeLayout parentLayout, final itemsSuccessCallBack callBack) {

        View.OnClickListener noticeTimeItems;
        final TopMenuPopupWindow menuWindow[] = new TopMenuPopupWindow[1];
        noticeTimeItems = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuWindow[0].dismiss();
                switch (v.getId()) {


                    case R.id.btn_01:


                        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(classHuoDongDetailsModel.getCreateUser())) {//创建者取消活动

                            //取消活动
                            if (((HuoDongCenterActivity) activity).getJoinPersonCount() > 1) {
                                //当前处于  非报名状态
                                Toast.makeText(activity, "当前有人参加，不可取消活动..", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定取消活动？", "确定", new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {
                                    GroupHelper.cancelGroupHuodong(activity, huodongId, new HttpRequestCallBack() {
                                        @Override
                                        public void onSuccess(String result) {

                                            //取消班级活动
                                            callBack.scuccess(SuccessEnum.HUODONG_CANCEL);
                                        }

                                        @Override
                                        public void onFailure(String errorMsg) {

                                        }
                                    });
                                }
                            }, "取消", new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {

                                }
                            });
                        } else {//一般人举报
                            ReportActivity.start(activity, huodongId, ReportType.OTHER);
                        }

                        break;
                    case R.id.btn_02:

                        if (((HuoDongCenterActivity) activity).getStatusType() != 0) {
                            //当前处于  非报名状态
                            Toast.makeText(activity, "当前不是报名状态，不可编辑..", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(classHuoDongDetailsModel.getCreateUser())) {//编辑活动
                            CreateGroupHuoDongFristStep.startEditeActivity(activity, classHuoDongDetailsModel, 111);
                        } else if (classHuoDongDetailsModel.isIsSinuped()) {//成员举报
                            ReportActivity.start(activity, huodongId, ReportType.OTHER);
                        }
                        break;
                }
            }
        };


        if (classHuoDongDetailsModel != null) {
            List<String> list_name1 = new ArrayList<>();
            List<Integer> list_color1 = new ArrayList<>();
            if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(classHuoDongDetailsModel.getCreateUser())) {
                list_name1.add("取消活动");
                list_color1.add(Color.rgb(51, 51, 51));
                list_name1.add("编辑活动");
                list_color1.add(Color.rgb(51, 51, 51));
            } else {
                list_name1.add("举报");
                list_color1.add(Color.rgb(51, 51, 51));
            }

            menuWindow[0] = new TopMenuPopupWindow(activity, noticeTimeItems, list_name1, list_color1, false);
            menuWindow[0].showAtLocation(parentLayout.findViewById(R.id.title_right), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }


    }

    public interface itemsSuccessCallBack {

        public void scuccess(SuccessEnum type);
    }
}
