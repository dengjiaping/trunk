package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.ListenerUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.setOnClickListener;
import com.histudent.jwsoft.histudent.comment2.bean.NetImageModel;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;


/*
     创建活动第二步（通用界面，填写活动内容，添加活动介绍图片信息）
 */
public class CreateHuoDongSeond extends BaseActivity implements View.OnClickListener {

    private TextView tv_done, tv_pic_num;
    private Intent intent;
    private setOnClickListener onItemClickListener;

    private CreateHuoDongBean activityBean;
    private EditText et_instr;
    private List<String> deleteImageIds;
    private ArrayList<String> fileList;
    private PictureTailorHelper clippHelper;
    private LinearLayout imageLayout;
    private int type;


    /**
     * @param activity
     * @param createActivityBean 保存了活动内容的实体类具体详情看实体类介绍
     * @param type               类型  1： 班级活动 2:社群活动，3：社群介绍
     * @param requestCode
     */
    public static void start(Activity activity, CreateHuoDongBean createActivityBean, int type, int requestCode) {
        Intent intent = new Intent(activity, CreateHuoDongSeond.class);
        intent.putExtra("infor", createActivityBean);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_create_activity2_;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        clippHelper = PictureTailorHelper.getInstance(false);
        activityBean = (CreateHuoDongBean) getIntent().getSerializableExtra("infor");
        type = getIntent().getIntExtra("type", 0);
        imageLayout = ((LinearLayout) findViewById(R.id.linear));
        fileList = new ArrayList<>();
        deleteImageIds = new ArrayList<>();
        ((TextView) findViewById(R.id.title_left_text)).setText("返回");
        ((TextView) findViewById(R.id.title_left_text)).setVisibility(View.VISIBLE);
        findViewById(R.id.title_left_image).setVisibility(View.GONE);

        tv_done = ((TextView) findViewById(R.id.title_right_text));
        tv_pic_num = ((TextView) findViewById(R.id.tv_pic_num));

        et_instr = ((EditText) findViewById(R.id.et_instr));

        tv_done.setText(R.string.completed);

        onItemClickListener = new setOnClickListener() {
            @Override
            public void setOnClickItemListener(Object o) {

                NetImageModel imageModel = ((NetImageModel) o);
                if (!StringUtil.isEmpty(imageModel.getId())) {
                    deleteImageIds.add(imageModel.getId());
                    activityBean.getInstrFileList().remove(o);
                } else {
                    fileList.remove(imageModel.getUrl());
                }
                tv_pic_num.setText("添加图片" + (getNetImageSize() + fileList.size()) + "/3张");

            }
        };

        updateUi();

    }

    //更新界面
    private void updateUi() {


        switch (type) {

            case 3:
                ((TextView) findViewById(R.id.title_middle_text)).setText("社群介绍");
                et_instr.setHint("请输入社群介绍");
                break;

            default:

                et_instr.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
                ((TextView) findViewById(R.id.title_middle_text)).setText("活动介绍");
                ListenerUtils.setTextChangeListener(et_instr, this, "长度超出范围");
                break;

        }
        if (activityBean != null) {
            if (activityBean.getInstrFileList() != null) {

                addPictures();
            }

            if (activityBean.getImages() != null) {
                fileList.addAll(activityBean.getImages());
                addPicturesForLocal(activityBean.getImages());
            }
            tv_pic_num.setText("添加图片" + (getNetImageSize() + fileList.size()) + "/3张");
            et_instr.setText(activityBean.getInstruction());
            et_instr.setSelection(et_instr.getText().toString().length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.title_left:

                if (!StringUtil.isEmpty(et_instr.getText().toString().trim()) && fileList.size() > 0) {
                    ReminderHelper.getIntentce().showDialog(this, "提示", "是否放弃修改？", "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                            setResult(-1);
                            CreateHuoDongSeond.this.finish();
                        }
                    }, "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    });
                } else {
                    setResult(-1);
                    CreateHuoDongSeond.this.finish();
                }

                break;

            //点击完成后的处理
            case R.id.title_right:

                setResult();

                break;


            case R.id.bottom:
                ViewUtils.hideSoftKeyBoard(this);
                checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        clippHelper.selectPictures(CreateHuoDongSeond.this, fileList, 3 - getNetImageSize(), null);
                    }
                });


                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PictureTailorHelper.PHOTO_REQUEST_GALLERYS && resultCode == 200) {
            if (data != null) {
                fileList.clear();
                List<String> urls = data.getStringArrayListExtra("return");
                fileList.addAll(urls);
                addPictures();
                addPicturesForLocal(fileList);
                tv_pic_num.setText("添加图片" + (getNetImageSize() + fileList.size()) + "/3张");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addPictures() {
        imageLayout.removeAllViews();
        if (activityBean.getInstrFileList() != null) {
            for (NetImageModel url : activityBean.getInstrFileList()) {
                ComViewUitls.addPictureWithEdit(this, url, imageLayout, onItemClickListener);
            }
        }

    }

    private void addPicturesForLocal(List<String> list) {
        if (list != null) {
            for (String url : list) {
                NetImageModel imageModel = new NetImageModel();
                imageModel.setUrl(url);
                //根据图片尺寸重设imageView大小
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(url, options);
                int h = options.outHeight;
                int w = options.outWidth;
                imageModel.setWidth(SystemUtil.getScreenWind());
                imageModel.setHeight(imageModel.getWidth() * h / w);
                imageModel.setIsLocal(true);
                ComViewUitls.addPictureWithEdit(this, imageModel, imageLayout, onItemClickListener);
            }
        }

    }


    //返回网络图片的数量
    private int getNetImageSize() {
        if (activityBean != null && activityBean.getInstrFileList() != null) {

            return activityBean.getInstrFileList().size();
        }
        return 0;
    }

    private void setResult() {

        activityBean.setInstruction(et_instr.getText().toString());
        activityBean.setImages(fileList);
        activityBean.setDeleteImageId(deleteImageIds);

        for (String id : deleteImageIds) {
            Log.e("---------", id);
        }
        for (String id : fileList) {
            Log.e("------------", id);
        }

        Intent intent = new Intent();
        intent.putExtra("infor", activityBean);
        setResult(200, intent);
        this.finish();
    }
}
