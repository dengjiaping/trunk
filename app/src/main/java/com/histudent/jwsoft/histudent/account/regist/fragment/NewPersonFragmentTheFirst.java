package com.histudent.jwsoft.histudent.account.regist.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.regist.activity.NewPersonActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuguiyu-pc on 2016/12/5.
 */

public class NewPersonFragmentTheFirst extends BaseFragment {
    private View view;
    private HiStudentHeadImageView headImageView;
    private TextView add_class;
    private PictureTailorHelper clippHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thefirstnewperson, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        clippHelper = PictureTailorHelper.getInstance();
        headImageView = (HiStudentHeadImageView) view.findViewById(R.id.camera);

        add_class = (TextView) view.findViewById(R.id.add_class);
        add_class.setText(HiCache.getInstance().getLoginUserInfo().getUserType() == 3 ? "创建班级" : "加入班级");

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().sendBroadcast(new Intent(TheReceiverAction.GETHEADIMAGE));
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
                String path = clippHelper.setPicToView(data);
                PersionHelper.getInstance().setPicToView((BaseActivity) getActivity(), path, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String pathUrl = new JSONObject(result).optString("imgUrl");
                            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(getContext(), pathUrl,
                                    headImageView, PhotoManager.getInstance().getDefaultPlaceholderResource());
                            HiCache.getInstance().updataAccountDataInDB(pathUrl);
                            NewPersonActivity.isUpdataImage = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
        }
    }

}
