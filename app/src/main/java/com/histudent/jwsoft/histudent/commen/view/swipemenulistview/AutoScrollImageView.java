package com.histudent.jwsoft.histudent.commen.view.swipemenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;

import java.util.Arrays;
import java.util.List;

public class AutoScrollImageView extends LinearLayout {
    private Context mContext;
    private ImageView textViews[] = new ImageView[3];

    private LinearLayout llayout;

    private String curText = null;

    /***
     * ����ʱ��
     */
    private int mAnimTime = 500;

    /**
     * ͣ��ʱ��
     */
    private int mStillTime = 500;

    /***
     * �ֲ���string
     */
    private List<String> mTextList;

    /***
     * ��ǰ�ֲ�������
     */
    private int currentIndex = 0;

    /***
     * ����ģʽ
     */
    private int animMode = ANIM_MODE_UP;// Ĭ������ 0--���ϣ�1--����

    public final static int ANIM_MODE_UP = 0;
    public final static int ANIM_MODE_DOWN = 1;

    private TranslateAnimation animationDown, animationUp;

    public AutoScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    private void initViews() {
        llayout = new LinearLayout(mContext);
        llayout.setOrientation(LinearLayout.VERTICAL);
        this.addView(llayout);

        textViews[0] = addImageView();
        textViews[1] = addImageView();
        textViews[2] = addImageView();
    }

    /***
     * ����������ʱ
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoScroll();// ��ֹ�ڴ�й©�Ĳ���
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setViewsHeight();
    }

    /***
     * ��������VIEW�ĸ߶�
     */
    private void setViewsHeight() {
        for (ImageView tv : textViews) {
            LayoutParams lp = (LayoutParams) tv.getLayoutParams();
            lp.height = getHeight();
            lp.width = getWidth();
            tv.setLayoutParams(lp);
        }

        LayoutParams lp2 = (LayoutParams) llayout.getLayoutParams();
        lp2.height = getHeight() * (llayout.getChildCount());
        lp2.setMargins(0, -getHeight(), 0, 0);// ʹ����ƫ��һ���ĸ߶ȣ���padding,scrollTo����������
        llayout.setLayoutParams(lp2);
    }

    // /////////////////////������һЩ�����ķ���textViewҪ�õ�///////////////////////////////////

//	public void setGravity(int graty) {
//		for (ImageView tv : textViews) {
//			tv.setGravity(graty);
//		}
//	}

//	public void setTextSize(int dpSize) {
//		for (ImageView tv : textViews) {
//			tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dpSize);
//		}
//	}

//	public void setTextColor(int color) {
//		for (TextView tv : textViews) {
//			tv.setTextColor(color);
//		}
//	}

    private ImageView addImageView() {
        ImageView tv = new ImageView(mContext);
//		tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        llayout.addView(tv);
        return tv;
    }

    /***
     * ���ó�ʼ����
     *
     * @param curText
     */
    public void setText(String curText) {
        this.curText = curText;

        MyImageLoader.getIntent().displayNetImage(mContext, curText, textViews[1]);
    }

    /***
     * ��ʼ�Զ�����
     */
    public void startAutoScroll() {
        if (mTextList == null || mTextList.size() == 0) {
            return;
        }
        // ��ֹͣ
        stopAutoScroll();
        this.postDelayed(runnable, mStillTime);// ����runnable������hander���� timer
    }

    /***
     * ֹͣ�Զ�����
     */
    public void stopAutoScroll() {
        this.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentIndex = (currentIndex) % mTextList.size();
            switch (animMode) {
                case ANIM_MODE_UP:
                    setTextUpAnim(mTextList.get(currentIndex));
                    break;
                case ANIM_MODE_DOWN:
                    setTextDownAnim(mTextList.get(currentIndex));
                    break;
            }

            currentIndex++;
            AutoScrollImageView.this.postDelayed(runnable, mStillTime + mAnimTime);

        }
    };

    /***
     * ���ϵ�����
     *
     * @param
     */
    public void setTextUpAnim(String text) {
        this.curText = text;

        MyImageLoader.getIntent().displayNetImage(mContext, text, textViews[2]);

        up();// ���ϵĶ���
    }

    public void setTextDownAnim(String text) {
        this.curText = text;
        MyImageLoader.getIntent().displayNetImage(mContext, text, textViews[0]);
        down();// ���ϵĶ���
    }

    public void setDuring(int during) {
        this.mAnimTime = during;
    }

    /***
     * ���϶���
     */
    private void up() {
        llayout.clearAnimation();
        if (animationUp == null)
            animationUp = new TranslateAnimation(0, 0, 0, -getHeight());
        animationUp.setDuration(mAnimTime);
        llayout.startAnimation(animationUp);
        animationUp.setAnimationListener(listener);

    }

    /***
     * ���¶���
     */
    public void down() {
        llayout.clearAnimation();
        if (animationDown == null)
            animationDown = new TranslateAnimation(0, 0, 0, getHeight());
        animationDown.setDuration(mAnimTime);
        llayout.startAnimation(animationDown);
        animationDown.setAnimationListener(listener);
    }

    /***
     * ����������������ɺ󣬶����ָ��������ı�
     */
    private AnimationListener listener = new AnimationListener() {

        @Override
        public void onAnimationStart(Animation arg0) {
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            clearAnimation();
            setText(curText);
        }
    };

    public int getAnimTime() {
        return mAnimTime;
    }

    public void setAnimTime(int mAnimTime) {
        this.mAnimTime = mAnimTime;
    }

    public int getStillTime() {
        return mStillTime;
    }

    public void setStillTime(int mStillTime) {
        this.mStillTime = mStillTime;
    }

    public List<String> getTextList() {
        return mTextList;
    }

    public void setTextList(List<String> mTextList) {
        this.mTextList = mTextList;
    }

    public void setTextList(String[] mTextList) {
        this.mTextList = Arrays.asList(mTextList);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getAnimMode() {
        return animMode;
    }

    public void setAnimMode(int animMode) {
        this.animMode = animMode;
    }

}
