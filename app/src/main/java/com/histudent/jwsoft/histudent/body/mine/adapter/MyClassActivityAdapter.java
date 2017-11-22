package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.UserGroupListModel;
import com.histudent.jwsoft.histudent.body.hiworld.bean.TopicModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;


/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class MyClassActivityAdapter extends BaseAdapter {

    private Context mContext;
    private List<Object> mListData;
    private String id;
    private boolean isMarker;

    private MyClassActivityAdapter(Context context, List<Object> data) {
        this.mContext = context;
        this.mListData = data;
    }

    public static final MyClassActivityAdapter create(Context context, List<Object> data) {
        return new MyClassActivityAdapter(context, data);
    }


    @Override
    public boolean isEnabled(int position) {

        if (mListData.get(position) instanceof String) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder holder = null;

        holder = new ChatHolder();
        Object object = mListData.get(position);

        if (object instanceof String) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.my_group_item_tag, null);
            holder.tv_tag = convertView.findViewById(R.id.tv_tag);
            holder.tv_tag.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray_h1));
            holder.tv_tag.setText((String) object);

            //话题
        } else if (object instanceof TopicModel) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.topic_item, null);
            holder.topicNewTopic = convertView.findViewById(R.id.tv_new_topic);
            holder.topicTitle = convertView.findViewById(R.id.topic_title);
            holder.topicContent = convertView.findViewById(R.id.topic_content);
            holder.topicNum = convertView.findViewById(R.id.topic_num);
            holder.topicLog = convertView.findViewById(R.id.topic_log);
            holder.topicLayout = convertView.findViewById(R.id.topic_layout);
            holder.tv_newLog = convertView.findViewById(R.id.topic_log1);
            holder.tv_topic = convertView.findViewById(R.id.tv_topic);

            TopicModel topicModel = ((TopicModel) object);

            if (StringUtil.isEmpty(topicModel.getTagId())) {
                holder.topicLayout.setVisibility(View.GONE);
                holder.topicLog.setVisibility(View.GONE);
                holder.tv_newLog.setVisibility(View.VISIBLE);
                holder.topicNum.setVisibility(View.GONE);
                holder.topicNewTopic.setVisibility(View.VISIBLE);
                holder.topicNewTopic.setText("#" + topicModel.getTagName().trim() + "#");
            } else {
                holder.topicLog.setVisibility(View.VISIBLE);
                holder.tv_newLog.setVisibility(View.GONE);
                holder.topicNewTopic.setVisibility(View.GONE);
                holder.topicLayout.setVisibility(View.VISIBLE);
                holder.topicNum.setVisibility(View.VISIBLE);
                holder.topicContent.setText(StringUtil.isEmpty(topicModel.getTagDescription()) ? "" : topicModel.getTagDescription().trim());
                holder.topicTitle.setText(StringUtil.isEmpty(topicModel.getTagName()) ? "" : topicModel.getTagName().trim());
                holder.topicNum.setText(+topicModel.getOwnerCount() + "人讨论");

                if (StringUtil.isEmpty(topicModel.getSmallPic())) {
                    holder.topicLog.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_color));
                    holder.tv_topic.setVisibility(View.VISIBLE);
                    if (!StringUtil.isEmpty(topicModel.getTagName()))
                        holder.tv_topic.setText(topicModel.getTagName().substring(0, 1));
                } else {
                    CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, topicModel.getSmallPic(),
                            holder.topicLog, PhotoManager.getInstance().getDefaultPlaceholderResource());
                }
            }

            //班级或者社群

        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.our_activity_myclass_item, null);

            holder.name = convertView.findViewById(R.id.our_activity_myclass_item_name);
            holder.image = convertView.findViewById(R.id.our_activity_myclass_item_picture);
            holder.tv_number = convertView.findViewById(R.id.number);

            if (object instanceof UserGroupListModel.MyJoinGroupsBean) {

                UserGroupListModel.MyJoinGroupsBean model = (UserGroupListModel.MyJoinGroupsBean) object;
                holder.name.setText(model.getGroupName());
                holder.tv_number.setText(String.valueOf(model.getGroupMemberNum()));
                id = model.getGroupId();
                isMarker = model.isIsGroupMaker();

                if (model.getGroupLogo() != null) {
                    CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, model.getGroupLogo(),
                            holder.image, ContextCompat.getDrawable(mContext, R.mipmap.default_group));
                }
            } else {
                UserGroupListModel.MyCreateGroupsBean bean = (UserGroupListModel.MyCreateGroupsBean) object;

                id = bean.getGroupId();
                isMarker = bean.isIsGroupMaker();
                holder.name.setText(bean.getGroupName());
                holder.tv_number.setText(String.valueOf(bean.getGroupMemberNum()));

                if (bean.getGroupLogo() != null) {
                    CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, bean.getGroupLogo(),
                            holder.image, ContextCompat.getDrawable(mContext, R.mipmap.default_group));

                }
            }

        }

        return convertView;
    }


    static class ChatHolder {
        //社群或者班级使用
        private TextView name, tv_tag, tv_number, tv_newLog, tv_topic;
        private AppCompatImageView image;
        private ImageView topicLog;
        //话题使用
        private TextView topicTitle, topicContent, topicNewTopic, topicNum;
        private LinearLayout topicLayout;
    }


}
