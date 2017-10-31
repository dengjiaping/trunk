//package com.netease.nim.uikit.session.viewholder;
//
//import android.content.Intent;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.netease.nim.uikit.R;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
///**
// * Created by zhoujianghua on 2015/8/4.
// */
//public class MsgViewHolderHome extends MsgViewHolderBase {
//
//    RelativeLayout bodyTextView;
//    HomeAttachment attachment;
//
//    @Override
//    protected int getContentResId() {
//        return R.layout.nim_message_item_home;
//    }
//
//    @Override
//    protected void inflateContentView() {
//    }
//
//    @Override
//    protected void bindContentView() {
//        layoutDirection();
//        if (attachment == null)
//            attachment = (HomeAttachment) message.getAttachment();
//
//        TextView text_title = (TextView) bodyTextView.findViewById(R.id.title);
//        TextView text_content = (TextView) bodyTextView.findViewById(R.id.content);
//        final ImageView image = (ImageView) bodyTextView.findViewById(R.id.image);
//
//        text_title.setText(attachment.getTitle());
//        text_content.setText(attachment.getContent());
//
//        ImageLoader.getInstance().displayImage(attachment.getImag(), image);
//
//        bodyTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent("com.netease.nim.uikit.session.viewholder");
//                intent.putExtra("url", attachment.getUrl());
//                context.sendBroadcast(intent);
//            }
//        });
//
//    }
//
//    private void layoutDirection() {
//        bodyTextView = findViewById(R.id.homeWork);
//        if (isReceivedMessage()) {
//            bodyTextView.setGravity(Gravity.LEFT);
//        } else {
//            bodyTextView.setGravity(Gravity.RIGHT);
//        }
//    }
//
//    @Override
//    protected int leftBackground() {
//        return 0;
//    }
//
//    @Override
//    protected int rightBackground() {
//        return 0;
//    }
//
//}
