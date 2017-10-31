//package com.histudent.jwsoft.histudent.commen.view;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//
//import com.histudent.jwsoft.histudent.R;
//import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
//import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
//
///**
// * Created by liuguiyu-pc on 2016/8/28.
// * 分享的PopupWindow
// */
//public class SharePopWindow extends PopupWindow implements View.OnClickListener {
//
//    private View mMenuView;
//    private BaseActivity context;
//    private String activityId;
//    private int num;
//    private int shareObjectType;
//
//    private ImageView weixin, weixin_circle, qq_circle;
//
//    public SharePopWindow(BaseActivity context, String activityId, int shareObjectType) {
//        super(context);
//        this.context = context;
//        this.activityId = activityId;
//        this.shareObjectType = shareObjectType;
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.popwindow_share, null);
//
//        weixin = (ImageView) mMenuView.findViewById(R.id.share_weixin);
//        weixin_circle = (ImageView) mMenuView.findViewById(R.id.share_weixin_circle);
//        qq_circle = (ImageView) mMenuView.findViewById(R.id.qq_circle);
//
//        //设置按钮监听
//        weixin.setOnClickListener(this);
//        weixin_circle.setOnClickListener(this);
//        qq_circle.setOnClickListener(this);
//
//        //设置SelectPicPopupWindow的View
//        this.setContentView(mMenuView);
//        //设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        //设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        //设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopupAnimation);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.hiworld_fram_gb));
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        mMenuView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            //弹出对话框中的分享
//            case R.id.share_weixin:
//                dismiss();
//                if (activityId != null) {
//
//                    if (shareObjectType == -1) {
//                        ShareUtils.getShareData(context, activityId, ShareUtils.WEIXIN);
//                    } else {
//                        ShareUtils.getShareData(context, activityId, ShareUtils.WEIXIN, shareObjectType);
//                    }
//                } else {
//                    ShareUtils.sharePicture_02(context, ShareUtils.WEIXIN);
//                }
//
//                break;
//
//            case R.id.share_weixin_circle:
//                dismiss();
//                if (activityId != null) {
//                    if (shareObjectType == -1) {
//                        ShareUtils.getShareData(context, activityId, ShareUtils.WEIXIN_CIRCLE);
//                    } else {
//                        ShareUtils.getShareData(context, activityId, ShareUtils.WEIXIN_CIRCLE, shareObjectType);
//                    }
//                } else {
//                    ShareUtils.sharePicture_02(context, ShareUtils.WEIXIN_CIRCLE);
//                }
//                break;
//
//            case R.id.qq_circle:
//                dismiss();
//                if (activityId != null) {
//                    if (shareObjectType == -1) {
//                        ShareUtils.getShareData(context, activityId, ShareUtils.QQ);
//                    } else {
//                        ShareUtils.getShareData(context, activityId, ShareUtils.QQ, shareObjectType);
//                    }
//                } else {
//                    ShareUtils.sharePicture_02(context, ShareUtils.QQ);
//                }
//                break;
//        }
//    }
//
//}
