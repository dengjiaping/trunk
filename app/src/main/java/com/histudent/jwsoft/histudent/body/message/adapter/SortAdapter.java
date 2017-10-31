package com.histudent.jwsoft.histudent.body.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.activity.CreateGroupActivity;
import com.histudent.jwsoft.histudent.body.message.activity.SelectContactsActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.info.InfoHelper;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> list;
    private Context mContext;
    private boolean flag = false;
    private Handler handler;
    private float x_down, x_up;
    private boolean flag_recath = true;

    public SortAdapter(Context mContext, List<SortModel> list, Handler handler) {
        this.mContext = mContext;
        this.list = list;
        this.handler = handler;
    }

    public SortAdapter(Context mContext, List<SortModel> list, Handler handler, boolean flag) {
        this.mContext = mContext;
        this.list = list;
        this.flag = flag;
        this.handler = handler;
    }

    public void setVisibaleLiter(boolean flag) {
        this.flag_recath = flag;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view_, ViewGroup arg2) {
        ViewHolder viewHolder;
        final SortModel mContent = list.get(position);
//        if (view == null) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.myfriends_item, null);

        viewHolder = new ViewHolder();
        viewHolder.headImage = (HiStudentHeadImageView) view.findViewById(R.id.image_left);
        viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
        viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
        viewHolder.checkBox = (ImageView) view.findViewById(R.id.checkbox_left);
        viewHolder.layout = (RelativeLayout) view.findViewById(R.id.layout);
        viewHolder.text_null = (Button) view.findViewById(R.id.text_null);
        viewHolder.scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
        viewHolder.button_left = (Button) view.findViewById(R.id.button_left);
        viewHolder.button_right = (Button) view.findViewById(R.id.button_right);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//
//        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        viewHolder.button_left.setVisibility(View.VISIBLE);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section) && flag_recath) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        final SortModel model = list.get(position);

        if ("6".equals(model.getSort())) {
            viewHolder.button_right.setText(model.isIsMutual() ? "相互关注" : "关注");
        }

        switch (model.getUserType()) {
            case 1:
                viewHolder.tvTitle.setTextColor(Color.parseColor("#594639"));
                break;
            case 2:
                viewHolder.tvTitle.setTextColor(Color.parseColor("#FF985F"));
                break;
            case 3:
                viewHolder.tvTitle.setTextColor(Color.parseColor("#E5544C"));
                break;
        }


        viewHolder.tvTitle.setText(model.getName());
        viewHolder.headImage.loadBuddyAvatar(model.getAvatar());

        if (flag) {
            viewHolder.scrollView.setVisibility(View.GONE);
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            if (list.get(position).isFlag()) {
                viewHolder.checkBox.setImageResource(R.mipmap.selected);
            } else {
                viewHolder.checkBox.setImageResource(R.mipmap.unselected);
            }
            selectItem(viewHolder, model);
        }

        if ("6".equals(model.getSort())) {
            viewHolder.button_left.setVisibility(View.GONE);
        } else if ("7".equals(model.getSort())) {
            viewHolder.button_left.setVisibility(View.GONE);
            viewHolder.button_right.setVisibility(View.GONE);
        }

        setWidth(viewHolder);
        setButtonListener(model, viewHolder);
//        setScrollListener(viewHolder);

        return view;

    }


    final static class ViewHolder {
        TextView tvLetter, tvTitle;
        ImageView checkBox;
        RelativeLayout layout;
        HiStudentHeadImageView headImage;
        HorizontalScrollView scrollView;
        Button button_left, button_right, text_null;
    }

    /**
     * 重新设置可见部分的宽度
     */
    private void setWidth(ViewHolder holder) {

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.text_null.getLayoutParams();
        linearParams.width = SystemUtil.getScreenWind();
        holder.text_null.setLayoutParams(linearParams);

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 设置控件点击监听
     */
    private void setButtonListener(final SortModel sortModel, final ViewHolder holderMember) {

        holderMember.button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scorllView(holderMember.scrollView, true);
                Message message = handler.obtainMessage();
                message.what = 500;
                message.obj = sortModel;
                handler.sendMessage(message);
            }
        });

        holderMember.button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("6".equals(sortModel.getSort())) {

                    if (!sortModel.isIsMutual()) {
                        PersionHelper.getInstance().attention(mContext, 1, sortModel.getUserId(), new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                sortModel.setIsMutual(true);
                                scorllView(holderMember.scrollView, true);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });
                    }

                } else {
                    PersionHelper.getInstance().attention(mContext, 0, sortModel.getUserId(), new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            scorllView(holderMember.scrollView, true);
                            list.remove(sortModel);
                            notifyDataSetChanged();
                            Message message = handler.obtainMessage();
                            message.what = 600;
                            handler.sendMessage(message);

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                }
            }
        });

        holderMember.text_null.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scorllView(holderMember.scrollView, true);
//                PersonalHomepageActivity.start((BaseActivity) mContext, sortModel.getUserId());
                InfoHelper.gotoPersonHome((BaseActivity) mContext, sortModel.getUserId(), false);

            }
        });

    }

    /**
     * 设置控件滚动监听
     */
    private void setScrollListener(final ViewHolder holderMember) {

        holderMember.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        x_down = motionEvent.getX();

                        break;
                    case MotionEvent.ACTION_UP:

                        x_up = motionEvent.getX();

                        float w = holderMember.button_left.getWidth() + holderMember.button_right.getWidth();

                        if (x_down > x_up) {//左移

                            if ((x_down - x_up) > w / 2) {
                                scorllView(holderMember.scrollView, false);
                                flag = true;
                            } else {
                                scorllView(holderMember.scrollView, true);
                            }
                        } else {//右移
                            if ((x_up - x_down) > w / 2) {
                                scorllView(holderMember.scrollView, true);
                            } else {
                                scorllView(holderMember.scrollView, false);
                            }

                        }

                        break;
                }
                return false;
            }
        });

    }

    private void scorllView(final HorizontalScrollView view, final boolean isLeft) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                view.fullScroll(isLeft ? ScrollView.FOCUS_LEFT : ScrollView.FOCUS_RIGHT);
            }
        });

    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public void selectItem(final ViewHolder viewHolder, final SortModel model) {
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = model.isFlag();
                model.setFlag(!flag);

                if (!flag && !CreateGroupActivity.list_data.contains(model)) {
                    CreateGroupActivity.list_data.add(model);
                    SelectContactsActivity.selected_url.add(model.getUserId());
                    viewHolder.checkBox.setImageResource(R.mipmap.selected);
                } else if (flag && CreateGroupActivity.list_data.contains(model)) {
                    CreateGroupActivity.list_data.remove(model);
                    SelectContactsActivity.selected_url.remove(model.getUserId());
                    viewHolder.checkBox.setImageResource(R.mipmap.unselected);
                }
            }
        });

    }

}