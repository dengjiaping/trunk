package com.histudent.jwsoft.histudent.wxapi;

import android.content.Intent;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * Created by liuguiyu-pc on 2017/1/11.
 */

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
