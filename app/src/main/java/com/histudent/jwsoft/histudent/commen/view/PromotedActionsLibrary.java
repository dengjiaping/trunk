package com.histudent.jwsoft.histudent.commen.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.util.ArrayList;


public class PromotedActionsLibrary {

    Context context;

    FrameLayout frameLayout, frameLayout_;

    IconView mainImageButton;

    RotateAnimation rotateOpenAnimation;

    RotateAnimation rotateCloseAnimation;

    ArrayList<TextView> promotedActions;

    ObjectAnimator objectAnimator[];

    private int px;

    private static final int ANIMATION_TIME = 100;

    private boolean isMenuOpened;
    private boolean isPlaying;

    public void setup(Context activityContext, FrameLayout layout, FrameLayout layout_) {
        context = activityContext;
        promotedActions = new ArrayList<>();
        frameLayout = layout;
        frameLayout_ = layout_;
        px = SystemUtil.dip2px(activityContext, 48);
        openRotation();
        closeRotation();
    }

    public IconView addMainItem(int id) {

        final IconView button = (IconView) LayoutInflater.from(context).inflate(R.layout.main_promoted_action_button, frameLayout, false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPlaying) return;

                isPlaying = true;

                if (isMenuOpened) {
                    closePromotedActions().start();
                    isMenuOpened = false;

                } else {
                    frameLayout_.setVisibility(View.VISIBLE);
                    openPromotedActions().start();
                    isMenuOpened = true;

                }
            }
        });

        frameLayout_.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isMenuOpened) {
                    closePromotedActions().start();
                    isMenuOpened = false;
                    return true;
                } else {
                    return false;
                }
            }
        });

        frameLayout.addView(button);

        mainImageButton = button;

        return button;
    }

    public void addItem(int id, int bg, String str, View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.promoted_action_button, frameLayout, false);

        TextView button1 = ((TextView) view.findViewById(R.id.iv_btn01));
        IconView button2 = ((IconView) view.findViewById(R.id.iv_btn02));

        if (str != null) {
            button1.setText(str);
        }

        button2.setText(id);
        button2.setBackgroundResource(bg);
        button2.setOnClickListener(onClickListener);

        promotedActions.add(button1);
        promotedActions.add(button2);
        frameLayout.addView(view);
    }

    /**
     * Set close animation for promoted actions
     */
    public AnimatorSet closePromotedActions() {

        if (objectAnimator == null) {
            objectAnimatorSetup();
        }

        AnimatorSet animation = new AnimatorSet();

        for (int i = 0; i < promotedActions.size(); i++) {

            objectAnimator[i] = setCloseAnimation(promotedActions.get(i), i, promotedActions.size() / 2);
        }
        ObjectAnimator objectAnimator_ = ObjectAnimator.ofFloat(frameLayout_, "alpha", 1f, 0f);
        objectAnimator_.setRepeatCount(0);
        objectAnimator_.setDuration(ANIMATION_TIME * 4);
        objectAnimator[promotedActions.size()] = objectAnimator_;

        if (objectAnimator.length == 0) {
            objectAnimator = null;
        }

        animation.playTogether(objectAnimator);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                mainImageButton.startAnimation(rotateCloseAnimation);

                for (TextView button : promotedActions) {
                    button.setClickable(false);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mainImageButton.setClickable(true);
                isPlaying = false;
                hidePromotedActions();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        return animation;
    }

    public AnimatorSet openPromotedActions() {

        if (objectAnimator == null) {
            objectAnimatorSetup();
        }

        AnimatorSet animation = new AnimatorSet();

        for (int i = 0; i < promotedActions.size(); i++) {
            objectAnimator[i] = setOpenAnimation(promotedActions.get(i), i, promotedActions.size() / 2);
        }

        ObjectAnimator objectAnimator_ = ObjectAnimator.ofFloat(frameLayout_, "alpha", 0f, 1f);
        objectAnimator_.setRepeatCount(0);
        objectAnimator_.setDuration(ANIMATION_TIME * 4);
        objectAnimator_.start();
        objectAnimator[promotedActions.size()] = objectAnimator_;

        if (objectAnimator.length == 0) {
            objectAnimator = null;
        }

        animation.playTogether(objectAnimator);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mainImageButton.startAnimation(rotateOpenAnimation);
                showPromotedActions();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mainImageButton.setClickable(true);
                isPlaying = false;
                for (TextView button : promotedActions) {
                    button.setClickable(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });


        return animation;
    }

    private void objectAnimatorSetup() {

        objectAnimator = new ObjectAnimator[promotedActions.size() + 1];
    }

    /**
     * Set close animation for single button
     *
     * @param promotedAction
     * @param position
     * @return objectAnimator
     */
    private ObjectAnimator setCloseAnimation(TextView promotedAction, int position, int n) {

        ObjectAnimator objectAnimator = null;

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            switch (position) {
                case 0:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", -SystemUtil.dip2px(context, 120), 0f),
                            PropertyValuesHolder.ofFloat("translationY", -SystemUtil.dip2px(context, 200), 0f));
                    break;
                case 1:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", -SystemUtil.dip2px(context, 120), 0f),
                            PropertyValuesHolder.ofFloat("translationY", -SystemUtil.dip2px(context, 200), 0f),
                            PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f),
                            PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f));
                    break;
                case 2:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationY", -SystemUtil.dip2px(context, 200), 0f));
                    break;
                case 3:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationY", -SystemUtil.dip2px(context, 200), 0f),
                            PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f),
                            PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f));
                    break;
                case 4:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", SystemUtil.dip2px(context, 120), 0f),
                            PropertyValuesHolder.ofFloat("translationY", -SystemUtil.dip2px(context, 200), 0f));
                    break;
                case 5:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", SystemUtil.dip2px(context, 120), 0f),
                            PropertyValuesHolder.ofFloat("translationY", -SystemUtil.dip2px(context, 200), 0f),
                            PropertyValuesHolder.ofFloat("scaleX", 1.5f, 1f),
                            PropertyValuesHolder.ofFloat("scaleY", 1.5f, 1f));
                    break;
            }
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * 4);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_X, -px * (promotedActions.size() - position), 0f);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (promotedActions.size() - position));
        }
        return objectAnimator;
    }

    /**
     * Set open animation for single button
     *
     * @param promotedAction
     * @param position
     * @return objectAnimator
     */
    private ObjectAnimator setOpenAnimation(TextView promotedAction, int position, int n) {

        ObjectAnimator objectAnimator = null;

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            switch (position) {
                case 0:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", 0f, -SystemUtil.dip2px(context, 120)),
                            PropertyValuesHolder.ofFloat("translationY", 0f, -SystemUtil.dip2px(context, 200)));
                    break;
                case 1:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", 0f, -SystemUtil.dip2px(context, 120)),
                            PropertyValuesHolder.ofFloat("translationY", 0f, -SystemUtil.dip2px(context, 200)),
                            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f),
                            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f));

                    break;
                case 2:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationY", 0f, -SystemUtil.dip2px(context, 200)));
                    break;
                case 3:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationY", 0f, -SystemUtil.dip2px(context, 200)),
                            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f),
                            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f));
                    break;
                case 4:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", 0f, SystemUtil.dip2px(context, 120)),
                            PropertyValuesHolder.ofFloat("translationY", 0f, -SystemUtil.dip2px(context, 200)));
                    break;
                case 5:
                    objectAnimator = ObjectAnimator.ofPropertyValuesHolder(promotedAction,
                            PropertyValuesHolder.ofFloat("translationX", 0f, SystemUtil.dip2px(context, 120)),
                            PropertyValuesHolder.ofFloat("translationY", 0f, -SystemUtil.dip2px(context, 200)),
                            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f),
                            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f));
                    break;
            }
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * 4);

        } else {
            objectAnimator = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_X, 0f, -px * (promotedActions.size() - position));
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (promotedActions.size() - position));
        }

        return objectAnimator;
    }

    private void hidePromotedActions() {

        for (int i = 0; i < promotedActions.size(); i++) {
            promotedActions.get(i).setVisibility(View.GONE);
        }
    }

    private void showPromotedActions() {

        for (int i = 0; i < promotedActions.size(); i++) {
            promotedActions.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void openRotation() {

        rotateOpenAnimation = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateOpenAnimation.setFillAfter(true);
        rotateOpenAnimation.setFillEnabled(true);
        rotateOpenAnimation.setDuration(ANIMATION_TIME);
    }

    private void closeRotation() {
        rotateCloseAnimation = new RotateAnimation(45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateCloseAnimation.setFillAfter(true);
        rotateCloseAnimation.setFillEnabled(true);
        rotateCloseAnimation.setDuration(ANIMATION_TIME);
    }

    public void CloseRotaion() {
        closePromotedActions().start();
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
        isMenuOpened = false;
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
}
