package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.CommentsModel;
import com.histudent.jwsoft.histudent.commen.keyword.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.commen.keyword.utils.SpanStringUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nim.uikit.common.util.sys.TimeUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ZhangYT on 2016/6/24.
 */
public class MyCommentAdpater extends BaseAdapter {
    private Activity context;
    private List<CommentsModel> list;
    private Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");

    public MyCommentAdpater(Activity context, List<CommentsModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.comment, null);
            holder.imageView = (HiStudentHeadImageView) convertView.findViewById(R.id.iv);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_to_name = (TextView) convertView.findViewById(R.id.tv_to_name);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final CommentsModel itemsBean = list.get(position);

        MyImageLoader.getIntent().displayNetImage(context, itemsBean.getUser().getAvatar(), holder.imageView);
        holder.tv_name.setText(itemsBean.getUser().getName());

        if (!TextUtils.isEmpty(itemsBean.getToUser().getName())) {
            holder.tv_to_name.setVisibility(View.VISIBLE);
            setComment(context, itemsBean, holder.tv_to_name);
        } else {
            holder.tv_to_name.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(itemsBean.getContent()))
            holder.tv_comment.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE,
                    context, holder.tv_comment, itemsBean.getContent()));

        holder.tv_time.setText(TimeUtil.getTimeShowString(TimeUtils.getTimeLong(itemsBean.getCreateTime()), true));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(context, itemsBean.getUser().getUserId());
            }
        });

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(context, itemsBean.getUser().getUserId());
            }
        });

        return convertView;
    }

    class Holder {

        HiStudentHeadImageView imageView;
        TextView tv_name, tv_time;
        TextView tv_to_name, tv_comment;

    }

    private void setComment(Context context, CommentsModel itemsBean, TextView view) {
        String name = itemsBean.getToUser().getName();
        ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.text_black_h3));
        SpannableString spannableString = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, view, name + "：" + itemsBean.getToCommentContent());
        SpannableStringBuilder builder = new SpannableStringBuilder(spannableString);
        builder.setSpan(redSpan, 0, name.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(builder);
    }

}
