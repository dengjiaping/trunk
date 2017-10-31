package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;

/**
 * Created by ZhangYT on 2017/5/4.
 */

public class PopWindowUtis {


    private PopupWindow popupWindow;


    private void initPopWindow(final Activity activity, View view, final IconView iconView) {
        View layout = View.inflate(activity, R.layout.same_city_popwindow, null);
        final ListView listView = ((ListView) layout.findViewById(R.id.list_view));

        popupWindow = new PopupWindow(layout, SystemUtil.getScreenWind(), LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.hiworld_fram_gb));
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.verticalMargin =SystemUtil.dp2px(45);
        lp.alpha = .3f;
        activity.getWindow().setAttributes(lp);

        int xoff = SystemUtil.getScreenWind() / 2 - 120;
        popupWindow.showAsDropDown(view, xoff, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iconView.setText(R.string.icon_arrowbottom);
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity. getWindow().setAttributes(lp);
            }
        });

    }
}
