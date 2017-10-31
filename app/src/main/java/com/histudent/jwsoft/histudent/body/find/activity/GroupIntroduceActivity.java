package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.GroupImageListAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2017/5/19.
 * 社群介绍
 */

public class GroupIntroduceActivity extends BaseActivity {

    private TextView titile;
    private ImageView cover;
    private TextView introduce;
    private GroupBean groupBean;
    private MyListView2 image_list;
    private GroupImageListAdapter adapter;
    private List<GroupBean.GroupDescriptionImgsListBean> images;

    public static void start(Activity context, GroupBean groupBean) {
        Intent intent = new Intent(context, GroupIntroduceActivity.class);
        intent.putExtra("groupBean", groupBean);
        context.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_group_introduce;
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

    @Override
    public void initView() {
        groupBean = (GroupBean) getIntent().getSerializableExtra("groupBean");
        images = new ArrayList<>();
        titile = (TextView) findViewById(R.id.title_middle_text);
        cover = (ImageView) findViewById(R.id.group_introduce_cover);
        introduce = (TextView) findViewById(R.id.group_introduce_content);
        image_list = (MyListView2) findViewById(R.id.group_introduce_image);
        adapter = new GroupImageListAdapter(this, images);
        image_list.setAdapter(adapter);
    }

    @Override
    public void doAction() {
        super.doAction();

        titile.setText("社群介绍");
        if (groupBean == null) return;
//        MyImageLoader.getIntent().displayNetImageWithAnimation(this, groupBean.getGroupLogo(), cover, R.mipmap.person_center_cover);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(this, groupBean.getGroupLogo(),
                cover, ContextCompat.getDrawable(this, R.mipmap.person_center_cover));
        SystemUtil.showHtml(introduce, this, groupBean.getGroupDescription());
        List<GroupBean.GroupDescriptionImgsListBean> paths = groupBean.getGroupDescriptionImgsList();
        if (paths != null && paths.size() > 0) {
            images.addAll(paths);
            adapter.notifyDataSetChanged();
        }

    }
}
