package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.bean.MemberTypeBean;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.team.constant.TeamMemberType;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 * 显示群组成员的GridView的适配器
 */
public class TeamAdapter extends BaseAdapter {

    private Activity activity;
    private List<TeamMember> messages;
    public static List<ImageView> image_list = new ArrayList<>();
    private List<String> mTeacherUserId;


    public TeamAdapter(Activity activity, List<TeamMember> messages) {
        this.activity = activity;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public void notifyDataSetChanged() {
        image_list.clear();
        super.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(activity).inflate(R.layout.chat_gridview_item, null);
        TextView chat_grad_name = view.findViewById(R.id.chat_grad_name);
        HeadImageView chat_grid_image = view.findViewById(R.id.chat_grid_image);
        ImageView chat_grid_reduce = view.findViewById(R.id.chat_grid_reduce);
        ImageView teacherIdentityView = view.findViewById(R.id.iv_teacher_identity);


        TeamMember message = messages.get(position);
        if (message == null) {
//            chat_grid_reduce.setVisibility(View.GONE);
//            if (position == messages.size() - 2) {
//                chat_grid_image.setBackgroundResource(R.mipmap.gridview_add);
//            } else {
//                chat_grid_image.setBackgroundResource(R.drawable.griadview_reduce);
//            }
//            chat_grad_name.setText("");
        } else {
            NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(message.getAccount());
            chat_grad_name.setText(user.getName());
            chat_grid_image.loadBuddyAvatar(user.getAccount());
            image_list.add(chat_grid_reduce);

            // TODO: 2017/8/14 老师身份识别
            if(mTeacherUserId.contains(message.getAccount())){
                teacherIdentityView.setVisibility(View.VISIBLE);
            }else{
                teacherIdentityView.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }

    public void setTeacherUserId(List<String> teacherUserId) {
        mTeacherUserId = teacherUserId;
    }
}
