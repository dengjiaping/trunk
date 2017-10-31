package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CommentsModel;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.keyword.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.commen.keyword.utils.SpanStringUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import java.util.List;


/**
 * Created by liuguiyu-pc on 2017/4/21
 */

public class ActionViewHolder {
    public HiStudentHeadImageView action_head;
    public TextView action_name, action_time, show_like_num, show_comment_num;
    public IconView action_like, action_discuss, action_share, action_menue, show_like;
    private ImageView action_tuijian;
    public LinearLayout like_layout, comment_layout, like_comment_layout, comment_layout_items;

    public void setCommen(View view) {
        action_head = (HiStudentHeadImageView) view.findViewById(R.id.action_head);
        action_name = (TextView) view.findViewById(R.id.action_name);
        action_time = (TextView) view.findViewById(R.id.action_time);
        show_like_num = (TextView) view.findViewById(R.id.show_like_num);
        show_comment_num = (TextView) view.findViewById(R.id.show_comment_num);
        action_like = (IconView) view.findViewById(R.id.action_like);
        action_discuss = (IconView) view.findViewById(R.id.action_discuss);
        action_share = (IconView) view.findViewById(R.id.action_share);
        action_tuijian = (ImageView) view.findViewById(R.id.action_tuijian);
        action_menue = (IconView) view.findViewById(R.id.action_menue);
        show_like = (IconView) view.findViewById(R.id.show_like);
        like_layout = (LinearLayout) view.findViewById(R.id.like_layout);
        comment_layout = (LinearLayout) view.findViewById(R.id.comment_layout);
        like_comment_layout = (LinearLayout) view.findViewById(R.id.like_comment_layout);
        comment_layout_items = (LinearLayout) view.findViewById(R.id.comment_layout_items);
    }

    public void bindCommenData(Context context, ActionViewHolder holder, ActionListBean bean, int showType, boolean isDetail) {
        MyImageLoader.getIntent().displayNetImage(context, bean.getUserAvatar(), holder.action_head);
        holder.action_name.setText(bean.getUserName());
        holder.action_time.setText(TimeUtils.exchangeTime(bean.getLastUpdateTime()));

        holder.action_share.setVisibility("Picture".equals(bean.getActivityItemKey()) ? View.GONE : View.VISIBLE);
        holder.action_menue.setVisibility("Picture".equals(bean.getActivityItemKey()) ? View.GONE : View.VISIBLE);

        holder.like_comment_layout.setVisibility(bean.getPraiseCount() == 0 && bean.getCommentCount() == 0 ? View.GONE : View.VISIBLE);

        //点赞
        holder.action_like.setTextColor(context.getResources().getColor(bean.getIsPraised() ? R.color.green_color : R.color.text_black_h3));
        holder.action_like.setText(bean.getIsPraised() ? R.string.icon_zan : R.string.icon_zannone);

        holder.action_tuijian.setVisibility(bean.getIsRecommand() && showType == ActionAdapter.CLASS_CIRCLE ? View.VISIBLE : View.GONE);
        if (isDetail){
            holder.like_comment_layout.setVisibility(View.GONE);
        }
        if (showType == ActionAdapter.INVITATION) {
            holder.like_comment_layout.setVisibility(View.GONE);
            holder.action_share.setVisibility(View.GONE);
            holder.action_menue.setVisibility(View.GONE);

        } else if (showType == ActionAdapter.COMMENT) {
            holder.like_comment_layout.setVisibility(View.GONE);

        } else if (bean.getPraiseCount() == 0 && bean.getCommentCount() == 0) {
            holder.like_comment_layout.setVisibility(View.GONE);

        } else {

            if (bean.getPraiseCount() > 0 && !isDetail) {
                like_layout.setVisibility(View.VISIBLE);
                holder.show_like_num.setText(bean.getPraiseCount() + "人点赞");
            } else {
                like_layout.setVisibility(View.GONE);
            }

            //评论
            if (bean.getCommentCount() > 0 && !isDetail) {
                comment_layout.setVisibility(View.VISIBLE);
                List<CommentsModel> beanList = bean.getItemsBeen();
                if (beanList != null && beanList.size() > 0) {
                    if (beanList.size() == 1) {
                        comment_layout_items.addView(getCommentItemView(context, beanList.get(0)));
                    } else {
                        CommentsModel model_00 = beanList.get(0);
                        comment_layout_items.addView(getCommentItemView(context, model_00));
                        CommentsModel model_01 = beanList.get(1);
                        comment_layout_items.addView(getCommentItemView(context, model_01));
                    }
                }

                if (bean.getCommentCount() > 2) {
                    holder.show_comment_num.setText("查看全部" + bean.getCommentCount() + "条评论");
                } else {
                    holder.show_comment_num.setVisibility(View.GONE);
                }
            } else {
                comment_layout.setVisibility(View.GONE);
            }
        }

    }

    private void setComment(Context context, CommentsModel itemsBean, TextView view) {
        String name = itemsBean.getUser().getName();
        ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.text_black_h3));
        SpannableString spannableString = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, context, view, name + "：" + itemsBean.getContent());
        SpannableStringBuilder builder = new SpannableStringBuilder(spannableString);
        builder.setSpan(redSpan, 0, name.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(builder);
    }

    private View getCommentItemView(Context context, CommentsModel model) {
        View layout = LayoutInflater.from(context).inflate(R.layout.action_comment_item_layout, null);
        TextView tv_to_name = ((TextView) layout.findViewById(R.id.tv_to_name));
        TextView tv_comment = ((TextView) layout.findViewById(R.id.tv_comment));

        if (!TextUtils.isEmpty(model.getToUser().getName())) {
            tv_to_name.setVisibility(View.GONE);
            setComment(context, model, tv_to_name);
        } else {
            tv_to_name.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getContent()))
            setComment(context, model, tv_comment);

        return layout;
    }

}
