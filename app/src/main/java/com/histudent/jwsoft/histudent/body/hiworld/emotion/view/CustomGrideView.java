package com.histudent.jwsoft.histudent.body.hiworld.emotion.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.emotion.adapter.GridViewAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.emotion.util.EmotionBean;

import java.util.List;

/**
 * Created by ZhangYT on 2016/7/11.
 */
public class CustomGrideView implements AdapterView.OnItemClickListener {
    List<EmotionBean> gift;
    private Context ctx;
    private GridView layout_grideaa;
    private GridViewAdapter grideViewAdapter;
    public CustomGrideView(Context ctx, List<EmotionBean> gift){
        this.ctx = ctx;
        this.gift = gift;
    }

    public View getViews(){
        View view = View.inflate(ctx, R.layout.emotion_grid_view, null);
        layout_grideaa =  (GridView)view.findViewById(R.id.grid_view01);
        grideViewAdapter = new GridViewAdapter(ctx,gift,100);
        layout_grideaa.setAdapter(grideViewAdapter);
        layout_grideaa.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EmotionBean giftBean = (EmotionBean) parent.getItemAtPosition(position);
        onGiftSelectCallBack.EmotionInfor(giftBean);
        grideViewAdapter = new GridViewAdapter(ctx,gift,position);
        layout_grideaa.setAdapter(grideViewAdapter);
    }

    public void clearAdapter(){
        grideViewAdapter = new GridViewAdapter(ctx,gift,100);
        layout_grideaa.setAdapter(grideViewAdapter);
    }

    public interface EffectGiftCallBack{
        void EmotionInfor(EmotionBean giftBean);
    }
    public EffectGiftCallBack onGiftSelectCallBack;

    public void setOnGiftSelectCallBack(EffectGiftCallBack onGiftSelectCallBack) {
        this.onGiftSelectCallBack = onGiftSelectCallBack;
    }
}
