package com.histudent.jwsoft.histudent.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lichaojie on 2017/7/26.
 * desc:
 * Main Use RecyclerView Adapter ViewHolder
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    /**
     * 用于存放加载Layout中widget
     * id---->view
     */
    protected SparseArray<View> mSparseArray;

    protected BaseAdapter.OnItemClickListener mOnItemClickListener;


    public BaseViewHolder(View view, BaseAdapter.OnItemClickListener onItemClickListener) {
        super(view);
        this.mSparseArray = new SparseArray();
        this.mOnItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }


    public TextView getTextView(int id){
        return (TextView) findView(id);
    }

    public ImageView mImageView(int id){
        return (ImageView) findView(id);
    }

    public Button getButton(int id){
        return (Button) findView(id);
    }

    /**
     * 通过子view 的id important
     * a.把view存起来  b.返回每一个holder中的view
     * @param id
     * @return
     */
    private <T extends View> T findView(int id){
        View view = mSparseArray.get(id);
        if (mSparseArray != null) {
            view = itemView.findViewById(id);
            mSparseArray.put(id,view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onClick(view,getLayoutPosition());
        }
    }
}
