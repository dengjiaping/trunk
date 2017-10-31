package com.histudent.jwsoft.histudent.commen.view.addressSelect;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.listener.AddressCallback;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择对话框
 *
 * @author 天锁
 */
public class Hz_address_selecte_dialog extends Dialog {

    private List<AreaCodeModel> data_show = new ArrayList<>();// 放置liatview正在显示的数据源
    private List<AreaCodeModel> name_list = new ArrayList<>();// 放置选好的名称和编号
    private Context context;
    private Handler handler;
    private Hz_address_list_adapter adapter;// 适配器
    private TextView title;// 标题控件
    private LinearLayout layout;
    private TextView f;// 下分割线
    private ListView listView;// 数据列表
    private LinearLayout button_left;// 后退按钮
    private AddressCallback callback;

    public Hz_address_selecte_dialog(Context context, AddressCallback callback) {
        super(context);
        this.context = context;
        this.callback = callback;
    }

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hz_address_selecte_dialog);

        data_show.addAll(Hz_utils.praseJSON(context, "0"));

        adapter = new Hz_address_list_adapter(context, data_show);

        listView = (ListView) findViewById(R.id.address_list);
        title = (TextView) findViewById(R.id.title);
        f = (TextView) findViewById(R.id.f);
        button_left = (LinearLayout) findViewById(R.id.layout);
        layout = (LinearLayout) findViewById(R.id.layout);

        listView.setAdapter(adapter);

        // 点击listview的item监听
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                // 将选中的信息存入集合 （0:中文名称，1:数字编号）
                AreaCodeModel model = data_show.get(arg2);
                name_list.add(model);

                // 在标题栏显示已选择的内容
                List<String> info = showtitleText();

                if ("3".equals(model.getDepth())) {

                    dismiss();
                    callback.getAddress(info);

                } else {
                    // 动画
                    slideview_left(listView);

                    // 显示下划线和返回按钮
                    f.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);

                    //获取下一集的数据源
                    data_show.clear();
                    data_show.addAll(Hz_utils.praseJSON(context, model.getAreaCode()));

                    adapter.notifyDataSetChanged();
                    listView.smoothScrollToPosition(0);
                }
            }
        });

        // 点击dialog的返回键监听
        button_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                slideview_right(listView);

                AreaCodeModel model = name_list.get(name_list.size() - 1);

                name_list.remove(name_list.size() - 1);

                //获取下一集的数据源
                data_show.clear();
                data_show.addAll(Hz_utils.praseJSON(context, model.getParentCode()));

                adapter.notifyDataSetChanged();
                if ("1".equals(model.getDepth())) {

                    title.setText("请选择");

                    // 隐藏下划线和返回按钮
                    f.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);

                } else {
                    showtitleText();
                }
            }
        });

    }

    /**
     * 显示标题栏内容
     */
    private List<String> showtitleText() {

        StringBuffer info_name_title = new StringBuffer();
        StringBuffer info_name_code = new StringBuffer();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < name_list.size(); i++) {
            if (i < name_list.size() - 1) {
                info_name_title.append(name_list.get(i).getName() + "-");
                info_name_code.append(name_list.get(i).getAreaCode() + "-");
            } else {
                info_name_title.append(name_list.get(i).getName());
                info_name_code.append(name_list.get(i).getAreaCode());
            }
        }

        list.add(info_name_title.toString());
        list.add(info_name_code.toString());
        title.setText(info_name_title.toString());

        return list;

    }

    // 向左动画
    private void slideview_left(final View view) {

        TranslateAnimation translateAnimation = new TranslateAnimation(0,
                -1000, 0, 0);
        translateAnimation.setDuration(500);// 设置动画持续时间
        view.setAnimation(translateAnimation);

    }

    // 向右动画
    private void slideview_right(View view) {

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 1000,
                0, 0);
        translateAnimation.setDuration(500);// 设置动画持续时间
        view.setAnimation(translateAnimation);

    }

}
