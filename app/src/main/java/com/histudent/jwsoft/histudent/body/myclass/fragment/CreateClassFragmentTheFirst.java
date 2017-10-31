package com.histudent.jwsoft.histudent.body.myclass.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassCreateActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liuguiyu-pc on 2016/12/5.
 */

public class CreateClassFragmentTheFirst extends BaseFragment {
    private View view;
    private HiStudentHeadImageView headImageView;
    private PictureTailorHelper clippHelper;
    private RelativeLayout camera_layout;
    private TextView gonext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thefirstcreateclass, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        clippHelper = PictureTailorHelper.getInstance();
        headImageView = (HiStudentHeadImageView) view.findViewById(R.id.camera);
        camera_layout = (RelativeLayout) view.findViewById(R.id.camera_layout);
        gonext = (TextView) view.findViewById(R.id.gonext);

        camera_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(ClassCreateActivity.TAKE_PHOTO);
            }
        });

        gonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(ClassCreateActivity.SECONDE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO:
                clippHelper.startPhotoZoom((BaseActivity) getActivity(), Uri.fromFile(clippHelper.photo_path), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_GALLERY:
                if (data != null)
                    clippHelper.startPhotoZoom((BaseActivity) getActivity(), data.getData(), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_CUT:
                String path = clippHelper.setPicToView(headImageView, data);
                if (!TextUtils.isEmpty(path)) {
                    ClassCreateActivity.logoPath = path;
                }

                break;
        }
    }


}
