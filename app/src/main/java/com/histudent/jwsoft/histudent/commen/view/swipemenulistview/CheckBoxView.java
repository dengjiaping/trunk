package com.histudent.jwsoft.histudent.commen.view.swipemenulistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.IconView;

/**
 * Created by ZhangYT on 2017/5/11.
 */

public class CheckBoxView extends IconView implements Checkable {

    CheckBoxView mView;
    private boolean isChecked;
    private setOnCheckedChangeListener listener;

    public CheckBoxView(Context context) {
        super(context);
        initData();
    }

    public CheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }


    public CheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }


    private void initData() {
        setTextColor(getResources().getColor(R.color.icon_color));
//        setTextSize(15);
    }


    public void setOnCheckChangeListener(setOnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
        if (listener!=null)
            listener.setOnCheckedChangeListener(this, isChecked);
        if (checked) {
            this.setTextColor(getResources().getColor(R.color.green_color));
        } else {
            this.setTextColor(getResources().getColor(R.color.icon_color));
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    public interface setOnCheckedChangeListener {
         void setOnCheckedChangeListener(View v, boolean isChecked);
    }

//    public void setBackground(Drawable drawable){
//
//    }
}
