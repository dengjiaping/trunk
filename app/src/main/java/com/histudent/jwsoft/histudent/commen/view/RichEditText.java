package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huyg on 2017/6/27.
 */

public class RichEditText extends AppCompatEditText implements TextWatcher {
    private Set<Topic> mTopics;
    private Set<User> mUsers;
    // 默认,话题文本高亮颜色
    private static final int FOREGROUND_COLOR = Color.parseColor("#2f4459");
    private static final String AT = "@";
    private static final String POUND = "#";
    private RichClick mListener;

    public RichEditText(Context context) {
        this(context, null);
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mTopics = new HashSet<>();
        mUsers = new HashSet<>();
        addTextChangedListener(this);
    }

    public void setTopic(TopicModel topicModel) {
        if (!("新话题").equals(topicModel.getTagName())){
            Topic topic = new Topic();
            topic.rule = POUND;
            topic.content = POUND + topicModel.getTagName() + POUND;
            topic.hash = topicModel.getTagName();
            mTopics.add(topic);
        }
        /**
         * 2.将话题内容添加到EditText中展示
         */
        int selectionStart = getSelectionStart();// 光标位置
        Editable editable = getText();// 原先内容

        if (selectionStart >= 0) {
            editable.insert(selectionStart, POUND + topicModel.getTagName() + POUND);// 在光标位置插入内容
            editable.insert(getSelectionStart(), " ");// 话题后面插入空格,至关重要
            setSelection(getSelectionStart());// 移动光标到添加的内容后面
        }

    }

    public void setUser(SimpleUserModel simpleUserModel) {
        User user = new User();
        user.rule = AT;
        user.id = simpleUserModel.getUserId();
        user.content = AT + simpleUserModel.getName();
        user.name = simpleUserModel.getName();
        mUsers.add(user);
        /**
         * 2.将他人内容添加到EditText中展示
         */
        int selectionStart = getSelectionStart();// 光标位置
        Editable editable = getText();// 原先内容

        if (selectionStart >= 0) {
            editable.insert(selectionStart, user.content);// 在光标位置插入内容
            editable.insert(getSelectionStart(), " ");// 话题后面插入空格,至关重要
            setSelection(getSelectionStart());// 移动光标到添加的内容后面
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        refreshEditTextUI(s.toString());
    }

    /**
     * EditText内容修改之后刷新UI
     *
     * @param content 输入框内容
     */
    private void refreshEditTextUI(String content) {

        /**
         * 内容变化时操作<br/>
         * 1.查找匹配所有话题内容 <br/>
         * 2.设置话题内容特殊颜色
         */

        if (mTopics == null)
            return;
        if (mUsers == null) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            mTopics.clear();
            mUsers.clear();
            return;
        }

        /**
         * 重新设置span
         */
        Editable editable = getText();
        for (Topic topic:mTopics) {
            String text = topic.content;
            Pattern pattern = Pattern.compile(text);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                        FOREGROUND_COLOR);
                editable.setSpan(colorSpan, matcher.start(), matcher.start()
                                + text.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        for (User user:mUsers) {
            String text = user.content;
            Pattern pattern = Pattern.compile(text);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                        FOREGROUND_COLOR);
                editable.setSpan(colorSpan, matcher.start(), matcher.start()
                                + text.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

    }


    /**
     * 监听光标的位置,若光标处于话题内容中间则移动光标到话题结束位置
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (mUsers == null || mTopics == null) {
            return;
        }
        String objectText = "";
        for (Topic topic:mTopics) {
            objectText = topic.content;// 文本
            Pattern pattern = Pattern.compile(objectText);
            Matcher matcher = pattern.matcher(getText());
            while (matcher.find()) {
                if (matcher.start() != -1 && selStart > matcher.start()
                        && selStart < matcher.start() + objectText.length()) {// 若光标处于话题内容中间则移动光标到话题结束位置
                    setSelection(getText().toString().length());
                    mListener.clickTopic(topic);
                }
            }
        }

        for (User user:mUsers) {
            objectText = user.content;// 文本
            Pattern pattern = Pattern.compile(objectText);
            Matcher matcher = pattern.matcher(getText());
            while (matcher.find()) {
                if (matcher.start() != -1 && selStart > matcher.start()
                        && selStart < matcher.start() + objectText.length()) {// 若光标处于话题内容中间则移动光标到话题结束位置
                    setSelection(getText().toString().length());
                    mListener.clickUser(user);
                }
            }
        }

    }

    public interface RichClick {
        void clickTopic(Topic topic);

        void clickUser(User user);
    }

    public void setListener(RichClick listener) {
        this.mListener = listener;
    }


    public class Topic {
        public String rule;
        public String content;
        public String hash;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Topic) {
                Topic top = (Topic) obj;
                if (top.hash.equals(this.hash)) {
                    return true;
                }
                return false;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int prime = 31;
            return prime + (hash == null ? 0 : hash.hashCode());
        }
    }

    public class User {
        public String id;
        public String rule;
        public String content;
        public String name;


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof User) {
                User use = (User) obj;
                if (use.id.equals(this.id)) {
                    return true;
                }
                return false;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int prime = 32;
            return prime + (id == null ? 0 : id.hashCode());
        }
    }


}
