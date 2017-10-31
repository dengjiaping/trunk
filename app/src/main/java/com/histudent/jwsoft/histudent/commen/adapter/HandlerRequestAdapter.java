package com.histudent.jwsoft.histudent.commen.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.bean.GroupNewsModel;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.lidroid.xutils.http.RequestParams;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * 获取加入班级或者社群请求适配器
 * Created by ZhangYT on 2016/6/14.
 */
public class HandlerRequestAdapter extends BaseAdapter {
    private List<Object> list;
    private Activity context;
    private boolean isOperate;
    private RequestParams requestParams;
    private String realName, ImageUrl, AplyId, aplyReason, aplyTime, className;


    public HandlerRequestAdapter(List<Object> list, Activity context) {
        this.context = context;
        this.list = list;
    }


    public boolean isOperate() {

        return this.isOperate;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

//        if (convertView == null) {
        convertView = View.inflate(context, R.layout.request_item, null);
        viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = ((ViewHolder) convertView.getTag());
//        }


        final Object object = list.get(position);

        if (object instanceof GroupNewsModel.ApplyListBean) {

            GroupNewsModel.ApplyListBean b = ((GroupNewsModel.ApplyListBean) object);

            realName = b.getApplyUserRealName();
            aplyTime = b.getApplyTime();
            aplyReason = b.getApplyReason();
            className = b.getGroupName();
            ImageUrl = b.getApplyUserAvatar();


        } else if (object instanceof GroupNewsModel.InvitationListBean) {
            viewHolder.tv_instr.setText("邀请你加入 ");
            GroupNewsModel.InvitationListBean invitationBean = ((GroupNewsModel.InvitationListBean) object);
            realName = invitationBean.getInvitationUserName();
            aplyTime = invitationBean.getInvitationTime();
            viewHolder.tv_reason.setVisibility(View.GONE);
            aplyReason = "";
            className = invitationBean.getGroupName();
            ImageUrl = invitationBean.getInvitationUserAvatar();
            setOnClickListener(viewHolder, invitationBean.getId());
        }
        setOnClickListener(viewHolder, object);


        viewHolder.tv_name.setText(realName);
        viewHolder.tv_time.setText(TimeUtils.getFormateTimeWithoutSecond(aplyTime).substring(0, 11));
        viewHolder.tv_group_name.setText(className);
        viewHolder.tv_reason.setText("申请加入的理由:  " + aplyReason);

        if (!StringUtil.isEmpty(ImageUrl)) {
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, ImageUrl,
                    viewHolder.iv, ContextCompat.getDrawable(context, R.mipmap.avatar_def));
        }

        return convertView;
    }

    private void setOnClickListener(final ViewHolder viewHolder, final Object item) {
        viewHolder.btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                GroupHelper.handleGroupNews(context, item, true, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        isOperate = true;
                        viewHolder.btn_refuse.setVisibility(View.GONE);
                        viewHolder.btn_agree.setText("已同意");
                        viewHolder.btn_agree.setEnabled(false);
                        viewHolder.btn_agree.setClickable(false);
                        viewHolder.btn_agree.setPadding(SystemUtil.dp2px(19), 0, SystemUtil.dp2px(19), 0);
                        viewHolder.btn_agree.setBackground(context.getResources().getDrawable(R.drawable.gray_button_3dp_bg));
                        viewHolder.btn_agree.setTextColor(context.getResources().getColor(R.color.white));
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

        viewHolder.btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                GroupHelper.handleGroupNews(context, item, false, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        isOperate = true;
                        viewHolder.btn_refuse.setVisibility(View.GONE);
                        viewHolder.btn_agree.setText("已拒绝");
                        viewHolder.btn_agree.setEnabled(false);
                        viewHolder.btn_agree.setClickable(false);
                        viewHolder.btn_agree.setPadding(SystemUtil.dp2px(19), 0, SystemUtil.dp2px(19), 0);
                        viewHolder.btn_agree.setBackground(context.getResources().getDrawable(R.drawable.gray_button_3dp_bg));
                        viewHolder.btn_agree.setTextColor(context.getResources().getColor(R.color.white));
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });


    }


    class ViewHolder {
        private ImageView iv;
        private TextView tv_name, tv_time, tv_group_name, tv_reason, tv_instr;
        private TextView btn_refuse;
        private TextView btn_agree;

        public ViewHolder(View convertView) {
            iv = ((ImageView) convertView.findViewById(R.id.iv));
            tv_name = ((TextView) convertView.findViewById(R.id.tv_person_name));
            tv_time = ((TextView) convertView.findViewById(R.id.tv_time));
            tv_group_name = ((TextView) convertView.findViewById(R.id.tv_group_name));
            btn_agree = ((TextView) convertView.findViewById(R.id.btn_agree));
            btn_refuse = ((TextView) convertView.findViewById(R.id.btn_refuse));
            tv_reason = ((TextView) convertView.findViewById(R.id.tv_join_reason));
            tv_instr = ((TextView) convertView.findViewById(R.id.tv_));

        }
    }
}
