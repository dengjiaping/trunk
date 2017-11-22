package com.histudent.jwsoft.histudent.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lichaojie on 2017/7/26.
 * desc:
 * Main Use RecyclerView Adapter
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected List<T> mListData;
    protected int mLayoutId;

    public BaseAdapter(Context context, List<T> listData, int layoutId) {
        this.mContext = context;
        this.mListData = listData;
        this.mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(mLayoutId, null),mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = mListData.get(position);
        bindData(t, holder, position);
    }


    /**
     * 绑定数据的具体操作让子类去实现
     *
     * @param t
     * @param holder
     * @param position
     */
    protected abstract void bindData(T t, BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener( OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onClick(View view,int position);
    }
}
