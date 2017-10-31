

package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.FindUserBean;
import com.histudent.jwsoft.histudent.body.message.model.RecommondUserModel;
import com.histudent.jwsoft.histudent.body.message.model.SeeFriendAdapterModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.InfoHelper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class SeeFriendAdapter extends BaseAdapter {

    private Activity activity;
    private List<Object> userModels;


    public SeeFriendAdapter(Activity activity, List<Object> userModels) {
        this.activity = activity;
        this.userModels = userModels;
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public Object getItem(int position) {
        return userModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder holder;
        if (convertView == null) {
            holder = new ChatHolder();
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.see_friend_list_item, null);

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.class_name = (TextView) convertView.findViewById(R.id.class_name);
            holder.image = (HiStudentHeadImageView) convertView.findViewById(R.id.image);
            holder.see = (Button) convertView.findViewById(R.id.see);

            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }

        SeeFriendAdapterModel model = exchange(userModels.get(position));

        holder.name.setText(model.getRealName());
        holder.class_name.setText(model.getCurrentClass());
        holder.image.loadBuddyAvatar(model.getAvatar());
        if (model.getIsFollowed()) {
            holder.see.setText("已关注");
            holder.see.setTextColor(activity.getResources().getColor(R.color.text_white));
            holder.see.setBackgroundResource(R.drawable.addsee_actionitem_ture);
        } else {
            holder.see.setText("+关注");
            holder.see.setTextColor(activity.getResources().getColor(R.color.text_blue));
            holder.see.setBackgroundResource(R.drawable.addsee_actionitem_false);
        }

        setLisitioner(holder.see, model, position);

        setLisitioner(holder.image, model);

        return convertView;
    }

    class ChatHolder {
        TextView name, class_name;
        HiStudentHeadImageView image;
        Button see;
    }

    private void setLisitioner(View view, final SeeFriendAdapterModel model, final int position) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean flag = model.getIsFollowed();

                Map<String, Object> map = new TreeMap<>();
                map.put("action", flag ? "0" : "1");
                map.put("objectId", model.getUserId());

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.doAction_url, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        model.setIsFollowed(!flag);
                        userModels.remove(position);
                        userModels.add(position, model);

                        notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });

    }

    private void setLisitioner(View view, final SeeFriendAdapterModel model) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoHelper.gotoPersonHome((BaseActivity) activity, model.getUserId(), false);
            }
        });

    }

    private SeeFriendAdapterModel exchange(Object object) {

        SeeFriendAdapterModel model = new SeeFriendAdapterModel();

        if (object instanceof RecommondUserModel) {
            RecommondUserModel userModel = (RecommondUserModel) object;
            model.setUserId(userModel.getUserId());
            model.setAvatar(userModel.getAvatar());
            model.setRealName(userModel.getRealname());
            model.setIsFollowed(userModel.isIsFocus());
            model.setCurrentClass(userModel.getClassFullName());

        } else if (object instanceof FindUserBean) {
            FindUserBean userInfo = (FindUserBean) object;
            model.setUserId(userInfo.getUserId());
            model.setAvatar(userInfo.getAvatar());
            model.setRealName(userInfo.getRealName());
            model.setCurrentClass(userInfo.getCurrClassName());
            model.setIsFollowed(userInfo.isIsFollowed());
        } else {
            return (SeeFriendAdapterModel) object;
        }

        return model;

    }

}
