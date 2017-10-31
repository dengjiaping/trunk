package com.histudent.jwsoft.histudent.body.find.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;

/**
 * 社群主主页
 * Created by ZhangYT on 2016/12/21.
 */

public class GroupHomeFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.default_item, container, false);
        return view;
    }

    public static GroupHomeFragment getGroupHomeFragment(String groupId){

        GroupHomeFragment fragment=new GroupHomeFragment();
        Bundle args=new Bundle();
        args.putString("groupId",groupId);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void initView() {

        ((TextView) view.findViewById(R.id.tv)).setText("社群主页");

    }
}
