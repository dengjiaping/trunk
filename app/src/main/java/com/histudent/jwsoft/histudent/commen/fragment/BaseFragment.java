package com.histudent.jwsoft.histudent.commen.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
        initData();
    }

    protected void init(){

    }



    /**
     * 初始化控件
     */
    public void initView() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 发送消息
     */
    public void sendMessage(Handler handler, int what, int arg1, Object obj) {

        Message message = handler.obtainMessage();
        message.what = what;
        message.arg1 = arg1;
        message.obj = obj;
        handler.sendMessage(message);

    }

    /**
     * 修改Button的背景与可否点击
     *
     * @param codeText
     * @param flag
     */
    public void exchangeButtonBG(Button codeText, boolean flag) {
        codeText.setEnabled(flag);
        if (flag) {
            codeText.setBackgroundResource(R.drawable.green_button_bg);
        } else {
            codeText.setBackgroundResource(R.drawable.gray_button_bg);
        }
    }

}
