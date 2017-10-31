package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by huyg on 2017/6/27.
 */

public class FixedEditText extends AppCompatEditText {

    private String fixStr;

    public FixedEditText(Context context) {
        super(context);
    }

    public FixedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFixStr(String fixStr) {
        this.fixStr = fixStr;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (fixStr == null)
            return;
        if (selStart < fixStr.length()) {

            setSelection(getText().toString().length());
        }
    }

}
