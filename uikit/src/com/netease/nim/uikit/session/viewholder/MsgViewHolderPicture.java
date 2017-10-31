package com.netease.nim.uikit.session.viewholder;

import android.content.Intent;

import com.netease.nim.uikit.R;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderPicture extends MsgViewHolderThumbBase {

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_picture;
    }

    @Override
    protected void onItemClick() {
        Intent mIntent = new Intent("com.netease.nim.uikit.session.viewholder.check_bigimg");
        mIntent.putExtra("message", message);
        context.sendBroadcast(mIntent);
    }

    @Override
    protected String thumbFromSourceFile(String path) {
        return path;
    }
}
