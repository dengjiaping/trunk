package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.GroupSecondCategoryAdapter;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * 班级徽章
 */
public class ClassBadgeAcitvity extends BaseActivity {

    private MyGridView gridView;
    private GroupSecondCategoryAdapter adapter;
    private List<Object> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 班级徽章界面入口
     *
     * @param activity
     */
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ClassBadgeAcitvity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_badge_acitvity;
    }

    @Override
    public void initView() {

        ((TextView) findViewById(R.id.title_middle_text)).setText("班级徽章");
        mList = new ArrayList<>();
        gridView = ((MyGridView) findViewById(R.id.grid_view));
        adapter = new GroupSecondCategoryAdapter(mList, this);
        gridView.setAdapter(adapter);
        getFashionData();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }

    /**
     * 显示徽章数据
     */
    public void getFashionData() {
        ClassModel model = HiCache.getInstance().getClassDetailInfo();
        if (model != null) {
            mList.addAll(model.getClassBadges());
            adapter.notifyDataSetChanged();
        }
    }
}
