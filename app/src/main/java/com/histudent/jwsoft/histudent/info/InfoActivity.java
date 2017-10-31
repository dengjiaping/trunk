package com.histudent.jwsoft.histudent.info;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

/**
 * Created by liuguiyu-pc on 2016/12/28.
 */

public abstract class InfoActivity extends BaseActivity {

    private PopupWindow mPopupWindow;
    private TextView button;

    /**
     * 扫码访问班级或者个人主页时，弹出的挡板设置
     *
     * @param activity
     * @param buttonName
     * @param handler
     * @param what
     */
    public void showCodeApply(Activity activity, String buttonName, final Handler handler, final int what) {
//        View view = LayoutInflater.from(activity).inflate(R.layout.popupwindow_codeapply, null);
//        mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        button = (TextView) activity.findViewById(R.id.btn_join);
        button.setVisibility(View.VISIBLE);
//        mPopupWindow.setContentView(view);
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setTouchable(true);
//        button.setText(buttonName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mPopupWindow.dismiss();
                handler.sendEmptyMessage(what);
            }
        });
//        mPopupWindow.showAtLocation(activity.findViewById(R.id.title_left), Gravity.CENTER, 0, 0);
    }

}
