package com.histudent.jwsoft.histudent.view.fragment.image;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.taobao.accs.ACCSManager.mContext;


public class ImageFragment extends Fragment {


    @BindView(R.id.head_big_image)
    RelativeLayout mLayout;
    @BindView(R.id.img_photoview)
    ImageView mPhotoView;
    @BindView(R.id.progressBar)
    ProgressBar mProgress;

    private PhotoViewAttacher mAttacher;
    private View rootView;
    private final String TAG = getClass().getSimpleName();


    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 缩略图宽高
     */
    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;

    /**
     * 缩略图左上角坐标
     */
    private int x;
    private int y;
    /**
     * 对应下标
     */
    private int position;

    /**
     * 是否有其他操作
     */
    private boolean isOperate;


    /**
     * 动画时长
     */
    private final int NORMAL_SCALE_DURATION = 300;

    /**
     * 背景透明度渐变动画
     */
    private AlphaAnimation bgAlphaAnimation;

    /**
     * 值变换动画
     */
    private ValueAnimator valueAnimator;


    /**
     * 构造方法
     *
     * @param imgUrl    图片url
     * @param width     缩略图宽度
     * @param height    缩略图高度
     * @param position  缩略图所在下标
     * @param x         缩略图左上角x坐标
     * @param y         缩略图左上角y坐标
     * @param isOperate 是否有其他操作
     * @return
     */
    public static ImageFragment newInstance(String imgUrl, int width,
                                            int height, int position, int x, int y, boolean isOperate) {
        final ImageFragment f = new ImageFragment();
        final Bundle args = new Bundle();
        args.putSerializable("imgUrl", imgUrl);
        args.putSerializable("width", width);
        args.putSerializable("height", height);
        args.putSerializable("position", position);
        args.putSerializable("x", x);
        args.putSerializable("y", y);
        args.putSerializable("isOperate", isOperate);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        showPhoto();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_image_big, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intOther();
        initArguments();
        initView();
        setListener();

    }

    private void initView() {
        mPhotoView.setDrawingCacheEnabled(true);
        mAttacher = new PhotoViewAttacher(mPhotoView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void initArguments() {
        imageUrl = getArguments() != null ? getArguments().getString("imgUrl") : null;
        width = getArguments() != null ? getArguments().getInt("width") : 50;
        height = getArguments() != null ? getArguments().getInt("height") : 50;
        position = getArguments() != null ? getArguments().getInt("position") : 0;
        x = getArguments() != null ? getArguments().getInt("x") : DisplayUtils.getScreenWidthPixels(getActivity()) / 3;
        y = getArguments() != null ? getArguments().getInt("y") : DisplayUtils.getScreenHeightPixels(getActivity()) / 3;
        isOperate = getArguments() != null && getArguments().getBoolean("isOperate");
    }


    private void intOther() {
        mContext = getActivity();
        screenWidth = DisplayUtils.getScreenWidthPixels(getActivity());
        screenHeight = DisplayUtils.getScreenHeightPixels(getActivity());

    }

    /**
     * 展示图片
     */
    public void showPhoto() {

        /**
         * 第一张图未加载出来背景透明
         */
        if (position == ((ShowImageActivity) mContext).getPositon() && ((ShowImageActivity) mContext).isFirst()) {
            mLayout.setBackgroundColor(Color.TRANSPARENT);
        } else {
            mLayout.setBackgroundColor(Color.BLACK);
        }


        RelativeLayout.LayoutParams progressBarParams = (RelativeLayout.LayoutParams) mProgress.getLayoutParams();
        progressBarParams.width = DisplayUtils.dp2px(getActivity(), 40);
        progressBarParams.height = DisplayUtils.dp2px(getActivity(), 40);
        progressBarParams.topMargin = (screenHeight - progressBarParams.height) / 2;
        progressBarParams.leftMargin = (screenWidth - progressBarParams.width) / 2;
        mProgress.setLayoutParams(progressBarParams);
        displayImage();
    }


    private void displayImage() {
        Glide.with(getActivity())
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(getActivity(), "获取图片失败", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//						if (screenHeight >= resource.getIntrinsicHeight()) {//长图
//							mAttacher.setScaleType(ScaleType.CENTER_INSIDE );
//						}else {
//							mAttacher.setScaleType(ScaleType.CENTER_CROP);
//						}
                        fullScreen(x, y, false);
                        return false;
                    }
                })
                .transition(CommonGlideImageLoader.DRAWABLE_TRANSITION_OPTIONS)
                .into(mPhotoView);

    }

