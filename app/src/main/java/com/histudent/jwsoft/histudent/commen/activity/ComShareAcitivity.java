package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;

public class ComShareAcitivity extends BaseActivity implements View.OnClickListener {
    private MyShareBean shareBean;
    private static WVJBWebView.WVJBResponseCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static void start(Activity activity, MyShareBean shareBean, WVJBWebView.WVJBResponseCallback callback) {
        Intent intent = new Intent(activity, ComShareAcitivity.class);
        intent.putExtra("shareBean", shareBean);
        ComShareAcitivity.callback = callback;
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_com_share_acitivity;
    }

    @Override
    public void initView() {
        shareBean = (MyShareBean) getIntent().getSerializableExtra("shareBean");
        findViewById(R.id.weixin).setOnClickListener(this);
        findViewById(R.id.weixin_circle).setOnClickListener(this);
        findViewById(R.id.qq_cricle).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //微信
            case R.id.weixin:
                ShareUtils.share(this, shareBean, ShareUtils.WEIXIN);
                break;

            //微信圈
            case R.id.weixin_circle:
                ShareUtils.share(this, shareBean, ShareUtils.WEIXIN_CIRCLE);
                break;


            //qq好友圈
            case R.id.qq_cricle:
                ShareUtils.share(this, shareBean, ShareUtils.QZONE);
                break;

            //关闭
            case R.id.close:
                setResult();
                break;
        }
    }

    private void setResult() {
        if (ComShareAcitivity.callback != null && shareBean != null)
            callback.callback(shareBean.getShareObjectId());
        finish();
    }

}
