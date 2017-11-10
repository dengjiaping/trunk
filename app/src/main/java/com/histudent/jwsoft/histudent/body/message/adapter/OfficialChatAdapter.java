

package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.ChatType;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.manage.PhotoManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/6/1.
 */
public class OfficialChatAdapter extends BaseAdapter {

    private Activity activity;
    private List<RecentContactsModel> users;
    private int flag;


    public OfficialChatAdapter(Activity activity, List<RecentContactsModel> datas, int flag) {
        this.activity = activity;
        this.users = datas;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder holder = null;
        if (convertView == null) {
            holder = new ChatHolder();
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.official_chat_item, null);

            holder.image = convertView.findViewById(R.id.image);
            holder.picture = convertView.findViewById(R.id.picture);
            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);
            holder.time = convertView.findViewById(R.id.time);

            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }
        RecentContactsModel model = users.get(position);

        switch (flag) {
            case ChatType.SYSTEMINFO:
                holder.image.setImageResource(R.mipmap.system_logo);
                break;
            case ChatType.SUBSCIBR:
                holder.image.setImageResource(R.mipmap.subscibr);
                break;
            case ChatType.ACTION:
                holder.image.setImageResource(R.mipmap.offical_action);
                break;
        }

        CommonGlideImageLoader.getInstance().displayNetImage(activity, model.getImg(),
                holder.picture, PhotoManager.getInstance().getDefaultPlaceholderResource());
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(model.getTime());
        String t1 = format.format(d1);
        holder.time.setText(t1);

        return convertView;
    }

    class ChatHolder {
        TextView title, content, time;
        HiStudentHeadImageView image;
        ImageView picture;

    }

}
