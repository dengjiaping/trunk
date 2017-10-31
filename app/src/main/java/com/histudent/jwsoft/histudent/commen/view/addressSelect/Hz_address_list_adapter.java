package com.histudent.jwsoft.histudent.commen.view.addressSelect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;

import java.util.List;

/**
 * 地址选择器列表适配器
 *
 * @author 天锁
 */
public class Hz_address_list_adapter extends BaseAdapter {

    private List<AreaCodeModel> data;
    private Context context;
    public int num = 1;

    public Hz_address_list_adapter(Context context, List<AreaCodeModel> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public void notifyDataSetChanged() {

        num += 1;

        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.hz_address_select_item,
                    null, true);
            mHolder.text_item_listview_username = (TextView) convertView
                    .findViewById(R.id.item_text);
            mHolder.text_item_listview_image = (ImageView) convertView
                    .findViewById(R.id.item_image);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        AreaCodeModel model = data.get(position);

        mHolder.text_item_listview_username.setText(model.getName());

        if ("3".equals(model.getDepth())) {
            mHolder.text_item_listview_image.setVisibility(View.GONE);
        } else {
            mHolder.text_item_listview_image.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class ViewHolder {
        private TextView text_item_listview_username;
        private ImageView text_item_listview_image;
    }

}
