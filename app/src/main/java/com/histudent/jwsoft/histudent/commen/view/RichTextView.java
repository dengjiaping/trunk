package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.histudent.jwsoft.histudent.commen.bean.AtUserModel;
import com.histudent.jwsoft.histudent.commen.bean.TopicModel;
import com.histudent.jwsoft.histudent.comment2.utils.EmotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huyg on 2017/6/27.
 */

public class RichTextView extends android.support.v7.widget.AppCompatTextView {
    // 默认,话题文本高亮颜色
    private static final int FOREGROUND_COLOR = Color.parseColor("#2f4459");
    private List<Topic> mTopics;
    private List<User> mUsers;
    private static final String TAG = "RichTextView";
    private static final String AT = "@";
    private static final String POUND = "#";
    private RichTextClick mListener;

    public RichTextView(Context context) {
        super(context);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    private void init() {
        mTopics = new ArrayList<>();
        mUsers = new ArrayList<>();
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void addUser(AtUserModel atUserModel) {
        User user = new User();
        user.rule = AT;
        user.id = atUserModel.getUserId();
        user.content = AT + atUserModel.getRealName();
        user.name = atUserModel.getRealName();
        mUsers.add(user);
    }

    public void addTopic(TopicModel topicModel){
        Topic topic=new Topic();
        topic.rule = POUND;
        topic.content = POUND+ topicModel.getTopicName()+POUND;
        topic.hash = topicModel.getTopicName();
        topic.id = topicModel.getTopicId();
        mTopics.add(topic);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        if (TextUtils.isEmpty(text)) {
            return;
        }
        SpannableString spannableString = new SpannableString(text);
        int findPosition = 0;


        for (int i = 0; i < mUsers.size(); i++) {
            final User user = mUsers.get(i);
            String content = user.content;
            Pattern pattern=Pattern.compile(content);
            Matcher matcher=pattern.matcher(text);
            while (matcher.find()){
                spannableString.setSpan(new ClickATSpan(user) , matcher.start(), matcher.start()
                                + content.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        for (int i = 0; i < mTopics.size(); i++) {
            Topic topic = mTopics.get(i);
            String content = topic.content;
            Pattern pattern=Pattern.compile(content);
            Matcher matcher=pattern.matcher(text);
            while (matcher.find()){
                spannableString.setSpan(new ClickPOUNDSpan(topic) , matcher.start(), matcher.start()
                                + content.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        SpannableString spannable= EmotionUtils.convertNormalStringToSpannableString(spannableString);

        super.setText(spannable, type);
    }


    private class ClickATSpan extends ClickableSpan {
        private User user;

        public ClickATSpan(User user){
            this.user = user;
        }
        @Override
        public void onClick(View widget) {
           mListener.clickUser(user);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(FOREGROUND_COLOR);
            ds.setUnderlineText(false);
        }
    }
    private class ClickPOUNDSpan extends ClickableSpan {
        private Topic topic;
        public ClickPOUNDSpan(Topic topic){
            this.topic = topic;
        }
        @Override
        public void onClick(View widget) {
            mListener.clickTopic(topic);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(FOREGROUND_COLOR);
            ds.setUnderlineText(false);
        }
    }

    public interface RichTextClick {
        void clickTopic(Topic topic);

        void clickUser(User user);
    }

    public void setListener(RichTextClick listener) {
        this.mListener = listener;
    }


    public class Topic {
        public String id;
        public String rule;
        public String content;
        public String hash;
    }

    public class User {
        public String id;
        public String rule;
        public String content;
        public String name;
    }
}
