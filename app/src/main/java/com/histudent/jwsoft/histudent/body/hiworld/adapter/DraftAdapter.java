package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.activity.DraftActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.LogModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;

import java.util.List;

/**
 * 日志草稿适配
 */
public class DraftAdapter extends BaseAdapter {
    private List<LogModel.ItemsBean> objects;
    private Activity activity;
    private float x_down, x_up, y_down, y_up;
    private Handler handler = new Handler();
    private DraftActivity.onItemClick onItemClick;


    public DraftAdapter(Activity activity, List<LogModel.ItemsBean> objects,DraftActivity.onItemClick onItemClick) {
        this.objects = objects;
        this.activity = activity;
        this.onItemClick=onItemClick;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }


    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //班级成员
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.draft_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final Object obj = objects.get(position);

        final LogModel.ItemsBean logModel = ((LogModel.ItemsBean) obj);

        Log.e("title--" + position, logModel.getTitle());

        viewHolder.draft_content.setText(logModel.getSummary());
        viewHolder.draft_time.setText(TimeUtils.exchangeTime(logModel.getCreateTime()));
        viewHolder.draft_title.setText(logModel.getTitle());

        setButtonListener(logModel, viewHolder);
        viewHolder.button_delete.setText("删除");
        setWidth(viewHolder);
        setScrollListener(viewHolder);

        return convertView;
    }


    /**
     * 重新设置可见部分的宽度
     */
    private void setWidth(ViewHolder holderMember) {

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holderMember.layout_show.getLayoutParams();
        linearParams.width = SystemUtil.getScreenWind();
        holderMember.layout_show.setLayoutParams(linearParams);

    }

    /**
     * 设置控件点击监听
     */
    private void setButtonListener(final LogModel.ItemsBean logModel, final ViewHolder viewHolder) {


        viewHolder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataUtils.deleteDraft((BaseActivity) activity, logModel.getBlogId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        scorllView(viewHolder, true);
                        objects.remove(logModel);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });
        viewHolder.layout_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(logModel.getBlogId());
            }
        });

    }


    /**
     * 设置控件滚动监听
     */
    private void setScrollListener(final ViewHolder viewHolder) {

        viewHolder.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        x_down = motionEvent.getX();
                        y_down = motionEvent.getY();

                        break;
                    case MotionEvent.ACTION_UP:

                        x_up = motionEvent.getX();
                        y_up = motionEvent.getY();

                        float w = viewHolder.button_delete.getWidth();

                        if (x_down > x_up) {//左移

                            if (x_down - x_up > w / 3) {// && Math.abs(y_down - y_up) < 70
                                scorllView(viewHolder, false);
                            } else {
                                scorllView(viewHolder, true);
                            }
                        } else if (x_down < x_up) {//右移
                            if (x_up - x_down > w / 3 ) {//&& Math.abs(y_down - y_up) < 70
                                scorllView(viewHolder, true);
                            } else {
                                scorllView(viewHolder, false);
                            }
                        }

                        break;
                }
                return false;
            }
        });

    }

    private void scorllView(final ViewHolder holderMember, final boolean isLeft) {

        handler.post(new Runnable() {
            @Override
            public void run() {
//                holderMember.scrollView.smoothScrollTo(isLeft ? 0 : SystemUtil.getScreenWind(), 0);
                if (holderMember.button_delete.getVisibility() != View.GONE) {
                    holderMember.iv_next.setVisibility(isLeft ? View.VISIBLE : View.GONE);
                }
            }
        });

    }

    private class ViewHolder {
        TextView draft_title, draft_time, draft_content;
        RelativeLayout layout_show;
        Button button_delete;
        HorizontalScrollView scrollView;
        private ImageView iv_next;

        public ViewHolder(View converView) {
            layout_show = (RelativeLayout) converView.findViewById(R.id.layout_show);
            button_delete = (Button) converView.findViewById(R.id.button_delet);
            scrollView = (HorizontalScrollView) converView.findViewById(R.id.scrollView);
            draft_content = ((TextView) converView.findViewById(R.id.draft_content));
            draft_time = ((TextView) converView.findViewById(R.id.draft_time));
            draft_title = ((TextView) converView.findViewById(R.id.draft_title));
            iv_next= ((ImageView) converView.findViewById(R.id.iv_next));
        }
    }

}
