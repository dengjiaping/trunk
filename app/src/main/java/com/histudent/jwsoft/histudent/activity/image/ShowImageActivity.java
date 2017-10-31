package com.histudent.jwsoft.histudent.activity.image;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.ImageInfoBean;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.entity.ImageAttrEntity;
import com.histudent.jwsoft.histudent.fragment.image.ImageFragment;
import com.histudent.jwsoft.histudent.presenter.image.ShowImagePresenter;
import com.histudent.jwsoft.histudent.presenter.image.contract.ShowImageContract;
import com.histudent.jwsoft.histudent.widget.ImageViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/9/12.
 */

public class ShowImageActivity extends BaseActivity<ShowImagePresenter> implements ShowImageContract.View {

    @BindView(R.id.image_show_num)
    TextView mPageNum;
    @BindView(R.id.image_show_viewpager)
    ImageViewPager mViewPager;
    @BindView(R.id.cb_layout)
    LinearLayout mCbLayout;
    @BindView(R.id.cb_favor_image)
    IconView mFavor;
    @BindView(R.id.rb_comment_image)
    IconView mComment;
    @BindView(R.id.cb_favor_text)
    TextView mFavorNum;


    @OnClick({R.id.cb_favor_image, R.id.rb_comment_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_favor_image:
                likePhoto();
                break;
            case R.id.rb_comment_image:
                CommentActivity.start(ShowImageActivity.this, mImageAttrs.get(currentPosition).getId(),1);
                break;
        }
    }


    /**
     * 点击的item对应下标
     */
    private int positon;
    private ImagePagerAdapter adapter;
    /**
     * 是否需要关闭时的动画，（在聊天页面图片个数无上限且不确定图片个数的时候应该关闭）
     */
    private boolean animation = true;
    private int item_width = 50;
    private int item_hight = 50;
    public int x;// 首item x坐标
    public int y;// 首item y坐标
    public int horizontal_space;//水平间距
    public int vertical_space;//垂直间距
    public int column_num;//每行个数
    private boolean isOperate;
    /**
     * 记录当前IamgeFragment以及上一个，下一个，为了按返回键的时候能够调用当前amgeFragment关闭的动画
     */
    private int currentPosition = 0;
    private ImageFragment imageFragmentCurrent;
    private ImageFragment imageFragmentPre;
    private ImageFragment imageFragmentNext;

    private int width;
    private int height;
    private Context mContext;
    private List<ImageAttrEntity> mImageAttrs;
    private ImageInfoBean imageInfo;

    /**
     * 是否第一次展示
     */
    private boolean isFirst = true;


    @Override
    protected void init() {
        initIntent();
        initView();
        intOther();
        initPraise();
    }

    private void initPraise() {
        getPhotoInfo();
    }

    private void intOther() {
        mContext = ShowImageActivity.this;
        width = DisplayUtils.getScreenWidthPixels(ShowImageActivity.this);
        height = DisplayUtils.getScreenHeightPixels(ShowImageActivity.this);

    }

    private void initIntent() {
        // TODO Auto-generated method stub
        if (getIntent() != null) {

            item_width = getIntent().getIntExtra("width",
                    width * 2 / 5);
            item_hight = getIntent().getIntExtra("height",
                    height * 2 / 5);
            column_num = getIntent().getIntExtra("column_num", 1);
            horizontal_space = getIntent().getIntExtra("horizontal_space", 1);
            vertical_space = getIntent().getIntExtra("vertical_space", 1);
            x = getIntent()
                    .getIntExtra("x", width / 3);
            y = getIntent()
                    .getIntExtra("y", height / 3);
            positon = getIntent().getIntExtra("position", 0);
            isOperate = getIntent().getBooleanExtra("isOperate", false);
            currentPosition = positon;

            animation = getIntent().getBooleanExtra("animation", true);
            x = buildOriginXY(positon)[0];
            y = buildOriginXY(positon)[1];
            mImageAttrs = (List<ImageAttrEntity>) getIntent().getSerializableExtra("photos");
        } else {
            finishAct();
        }
    }


    private void initView() {
        mFavorNum.setVisibility(View.GONE);
        overridePendingTransition(0, 0);
        initViewPager();
    }

