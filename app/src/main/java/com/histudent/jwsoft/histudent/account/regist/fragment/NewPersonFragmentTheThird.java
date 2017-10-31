package com.histudent.jwsoft.histudent.account.regist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.regist.activity.NewPersonActivity;
import com.histudent.jwsoft.histudent.account.regist.adapter.NewPersonAdapter;
import com.histudent.jwsoft.histudent.account.regist.bean.NewPersonModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/5.
 */

public class NewPersonFragmentTheThird extends BaseFragment {

    private View view;
    private ListView listView;
    private LinearLayout exchange_data;
    private NewPersonAdapter adapter;
    private List<NewPersonModel.ItemsBean> lists;
    private int pagIndex;
    private TextView add_class;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thethirdnewperson, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();

        NewPersonActivity.list_ids.clear();
        lists = new ArrayList<>();
        adapter = new NewPersonAdapter(getActivity(), lists, NewPersonActivity.list_ids);
        listView = (ListView) view.findViewById(R.id.list);
        exchange_data = (LinearLayout) view.findViewById(R.id.exchange_data);
        listView.setAdapter(adapter);

        add_class = (TextView) view.findViewById(R.id.add_class);
        add_class.setText(HiCache.getInstance().getLoginUserInfo().getUserType() == 3 ? "创建班级" : "加入班级");

        getData();

        exchange_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagIndex++;
                getData();
            }
        });
    }

    private void getData() {

        final Map<String, Object> map = new TreeMap<>();
        map.put("userType", HiCache.getInstance().getLoginUserInfo().getUserType());
        map.put("pagIndex", pagIndex);
        map.put("pageSize", 8);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) getActivity(), map, HistudentUrl.recommondUserList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                NewPersonModel model = JSON.parseObject(result, NewPersonModel.class);
                if (model.getItems().size() > 0) {
                    lists.clear();
                    lists.addAll(model.getItems());
                    adapter.notifyDataSetChanged();
                } else {
                    pagIndex--;
                    Toast.makeText(getActivity(), R.string.no_more_information, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }
}
