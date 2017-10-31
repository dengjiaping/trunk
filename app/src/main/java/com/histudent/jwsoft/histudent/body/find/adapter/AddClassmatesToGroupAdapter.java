package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import java.util.List;

/**
 * Created by ZhangYT on 2017/5/19.
 * 添加邀请班级同学加入社群
 */

public class AddClassmatesToGroupAdapter extends BaseAdapter {
    private Activity context;
    private List<SimpleUserModel> list;
    private Handler handler = new Handler();
    private float x_down, x_up, y_down, y_up;

    public AddClassmatesToGroupAdapter(Activity context, List<SimpleUserModel> list) {
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
        ViewHolder viewHolder;

        SimpleUserModel userModel = list.get(position);
//        if (convertView == null) {

        convertView = View.inflate(context, R.layout.add_classmate_into_group_item, null);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);

//        } else {
//            viewHolder = ((ViewHolder) convertView.getTag());
//        }


        viewHolder.name.setText(userModel.getName());
        MyImageLoader.getIntent().displayNetImage(context, userModel.getAvatar(), viewHolder.headImageView);
        setListener(viewHolder, userModel);
        setWidth(viewHolder);
//        scorllView(viewHolder);

        return convertView;
    }

    private void setListener(ViewHolder viewHolder, final SimpleUserModel userModel) {

        viewHolder.button_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.remove(userModel);
                notifyDataSetChanged();
            }
        });
        viewHolder.layout_recentcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PersonCenterActivity.start(context, userModel.getUserId());
            }
        });
    }


    /**
     * 重新设置可见部分的宽度
     */
    private void setWidth(ViewHolder holderMember) {

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holderMember.layout_recentcontact.getLayoutParams();
        linearParams.width = SystemUtil.getScreenWind();
        holderMember.layout_recentcontact.setLayoutParams(linearParams);

    }


    class ViewHolder {
        private HiStudentHeadImageView headImageView;
        private HorizontalScrollView scrollView;
        private TextView name;
        private Button button_delet;
        private RelativeLayout layout_recentcontact;

        public ViewHolder(View convertView) {
            headImageView = (HiStudentHeadImageView) convertView.findViewById(R.id.head_image);
            name = ((TextView) convertView.findViewById(R.id.name));
            button_delet = (Button) convertView.findViewById(R.id.button_delet);
            layout_recentcontact = (RelativeLayout) convertView.findViewById(R.id.layout_recentcontact);
            scrollView = (HorizontalScrollView) convertView.findViewById(R.id.scrollView);
        }
    }
}
