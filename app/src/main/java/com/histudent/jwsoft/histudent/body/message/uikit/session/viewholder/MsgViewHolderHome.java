package com.histudent.jwsoft.histudent.body.message.uikit.session.viewholder;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.body.message.uikit.model.MsgModel;
import com.histudent.jwsoft.histudent.body.message.uikit.session.extension.HomeAttachment;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderHome extends MsgViewHolderBase {

    RelativeLayout bodyTextView;
    HomeAttachment attachment;

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_home;
    }

    @Override
    protected void inflateContentView() {
    }

    @Override
    protected void bindContentView() {
        layoutDirection();
        if (attachment == null)
            attachment = (HomeAttachment) message.getAttachment();

        TextView text_title = (TextView) bodyTextView.findViewById(R.id.title);
        TextView text_content = (TextView) bodyTextView.findViewById(R.id.content);
        final ImageView image = (ImageView) bodyTextView.findViewById(R.id.image);

        MsgModel msgModel = attachment.getMsgModel();

        if (msgModel == null) return;

        text_title.setText(msgModel.getData().getTitle());
        text_content.setText(msgModel.getData().getContent());

        ImageLoader.getInstance().displayImage(msgModel.getData().getImg(), image);

        bodyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(msgModel);
            }
        });

    }

    private void layoutDirection() {
        bodyTextView = findViewById(R.id.homeWork);
        if (isReceivedMessage()) {
            bodyTextView.setGravity(Gravity.LEFT);
        } else {
            bodyTextView.setGravity(Gravity.RIGHT);
        }
    }

    @Override
    protected int leftBackground() {
        return 0;
    }

    @Override
    protected int rightBackground() {
        return 0;
    }

}
