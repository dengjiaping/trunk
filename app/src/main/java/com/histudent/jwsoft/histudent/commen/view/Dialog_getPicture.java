package com.histudent.jwsoft.histudent.commen.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.listener.MyDialogListener;
import com.histudent.jwsoft.histudent.body.mine.adapter.MyDialogAdapter;

/**
 * 获取照片
 * Created by liuguiyu-pc on 2016/8/5.
 */
public class Dialog_getPicture extends Dialog {

    private String title;
    private String[] datas;
    private TextView titleView;
    private ListView listView;
    private Context context;
    private MyDialogListener listener;

    public Dialog_getPicture(Context context, String title, String[] datas, MyDialogListener listener) {
        super(context);

        this.context = context;
        this.title = title;
        this.datas = datas;
        this.listener = listener;
    }

    public Dialog_getPicture(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Dialog_getPicture(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mydialog_layout);

        titleView = (TextView) findViewById(R.id.dialog_titile);
        listView = (ListView) findViewById(R.id.dialog_list);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }

        if (datas != null) {
            MyDialogAdapter arrayAdapter = new MyDialogAdapter(context, datas);
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dismiss();
                listener.callBack(position);

            }
        });

    }

    @Override
    public void show() {

//        WindowManager.LayoutParams attributes = this.getWindow().getAttributes();
//        DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
//        attributes.width = (int) (metrics.widthPixels * 0.5);
//        attributes.height = (int) (metrics.heightPixels * 0.5);
//        attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        attributes.dimAmount = 0.5f;
//        this.getWindow().setAttributes(attributes);

        super.show();
    }
}


