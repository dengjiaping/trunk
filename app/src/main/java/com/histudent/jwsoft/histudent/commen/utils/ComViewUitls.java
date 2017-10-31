package com.histudent.jwsoft.histudent.commen.utils;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.FindHomeModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.bean.NetImageModel;
import com.histudent.jwsoft.histudent.comment2.utils.GetDeleteUserCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.SelectedClassCallBack;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.List;

/**
 * Created by ZhangYT on 2017/4/13.
 */

public class ComViewUitls {

    public static void setSyncViewVisiablity(Activity activity, String authority) {

        if (!StringUtil.isEmpty(authority)) {
            if (authority.equals("私密")) {
//                activity.findViewById(R.id.classes_layout).setVisibility(View.GONE);
                activity.findViewById(R.id.user_sycn_class_layout).setVisibility(View.GONE);
                if (activity.findViewById(R.id.tv_at) != null)
                    activity.findViewById(R.id.tv_at).setVisibility(View.GONE);
            } else {
                activity.findViewById(R.id.user_sycn_class_layout).setVisibility(View.VISIBLE);
                if (activity.findViewById(R.id.tv_at) != null)
                    activity.findViewById(R.id.tv_at).setVisibility(View.VISIBLE);
            }
        }
    }

    public static void setSyncViewVisiablity(Activity activity, boolean isShow) {


        if (isShow) {
            activity.findViewById(R.id.user_sycn_class_layout).setVisibility(View.VISIBLE);
        } else {
            activity.findViewById(R.id.user_sycn_class_layout).setVisibility(View.GONE);
        }
    }

    public static void setSyncViewVisiablityByNum(Activity activity, int authority) {

        if (authority == 2) {
            activity.findViewById(R.id.user_sycn_class_layout).setVisibility(View.GONE);
            if (activity.findViewById(R.id.tv_at) != null)
                activity.findViewById(R.id.tv_at).setVisibility(View.GONE);

        } else {
            activity.findViewById(R.id.user_sycn_class_layout).setVisibility(View.VISIBLE);
            if (activity.findViewById(R.id.tv_at) != null)
                activity.findViewById(R.id.tv_at).setVisibility(View.VISIBLE);
        }
    }


    //添加好友
    public static void addView(Activity activity, final LinearLayout linearLayout, SimpleUserModel model, final GetDeleteUserCallBack callBack) {

        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.item, null);
        HiStudentHeadImageView iv = ((HiStudentHeadImageView) layout.findViewById(R.id.iv));
        IconView iv_delete = ((IconView) layout.findViewById(R.id.iv_delete));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SystemUtil.dp2px(40), SystemUtil.dp2px(40));
        params.setMargins(5, 5, SystemUtil.dp2px(10), 5);
        iv.setLayoutParams(params);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(SystemUtil.dp2px(25) - iv_delete.getWidth(), 0, 0, SystemUtil.dp2px(5));

        iv_delete.setLayoutParams(params1);

//        MyImageLoader.getIntent().displayNetImageWithAnimation(activity, model.getAvatar(), iv, R.mipmap.avatar_def);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, model.getAvatar(),
                iv, ContextCompat.getDrawable(activity, R.mipmap.avatar_def));

        layout.setTag(model);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleUserModel url = ((SimpleUserModel) v.getTag());
                linearLayout.removeView(v);
                if (callBack != null)
                    callBack.getDeleteUserModel(url);
            }
        });
        linearLayout.addView(layout);
    }

    //添加好友
    public static void addUserView(Activity activity, final LinearLayout linearLayout, SimpleUserModel model, final GetDeleteUserCallBack callBack) {

        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.parser_item, null);
        HiStudentHeadImageView iv = layout.findViewById(R.id.iv);
        ImageView teacherIdentity = layout.findViewById(R.id.iv_teacher_identity);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SystemUtil.dp2px(45), SystemUtil.dp2px(45));
//        params.setMargins(2, 5, 2, 5);
//        iv.setLayoutParams(params);

        if (model.getType() == 3) {
            teacherIdentity.setVisibility(View.VISIBLE);
        } else {
            teacherIdentity.setVisibility(View.INVISIBLE);
        }

        if (model.getSinupedUsersBean() != null) {
            if (model.getSinupedUsersBean().getUserType() == 3) {
                teacherIdentity.setVisibility(View.VISIBLE);
            } else {
                teacherIdentity.setVisibility(View.INVISIBLE);
            }
        }

        if (!StringUtil.isEmpty(model.getAvatar())) {
//            MyImageLoader.getIntent().displayNetImageWithAnimation(activity, model.getAvatar(), iv, R.mipmap.avatar_def);
            CommonGlideImageLoader.getInstance().displayNetImage(activity, model.getAvatar(),
                    iv, ContextCompat.getDrawable(activity, R.mipmap.avatar_def));
        } else {
            teacherIdentity.setVisibility(View.INVISIBLE);
        }

        layout.setTag(model);
        layout.setOnClickListener((View v) -> {
            SimpleUserModel userModel = ((SimpleUserModel) v.getTag());
            if (callBack != null)
                callBack.getDeleteUserModel(userModel);
        });
        linearLayout.addView(layout);
    }

    //添加好友
    public static void addView1(Activity activity, final LinearLayout linearLayout, final ClassModel.ClassBadgesBean o, final setOnClickListener callBack) {
        if (activity == null)
            return;
        LinearLayout layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.badge_item, null);
        ImageView iv = ((ImageView) layout.findViewById(R.id.iv_log));
        TextView iv_delete = ((TextView) layout.findViewById(R.id.tv_log));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SystemUtil.dp2px(60), SystemUtil.dp2px(60));
        params.gravity = Gravity.CENTER_HORIZONTAL;
        iv.setLayoutParams(params);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(SystemUtil.dp2px(70), SystemUtil.dp2px(20));

        params1.setMargins(5, 5, 5, 5);
        iv_delete.setLayoutParams(params1);

