package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class GroupAuthoritySelectActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout relative_public, relative_private;
    private ImageView iv_public, iv_private, iv_back;

    private List<ImageView> images;
    private Intent intent;
    private String authority;

    @Override
    public int setViewLayout() {
        return R.layout.activity_group_authority_select;
    }

    public static void start(Activity activity, String authority, int requestCode) {
        Intent intent = new Intent(activity, GroupAuthoritySelectActivity.class);
        intent.putExtra("authority", authority);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
        intent = getIntent();
        authority = intent.getStringExtra("authority");
        if (authority.equals("群权限")) {
            authority = "公开";
        }
        relative_private = ((RelativeLayout) findViewById(R.id.relative_privata));
        relative_public = ((RelativeLayout) findViewById(R.id.relative_public));
        iv_public = ((ImageView) findViewById(R.id.iv_public));
        iv_private = ((ImageView) findViewById(R.id.iv_private));
        iv_back = ((ImageView) findViewById(R.id.title_left_image));
        ((TextView) findViewById(R.id.title_middle_text)).setText("隐私");


//        iv_back.setOnClickListener(this);
        relative_private.setOnClickListener(this);
        relative_public.setOnClickListener(this);

        initImageBackGroud();
    }

    private void initImageBackGroud() {
        images = new ArrayList<>();
        images.add(iv_public);
        images.add(iv_private);

        for (int i = 0; i < images.size(); i++) {
            images.get(i).setImageResource(R.drawable.checked_selector);
            images.get(i).setSelected(false);
        }

        if (authority.equals("公开")) {
            images.get(0).setSelected(true);
        } else {
            images.get(1).setSelected(true);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //返回
            case R.id.title_left:
                if (StringUtil.isEmpty(getSelectedAuthority())) {
                    Toast.makeText(this, "请选择访问权限", Toast.LENGTH_LONG).show();
                } else {
                    intent.putExtra("authority", getSelectedAuthority());
                    setResult(600, intent);
                    this.finish();
                }
                break;

            //私有
            case R.id.relative_privata:
                changeImageSelecetBack(1);

                break;

            //公开
            case R.id.relative_public:
                changeImageSelecetBack(0);
                break;

        }
    }

    //改变被选中的图片的背景颜色状态
    private void changeImageSelecetBack(int position) {

        for (int i = 0; i < images.size(); i++) {
            if (position == i) {
                images.get(i).setSelected(true);
            } else {
                images.get(i).setSelected(false);
            }
        }
    }

    //获取选择的权限
    private String getSelectedAuthority() {

        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).isSelected()) {
                if (i == 0) {
                    return "公开";
                } else {
                    return "不公开";
                }
            }
        }

        return null;
    }

}
