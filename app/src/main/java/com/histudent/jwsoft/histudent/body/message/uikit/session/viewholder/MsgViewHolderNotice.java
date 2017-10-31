package com.histudent.jwsoft.histudent.body.message.uikit.session.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.NoticeAttachment;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;

public class MsgViewHolderNotice extends MsgViewHolderBase {

    private TextView title_left, content_left, title_right, content_right;
    private LinearLayout layout_right, layout_left;
    private NoticeAttachment attachment;

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_notice;
    }

    @Override
    protected void inflateContentView() {
        title_left = findViewById(R.id.title_left);
        content_left = findViewById(R.id.content_left);
        title_right = findViewById(R.id.title_right);
        content_right = findViewById(R.id.content_right);
        layout_right = findViewById(R.id.layout_right);
        layout_left = findViewById(R.id.layout_left);
    }

    @Override
    protected void onItemClick() {
        if (attachment == null)
            attachment = (NoticeAttachment) message.getAttachment();
        Intent mIntent = new Intent(TheReceiverAction.GOTIO_NOTICE);
        mIntent.putExtra("msg", attachment.getMsg());
        context.sendBroadcast(mIntent);
    }

    @Override
    protected void bindContentView() {
        if (attachment == null)
            attachment = (NoticeAttachment) message.getAttachment();

        if (isReceivedMessage()) {
            layout_left.setVisibility(View.VISIBLE);
            layout_right.setVisibility(View.GONE);
            title_left.setText(attachment.getTitle());
            content_left.setText(attachment.getContent());
        } else {
            layout_left.setVisibility(View.GONE);
            layout_right.setVisibility(View.VISIBLE);
            title_right.setText(attachment.getTitle());
            content_right.setText(attachment.getContent());
        }

    }

}