//        MyImageLoader.getIntent().displayNetImageWithAnimation(activity, o.getBadgeUrl(), iv, R.mipmap.avatar_def);
        CommonGlideImageLoader.getInstance().displayNetImage(activity, o.getBadgeUrl(),
                iv, ContextCompat.getDrawable(activity, R.mipmap.avatar_def));

        iv_delete.setText(o.getBadgeName());

        layout.setTag(o);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null)
                    callBack.setOnClickItemListener(o);
            }
        });
        linearLayout.addView(layout);
    }

    public static void addPicture(Activity activity, final NetImageModel imageModel, LinearLayout linearLayout) {
        final RelativeLayout layout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.log_edit_image_view, null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.log_edit_picture);
        IconView imageView_delete = (IconView) layout.findViewById(R.id.log_edit_delet);
        RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //取控件textView当前的布局参数

        imageView_delete.setVisibility(View.GONE);

        int h = imageModel.getHeight();
        int w = imageModel.getWidth();
        linearParams.width = SystemUtil.getScreenWind();
        linearParams.height = linearParams.width * h / w;

        //
        Log.e("----", linearParams.height + "");
        imageView.setLayoutParams(linearParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

//        MyImageLoader.getIntent().displayNetImageWithAnimation(activity, imageModel.getUrl(), imageView);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(activity, imageModel.getUrl(), imageView);

        linearLayout.addView(layout);

    }


    public static void addPictureWithEdit(Activity activity, final NetImageModel imageModel,
                                          final LinearLayout linearLayout, final setOnClickListener onClickListener) {
        final RelativeLayout layout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.log_edit_image_view, null);
        final ImageView imageView = (ImageView) layout.findViewById(R.id.log_edit_picture);
        IconView imageView_delete = (IconView) layout.findViewById(R.id.log_edit_delet);
        RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //取控件textView当前的布局参数


        linearParams.setMargins(SystemUtil.dp2px(15), SystemUtil.dp2px(5), SystemUtil.dp2px(15), SystemUtil.dp2px(5));
        int h = imageModel.getHeight();
        int w = imageModel.getWidth();

        if (!imageModel.getIsLocal()) {
            linearParams.width = (SystemUtil.getScreenWind() - 30) * 3 / 5;
            linearParams.height = linearParams.width * 3 / 4;
        } else {
            linearParams.width = (w - 30) * 3 / 5;
            linearParams.height = linearParams.width * 3 / 4;
        }
        //
        Log.e("----", linearParams.height + "");
        imageView.setLayoutParams(linearParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RelativeLayout.LayoutParams linearParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams1.setMargins(linearParams.width + SystemUtil.dp2px(5), 0, 0, 0);
        imageView_delete.setLayoutParams(linearParams1);

        if (imageModel.getIsLocal()) {
            CommonGlideImageLoader.getInstance().displayLocalImage(activity, new File(imageModel.getUrl()), imageView);
        } else {
            CommonGlideImageLoader.getInstance().displayNetImage(activity, imageModel.getUrl(), imageView);
        }
//        imageView.setTag(imageModel);


        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(layout);
                onClickListener.setOnClickItemListener(imageView.getTag());
            }
        });

        linearLayout.addView(layout);

    }


    //添加热门班级
    public static void addHotClass(Activity activity, final LinearLayout linearLayout, List<FindHomeModel.HotClassListBean> models, final SelectedClassCallBack callBack) {

        linearLayout.removeAllViews();

        if (models != null && models.size() > 0) {

            for (FindHomeModel.HotClassListBean model : models) {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.hot_class, null);
                HiStudentHeadImageView iv = layout.findViewById(R.id.class_log);
                TextView className = layout.findViewById(R.id.class_name);
                className.setText(model.getName());
                if (!StringUtil.isEmpty(model.getLogo())) {
                    CommonGlideImageLoader.getInstance().displayNetImage(activity, model.getLogo(),
                            iv, ContextCompat.getDrawable(activity, R.mipmap.default_class_style), SystemUtil.dp2px(60), SystemUtil.dp2px(60));
                }
                layout.setTag(model);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FindHomeModel.HotClassListBean userModel = ((FindHomeModel.HotClassListBean) v.getTag());
                        if (callBack != null)
                            callBack.getSelectedClass(userModel);
                    }
                });
                linearLayout.addView(layout);
            }

        }

    }

}
