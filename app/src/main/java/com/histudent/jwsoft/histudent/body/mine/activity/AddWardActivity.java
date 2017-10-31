package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.dateSelect.TheDatePickerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/8/15.
 */
public class AddWardActivity extends BaseActivity {

    private EditText editText;
    private TextView textView, title, right;
    private ImageView image;
    private LinearLayout layout;
    private String date;
    private boolean isGetPicture;
    private String path;
    private PictureTailorHelper clippHelper;

    public static void start(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, AddWardActivity.class);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public int setViewLayout() {
        return R.layout.addward_activity;
    }

    @Override
    public void initView() {

        clippHelper = PictureTailorHelper.getInstance();
        date = SystemUtil.getCurrentData();

        editText = (EditText) findViewById(R.id.getAward_name_edit);
        textView = (TextView) findViewById(R.id.getAward_time_text);
        title = (TextView) findViewById(R.id.title_middle_text);
        right = (TextView) findViewById(R.id.title_right_text);
        image = (ImageView) findViewById(R.id.image_show);
        layout = (LinearLayout) findViewById(R.id.layout);

        textView.setText(date);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("所获奖项");
        right.setText("保存");

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.upload_picture:
            case R.id.image_show:

                clippHelper.showGetPhotoPictureListDialog(this);

                break;

            case R.id.getAward_timeb:

                if (TextUtils.isEmpty(date)) {
                    date = "1900-1-1";
                }

                String[] data_str = date.split("-");

                int[] date_time = new int[3];

                date_time[0] = Integer.parseInt(data_str[0]);
                date_time[1] = Integer.parseInt(data_str[1]);
                date_time[2] = Integer.parseInt(data_str[2]);

                String[] date_ = {"1900-1-1", "2020-1-1"};

                TheDatePickerDialog datePickerDialog = new TheDatePickerDialog(this, date_time, date_, 3, new TheDatePickerDialog.Callback() {
                    @Override
                    public void showInfo(String string) {

                        textView.setText(string);

                    }
                });

                datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                datePickerDialog.show();

                break;

            case R.id.title_left:
                Intent data_ = new Intent();
                setResult(10, data_);
                finish();
                break;

            case R.id.title_right:

                String name = editText.getText().toString();
                String time = textView.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddWardActivity.this, "请输入获奖名称", Toast.LENGTH_SHORT).show();
                } else if (time.isEmpty()) {
                    Toast.makeText(AddWardActivity.this, "请选择获奖时间", Toast.LENGTH_SHORT).show();
                } else if (judgeTime(time)) {
                    Toast.makeText(AddWardActivity.this, "请选择正确的获奖时间", Toast.LENGTH_SHORT).show();
                } else if (!isGetPicture) {
                    Toast.makeText(AddWardActivity.this, "请选择获奖封面", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> map = new TreeMap<>();
                    map.put("name", name);
                    map.put("getTime", time);

                    List<String> urls = new ArrayList<>();
                    urls.add(path);

                    HiStudentHttpUtils.postImageByOKHttp(AddWardActivity.this, urls, map, HistudentUrl.addHoner, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(AddWardActivity.this, "上传成功!", Toast.LENGTH_SHORT).show();

                            Intent data_ = new Intent();
                            setResult(20, data_);
                            finish();
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode == Activity.RESULT_OK)
                    clippHelper.startPhotoZoom(this, Uri.fromFile(clippHelper.photo_path), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_GALLERY:
                if (data != null)
                    clippHelper.startPhotoZoom(this, data.getData(), 150);
                break;


            case PictureTailorHelper.PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent intent) {
        path = clippHelper.setPicToView(null, intent);
        layout.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        CommonGlideImageLoader.getInstance().displayNetImage(this,path,image);
        isGetPicture = true;
    }

    private boolean judgeTime(String time) {
        String[] times1 = date.split("-");
        String[] times2 = time.split("-");
        String date_ = times1[0] + times1[1] + times1[2];
        String time_ = times2[0] + times2[1] + times2[2];
        return (Integer.parseInt(time_) - Integer.parseInt(date_)) > 0;
    }

}
