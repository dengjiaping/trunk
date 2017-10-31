package com.histudent.jwsoft.histudent.body.hiworld.emotion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.emotion.util.EmotionBean;

import java.util.List;

/**
 *
 */
public class  GridViewAdapter extends BaseAdapter {

    private  List<EmotionBean> gift;
    private int index;
     private  Context ctx;
    public GridViewAdapter(Context ctx, List<EmotionBean> gift, int index){
        this.ctx = ctx;
        this.gift = gift;
        this.index = index;
    }
    @Override
    public int getCount() {
        return gift.size();
    }

    @Override
    public Object getItem(int position) {
        return gift.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.emotion_imag,null);
        ImageView iv_image = (ImageView)view.findViewById(R.id.iv_img);
        iv_image.setBackgroundResource(gift.get(position).getResId());
        return view;
    }
}