    /**
     * 第一次加载背景透明度渐变
     */
    public void setBgAlphaAnimation() {
        if (position == ((ShowImageActivity) mContext).getPositon() && ((ShowImageActivity) mContext).isFirst()) {
            ((ShowImageActivity) mContext).setFirst(false);
            mLayout.setBackgroundColor(Color.BLACK);
            bgAlphaAnimation = new AlphaAnimation(0, (float) 1);
            bgAlphaAnimation.setDuration(NORMAL_SCALE_DURATION);
            bgAlphaAnimation.setFillAfter(true);
            mLayout.startAnimation(bgAlphaAnimation);

        }
    }


    /**
     * photoview当前位置放大到全屏的动画
     * 通过改变photoview 的 topMargin ，leftMargin，height，width
     *
     * @param x           photoview的左上角x坐标
     * @param y           photoview的左上角y坐标
     * @param from_center 是否从中心来的
     */
    public void fullScreen(final int x, final int y, boolean from_center) {//int x, int y,int width,int hight

        setBgAlphaAnimation();
        valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPhotoView.getLayoutParams();
                params.height = mEvaluator.evaluate(fraction, height, screenHeight);//从当前缩略图的高度到满屏
                params.width = mEvaluator.evaluate(fraction, width, screenWidth);//从当前缩略图的宽度到满屏
                params.topMargin = mEvaluator.evaluate(fraction, y, 0);//从photoview的y到0
                params.leftMargin = mEvaluator.evaluate(fraction, x, 0);//从photoview的x到0
                mPhotoView.setLayoutParams(params);


            }
        });
        if (((ShowImageActivity) mContext).getPositon() == position
                ) {
            valueAnimator.setDuration((long) (NORMAL_SCALE_DURATION));
        } else {
            valueAnimator.setDuration((long) (100));
        }
        valueAnimator.start();
    }


    /**
     * 关闭动画
     */
    public void close() {
        /**
         * 背景透明
         */
        mLayout.setBackgroundColor(Color.TRANSPARENT);
        mPhotoView.setBackgroundColor(Color.TRANSPARENT);
        ((ShowImageActivity) mContext).finishAct();
        /*if (closeScaleType == 1) {
            mAttacher.setScaleType(ScaleType.CENTER_INSIDE);
		}else {
			mAttacher.setScaleType(ScaleType.CENTER_CROP);
		}*/
        final int closeHeight = mPhotoView.getWidth();//结束时的高度以photoView 加载的bitmap高度为准
        final int closeMarginTop = (screenHeight - mPhotoView.getHeight()) / 2;

        valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPhotoView.getLayoutParams();
                params.height = mEvaluator.evaluate(fraction, closeHeight, height);//从当前满屏到缩略图的高度
                params.width = mEvaluator.evaluate(fraction, screenWidth, width);//从当前满屏到缩略图的宽度
                params.topMargin = mEvaluator.evaluate(fraction, closeMarginTop, y);//从bitmap的左上角y坐标到缩略图的y
                params.leftMargin = mEvaluator.evaluate(fraction, 0, x);//从bitmap的左上角x坐标到缩略图的x
                mPhotoView.setLayoutParams(params);
                Log.d("test", "height----> " + params.height + "width---->" + params.width + "top--->" + params.topMargin + "left--->" + params.leftMargin);

            }
        });
        valueAnimator.setDuration((long) (NORMAL_SCALE_DURATION));
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

//        valueAnimator.start();


    }


    private void setListener() {
        // TODO Auto-generated method stub
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View view, float x, float y) {
                // TODO Auto-generated method stub
                finishFlash();
            }
        });
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

            @Override
            public void onViewTap(View view, float x, float y) {
                // TODO Auto-generated method stub
                finishFlash();
            }
        });

        //长按保存
        mAttacher.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    public void finishFlash() {
        ((ShowImageActivity) mContext).setFinish(true);
        if (((ShowImageActivity) mContext).isAnimation() || position == ((ShowImageActivity) mContext).getPositon()) {
            close();
        } else {
            ((ShowImageActivity) mContext).finishAct();
        }
    }


}
