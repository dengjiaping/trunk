package com.histudent.jwsoft.histudent.comment2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.commen.view.addressSelect.Hz_utils;

import java.util.List;

/**
 * 地址选择器适配
 * Created by ZhangYT on 2016/8/29.
 */
public class AddressExpandedAdapter extends BaseExpandableListAdapter {
    Activity context;
    List<Object> groupData;//大组成员
    List<List<Object>> childData;//小组成员
    private getSelectedCity callback;


    public AddressExpandedAdapter(Activity context, List<Object> groupData, List<List<Object>> childData,
                                  getSelectedCity callback) {
        super();
        this.context = context;
        this.childData = childData;
        this.groupData = groupData;
        this.callback = callback;
    }


    //得到大组成员的view
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.address_selected_group, null);
        }


        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView title1 = (TextView) view.findViewById(R.id.tv);

        LinearLayout lin = ((LinearLayout) view.findViewById(R.id.line));
        title.setText(groupPosition == 0 ? "你所在的地区：" : groupPosition == 1 ? "" : getGroup(groupPosition).toString());//设置大组成员名称

        ImageView image = (ImageView) view.findViewById(R.id.iv);//是否展开大组的箭头图标
        if (groupPosition == 1) {
            image.setVisibility(View.VISIBLE);
            lin.setVisibility(View.VISIBLE);
            title1.setText(getGroup(groupPosition).toString());
        } else {
            lin.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
        }

        if (groupPosition >= 0 && groupPosition <= 2) {
            view.findViewById(R.id.relative).setBackgroundResource(R.color.bg_color);
        } else {
            view.findViewById(R.id.relative).setBackgroundResource(R.color.gray_bg);
        }

        if (isExpanded)//大组展开时
            image.setImageResource(R.mipmap.up_address);
        else//大组合并时
            image.setImageResource(R.mipmap.down);

        return view;
    }

    //得到大组成员的id
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //得到大组成员名称
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    //得到大组成员总数
    public int getGroupCount() {

        return groupData.size();

    }


    // 得到小组成员的view
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (groupPosition <= 2) {

            if (groupPosition == 0) {
                convertView = View.inflate(context, R.layout.hot_city_item, null);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, SystemUtil.dp2px(40));
                params.setMargins(SystemUtil.dp2px(20), 0, 0, 0);
                convertView.findViewById(R.id.parent_view).setBackgroundResource(R.color.bg_color);
                TextView tv = ((TextView) convertView.findViewById(R.id.tv_hot_city));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.getSelectedCity((AreaCodeModel) childData.get(groupPosition).get(0), 3);
                    }
                });

                tv.setLayoutParams(params);
                tv.setText(((AreaCodeModel) childData.get(groupPosition).get(0)).getName());
            } else {
                convertView = View.inflate(context, R.layout.address_grid_view, null);
                final HotCityAdapter hotCityAdapter = new HotCityAdapter(childData.get(groupPosition), context);


                final MyGridView gridView = (MyGridView) convertView
                        .findViewById(R.id.grid_view);
                gridView.setAdapter(hotCityAdapter);


                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AreaCodeModel areaCodeModel = ((AreaCodeModel) childData.get(groupPosition).get(position));
                        if (areaCodeModel.getDepth().equals("3"))
                            Hz_utils.setSelectedDis(childData.get(groupPosition), areaCodeModel.getName());
                        hotCityAdapter.notifyDataSetChanged();

                        callback.getSelectedCity(areaCodeModel, groupPosition == 1 ? 0 : 1);
                    }
                });
            }

        } else {

            AddressAdapter addressAdapter = new AddressAdapter(context, childData.get(groupPosition));
            convertView = View.inflate(context, R.layout.list_view, null);
            ListView listView = ((MyListView2) convertView.findViewById(R.id.list_view));
            listView.setTag(groupPosition);
            listView.setAdapter(addressAdapter);
            listView.setDividerHeight(0);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    callback.getSelectedCity(((AreaCodeModel) childData.get(groupPosition).get(position)), 1);
                }
            });

            convertView = listView;
        }

        return convertView;
    }


    //得到小组成员id
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    //得到小组成员的名称
    public Object getChild(int groupPosition, int childPosition) {

        return childData.get(groupPosition).get(childPosition);
    }

    //得到小组成员的数量
    public int getChildrenCount(int groupPosition) {
        return 1;
    }


    //返回每个字母所对应的位置
    public int getSelection(char c) {
        int m = -1;
        //首字母定位
        for (int i = 3; i < groupData.size(); i++) {
            char c1 = ((char) groupData.get(i));
            if (c1 == c) {
                m = i * 2;
                break;
            }
        }
        return m;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     * 表明大組和小组id是否稳定的更改底层数据。
     *
     * @return whether or not the same ID always refers to the same object
     */
    public boolean hasStableIds() {
        return true;
    }

    //得到小组成员是否被选择
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface getSelectedCity {
        public void getSelectedCity(AreaCodeModel areaCodeModel, int type);
    }

}
