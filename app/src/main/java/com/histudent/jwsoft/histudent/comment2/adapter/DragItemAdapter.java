package com.histudent.jwsoft.histudent.comment2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.EditApplicationActivity;
import com.histudent.jwsoft.histudent.body.find.bean.HuodongBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.DragHolderCallBack;
import com.histudent.jwsoft.histudent.comment2.utils.RecycleCallBack;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Author: zhuwt
 * Date: 2016/6/21 18:07
 * Description: 说明
 * PackageName: com.ancun.autopack.ProjectAdapter
 **/
public class DragItemAdapter extends RecyclerView.Adapter<DragItemAdapter.DragHolder> {

    private List<Object> list;
    private boolean isShow;
    private EditApplicationActivity.onItemClick onItemClick;

    private RecycleCallBack mRecycleClick;
    public SparseArray<Integer> show = new SparseArray<>();
    private Context mContext;

    public DragItemAdapter(Context context,RecycleCallBack click, List<Object> data, EditApplicationActivity.onItemClick onItemClick) {
        this.mContext = context;
        this.list = data;
        this.mRecycleClick = click;
        this.onItemClick = onItemClick;
    }

    public void setData(List<Object> data) {
        this.list = data;
    }


    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
//        notifyDataSetChanged();
    }

    @Override
    public DragHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new DragHolder(view, mRecycleClick);
    }

    @Override
    public void onBindViewHolder(final DragHolder holder, final int position) {


        Log.e("position", position + "");
        HuodongBean bean = ((HuodongBean) list.get(position));
        holder.text.setText(bean.getAppName());
        if (!StringUtil.isEmpty(bean.getLogo())) {
//            MyImageLoader.getIntent().displayNetImageWithAnimation(BaseActivity.intence,bean.getLogo(),holder.log,R.mipmap.def);
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext,
                    bean.getLogo(), holder.log, ContextCompat.getDrawable(mContext, R.mipmap.def));
        }
//        if (null == show.get(position))
        if (!isShow || bean.isEmpty())
            holder.del.setVisibility(View.INVISIBLE);
        else
            holder.del.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DragHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, DragHolderCallBack {

        public TextView text;
        public ImageView del, log;
        public RelativeLayout item;
        private RecycleCallBack mClick;

        public DragHolder(View itemView, RecycleCallBack click) {
            super(itemView);
            mClick = click;
            item = itemView.findViewById(R.id.item);
            text = itemView.findViewById(R.id.text);
            del = itemView.findViewById(R.id.del);
            log = itemView.findViewById(R.id.icon);
            item.setOnClickListener(this);
            del.setOnClickListener(this);
        }

        @Override
        public void onSelect() {

            Log.e("onSelect", "onSelect");
            show.clear();
            if (!isShow) {
                isShow = true;
                onItemClick.onItemClick(null, true);
                notifyDataSetChanged();
            }


            show.put(getAdapterPosition(), getAdapterPosition());
            itemView.setBackgroundColor(Color.LTGRAY);

//            del.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClear() {
            itemView.setBackgroundResource(R.drawable.right_bottom_view);
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            if (null != mClick) {
                show.clear();
                mClick.itemOnClick(getAdapterPosition(), v);
            }
        }
    }
}
