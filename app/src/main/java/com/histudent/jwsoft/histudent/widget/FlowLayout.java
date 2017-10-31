package com.histudent.jwsoft.histudent.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.keyword.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by huyg on 2017/10/30.
 * 流式布局，自动换行
 */

public class FlowLayout extends RelativeLayout {


    public static final int STYLE_TEXTVIEW = 1;
    public static final int STYLE_BUTTON = 2;
    public static final int STYLE_CHECKBOX = 3;
    private View mView;
    private Context mContext;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    // /*********************about measure onLayout*********************************************/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        1.measure children and  measure  self
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;


        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams childLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childMeasuredHeight = child.getMeasuredHeight() + childLayoutParams.bottomMargin + childLayoutParams.topMargin;
            int childMeasuredWidth = child.getMeasuredWidth() + childLayoutParams.leftMargin + childLayoutParams.rightMargin;

            if (lineWidth + childMeasuredWidth > widthSpecSize) {//The line is full
                width = Math.max(lineWidth, childMeasuredWidth);
                height = lineHeight + height;
                lineWidth = childMeasuredWidth;
                lineHeight = childMeasuredWidth;
            } else {
                //record line info
                lineWidth = lineWidth + childMeasuredWidth;
                lineHeight = Math.max(lineHeight, childMeasuredHeight);
            }

            if (i == childCount - 1) { //The last one
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }
//        2.set params
        setMeasuredDimension(widthSpecMode == MeasureSpec.EXACTLY ? widthSpecSize : width,
                heightSpecMode == MeasureSpec.EXACTLY ? heightSpecSize : height
        );

    }


    List<Integer> eachLineHeight = new ArrayList<>();
    List<List<View>> allViews = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        1.record how many views each row and   get max height of the row
        eachLineHeight.clear();
        allViews.clear();

        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childMeasuredHeight = child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;
            int childMeasuredWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;


            if (lineWidth + childMeasuredWidth > getWidth()) {//The line is full
                lineWidth = 0;
                eachLineHeight.add(lineHeight);
                allViews.add(lineViews);
                lineViews = new ArrayList<>();
            }//The line is not full


            lineHeight = Math.max(lineHeight, childMeasuredHeight);
            lineWidth = lineWidth + childMeasuredWidth;
            lineViews.add(child);

            if (i == childCount - 1) {//The last one
                eachLineHeight.add(lineHeight);
                allViews.add(lineViews);
            }

            final int finalI = i;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {// set callback
                        mCallback.onItemClick(finalI);
                    }
                }
            });
        }

        int left = 0;
        int top = 0;

        for (int i = 0; i < allViews.size(); i++) {

            List<View> views = allViews.get(i);
            lineHeight = eachLineHeight.get(i);

//        2. layout   row
            for (int j = 0; j < views.size(); j++) {
                View child = views.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();
                //calc childView's left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
                Point point = new Point();
                point.x = lc;
                point.y = tc;
                child.setTag(point);
            }
            top = top + lineHeight;
            left = 0;
        }
    }

    public void setData(List<String> lists, int type) {
        if (lists != null) {
            for (int i = 0; i < lists.size(); i++) {
                switch (type) {
                    case STYLE_TEXTVIEW:
                        break;
                    case STYLE_BUTTON:
                        break;
                    case STYLE_CHECKBOX:
                        initCheckBox(lists.get(i));
                        break;

                }
            }
        }
    }

    private void initCheckBox(String content) {
        mView = new CheckBox(mContext);
        ((CheckBox) mView).setButtonDrawable(null);
        ((CheckBox) mView).setText(content);
        ((CheckBox) mView).setTextSize(DisplayUtils.sp2px(mContext, 13));
        ((CheckBox) mView).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ((CheckBox) mView).setTextColor(Color.parseColor("#28ca7e"));
                    ((CheckBox) mView).setBackground(AppCompatResources.getDrawable(mContext, R.drawable.shape_comment_checked_true));
                } else {
                    ((CheckBox) mView).setTextColor(Color.parseColor("#b0b0b0"));
                    ((CheckBox) mView).setBackground(AppCompatResources.getDrawable(mContext, R.drawable.shape_comment_checked));
                }
            }
        });
        addView(mView);
    }


    //*********************about callback
    Callback mCallback;

    public void setOnItemClickCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClick(int position);
    }
}