    private void initViewPager() {
        adapter = new ImagePagerAdapter(getSupportFragmentManager());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                initTitle(position);
                if (position + 1 == currentPosition) {
                    imageFragmentNext = imageFragmentCurrent;
                    imageFragmentCurrent = imageFragmentPre;
                } else if (position - 1 == currentPosition) {
                    imageFragmentPre = imageFragmentCurrent;
                    imageFragmentCurrent = imageFragmentNext;
                }
                currentPosition = position;
                getPhotoInfo();
                setPositon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(positon);
        initTitle(positon);
    }

    private void initTitle(int position) {
        if (mImageAttrs.size() == 1) {
            mPageNum.setVisibility(View.GONE);
        } else {
            mPageNum.setVisibility(View.VISIBLE);
            mPageNum.setText((position + 1) + "/" + mImageAttrs.size());
        }
    }

    @Override
    public void showContent(String message) {

    }

    @Override
    public void getPhotoInfo() {
        mPresenter.getImgPraiseInfo(5, mImageAttrs.get(currentPosition).getId());
    }

    @Override
    public void showPraiseInfo(ImageInfoBean imageInfo) {
        this.imageInfo = imageInfo;
        mFavorNum.setText(String.valueOf(imageInfo.getPraiseCount()));
        setPraiseBg(imageInfo.isPraise());

    }

    private void setPraiseBg(boolean isPraise) {
        mFavor.setText(isPraise ? R.string.icon_zan : R.string.icon_zannone);
        mFavor.setTextColor(isPraise ? getResources().getColor(R.color.green_color) : getResources().getColor(R.color.text_black_h2));
        mFavorNum.setTextColor(isPraise ? getResources().getColor(R.color.green_color) : getResources().getColor(R.color.text_black_h2));
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mImageAttrs == null ? 0 : mImageAttrs.size();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            ImageFragment imageFragment = ImageFragment.newInstance(mImageAttrs.get(position).getBigSizeUrl(),
                    item_width, item_hight, position, buildXY(position)[0],
                    buildXY(position)[1], isOperate);
            /**
             * 记录最多3个imageFragment
             */
            if (position == mViewPager.getCurrentItem()) {
                imageFragmentCurrent = imageFragment;
            } else if (position + 1 == mViewPager.getCurrentItem()) {
                imageFragmentPre = imageFragment;
            } else if (position - 1 == mViewPager.getCurrentItem()) {
                imageFragmentNext = imageFragment;
            }


            return imageFragment;
            // return fragments.get(position);
        }
    }


    /**
     * 照片点赞
     */
    private void likePhoto() {
        if (imageInfo.isPraise()) {
            mFavorNum.setText(String.valueOf(imageInfo.getPraiseCount() - 1));
        } else {
            mFavorNum.setText(String.valueOf(imageInfo.getPraiseCount() + 1));
        }
        setPraiseBg(!imageInfo.isPraise());
        mPresenter.praiseImg(!imageInfo.isPraise(), 5, mImageAttrs.get(currentPosition).getId());
    }


    private boolean isFinish = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /**
             * 防止连续点击返回键
             */
            if (isFinish) return true;
            isFinish = true;

            if (imageFragmentCurrent != null) {
                imageFragmentCurrent.finishFlash();
            } else {
                //showStatusBar();
                if (isAnimation() || currentPosition == getPositon()) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            finishAct();
                        }
                    }, 225);
                } else {
                    finishAct();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void finishAct() {
        mCbLayout.setVisibility(View.GONE);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {

        super.finish();
    }


    /**
     * 当前位置获取首item x,y
     *
     * @param position
     * @return
     */
    public int[] buildOriginXY(int position) {
        int[] location = new int[2];
        int buildX = x - (position % column_num)
                * (item_width + horizontal_space);
        int buildY = y - (position / column_num)
                * (item_hight + vertical_space);
        location[0] = buildX;
        location[1] = buildY;
        return location;
    }

    /**
     * 根据首item x,y获取当前item 的x,y
     *
     * @param position
     * @return
     */
    public int[] buildXY(int position) {
        int[] location = new int[2];
        int buildX = x + (position % column_num)
                * (item_width + horizontal_space);
        int buildY = y + (position / column_num)
                * (item_hight + vertical_space);
        location[0] = buildX;
        location[1] = buildY;
        return location;
    }


    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }


    public boolean isAnimation() {
        return animation;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {

        isFinish = finish;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_show_image;
    }
}
