package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.UploadPhotoActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.AlbumAuthorityModel;
import com.histudent.jwsoft.histudent.commen.fragment.AlbumFragment;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.fragment.image.UploadImgFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 相册中心
 */

public class AlbumsCenterActivity extends BaseActivity {


    @BindView(R.id.ll_new_upload_layout)
    LinearLayout mNewLayout;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.rb_upload)
    RadioButton mUpload;
    @BindView(R.id.rb_album)
    RadioButton mAlbum;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.title_right_image)
    IconView mTitleRightImg;
    @BindView(R.id.title_right)
    LinearLayout mTitleRight;
    @BindView(R.id.title_middle_text)
    TextView mTitle;

    @OnClick({R.id.title_right, R.id.title_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                ActionTypeEnum typeEnum = albumEnum == ActionTypeEnum.CLASS ? ActionTypeEnum.CLASS : albumEnum == ActionTypeEnum.GROUP ? ActionTypeEnum.GROUP : ActionTypeEnum.OWNER;
                boolean isCanCreate = model.isAdmin() || model.isOwner() || model.isMasker();
                UploadPhotoActivity.start(this, typeEnum, model.getId(), isCanCreate, 222);
                break;
            case R.id.title_left:
                finish();
                break;
        }
    }


    private AlbumAuthorityModel model;
    private ActionTypeEnum albumEnum;
    private boolean mIsShowNewPhotoOnly;
    private AlbumFragment mAlbumFragment;
    private UploadImgFragment mUploadImgFragment;
    private List<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 相册中心通用入口
     *
     * @param activity
     * @param albumEnum   相册类型
     * @param model       相册权限实体类
     * @param requestCode
     */
    public static void start(Activity activity, ActionTypeEnum albumEnum, AlbumAuthorityModel model, int requestCode, boolean isShowNewPhotoOnly) {
        Intent intent = new Intent(activity, AlbumsCenterActivity.class);
        intent.putExtra("albumEnum", albumEnum);
        intent.putExtra("model", model);
        intent.putExtra(TransferKeys.SHOW_PHOTO_ONLY, isShowNewPhotoOnly);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        /**
         * 全屏不重绘
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return R.layout.activity_albums_center;
    }

    @Override
    protected void init() {
        initIntent();
    }

    private void initIntent() {
        model = ((AlbumAuthorityModel) getIntent().getSerializableExtra("model"));
        albumEnum = ((ActionTypeEnum) getIntent().getSerializableExtra("albumEnum"));
        mIsShowNewPhotoOnly = getIntent().getBooleanExtra(TransferKeys.SHOW_PHOTO_ONLY, false);
    }

    @Override
    public void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_layout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        params.topMargin = DisplayUtils.getStatusHeight(this);
        linearLayout.setLayoutParams(params);
        initTitle();
        if (mIsShowNewPhotoOnly) {
            mNewLayout.setVisibility(View.VISIBLE);
            mRadioGroup.setVisibility(View.GONE);
        } else {
            mNewLayout.setVisibility(View.GONE);
            mRadioGroup.setVisibility(View.VISIBLE);
        }
        initRadio();
        initFragment();
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               switch (position){
                   case 0:
                       mRadioGroup.check(R.id.rb_upload);
                       break;
                   case 1:
                       mRadioGroup.check(R.id.rb_album);
                       break;
               }

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    private void initRadio() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_upload:
                        mUpload.setTextColor(getResources().getColor(R.color.white));
                        mAlbum.setTextColor(getResources().getColor(R.color.green_color));
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_album:
                        mUpload.setTextColor(getResources().getColor(R.color.green_color));
                        mAlbum.setTextColor(getResources().getColor(R.color.white));
                        mViewPager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    private class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    private void initTitle() {
        mTitleRightImg.setText(R.string.icon_upload2);
        mTitleRightImg.setTextColor(getResources().getColor(R.color.green_color));
        switch (albumEnum) {
            case OWNER:
                mTitle.setText("个人相册");
                if (!model.isOwner()) {
                    mTitleRight.setVisibility(View.GONE);
                }
                break;

            case CLASS:
                mTitle.setText("班级相册");
                if (!model.isMember()) {
                    mTitleRight.setVisibility(View.GONE);
                }
                break;
            case GROUP:
                mTitle.setText("社群相册");
                if (!model.isMember()) {
                    mTitleRight.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        if (mAlbumFragment == null) {
            mAlbumFragment = AlbumFragment.getAlbumFragment(albumEnum, model);
        }
        if (mUploadImgFragment==null){
            mUploadImgFragment = UploadImgFragment.getUploadFragment(albumEnum,model);
        }
        mFragments.add(mUploadImgFragment);
        mFragments.add(mAlbumFragment);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //getSupportFragmentManager().findFragmentById(R.id.layout).onActivityResult(requestCode, resultCode, data);
        mAlbumFragment.getAlbums(0,8);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonGlideImageLoader.getInstance().clearMemory(this);
    }
}
