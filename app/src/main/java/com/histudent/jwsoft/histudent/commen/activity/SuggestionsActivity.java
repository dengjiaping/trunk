package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.CheckBoxView;
import com.histudent.jwsoft.histudent.comment2.utils.TellUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO;


/**
 * 意见反馈
 */
public class SuggestionsActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private EditText ed_content, et_tell;
    private TextView btn;
    private List<RelativeLayout> relativeLayouts;
    private List<CheckBoxView> images;
    private List<String> fileList;
    private RecyclerView recyclerView;
    private int width;
    private UploadImageRecyclerViewAdapter addImageAdapter;
    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener listener;
    private List<ActionListBean.ImagesBean> listImageBean = new ArrayList();
    private String contactWay, fbContent;
    private List<String> compressPicList;
    private TopMenuPopupWindow takePicPopupWindow;
    private View.OnClickListener onItemClick;
    private PictureTailorHelper clippHelper;
    private File file;
    private int limitCount = 4;//照片限制数量


    @Override
    public int setViewLayout() {
        return R.layout.activity_suggestions;
    }

    public static void start(Activity activity) {

        Intent intent = new Intent(activity, SuggestionsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void initView() {
        clippHelper = PictureTailorHelper.getInstance();
        fileList = new ArrayList<String>();
        compressPicList = new ArrayList<String>();

        ((TextView) findViewById(R.id.title_middle_text)).setText("意见反馈");
        btn = ((TextView) findViewById(R.id.btn));
        ed_content = ((EditText) findViewById(R.id.et_content));
        et_tell = ((EditText) findViewById(R.id.et_tell));
        recyclerView = ((RecyclerView) findViewById(R.id.grid_view));

        initRecyclerView();
        initLayout();
        initTextChangeListener();
        btn.setOnClickListener(this);

        initTakePicAction();

    }

    private void initTextChangeListener() {

        et_tell.addTextChangedListener(this);
        ed_content.addTextChangedListener(this);
    }

    private void initTakePicAction() {
        onItemClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicPopupWindow.dismiss();
                switch (v.getId()) {

                    //拍照
                    case R.id.btn_01:

                        checkTakePhotoPermission(new IPermissionCallBackListener() {
                            @Override
                            public void doAction() {

                                clippHelper.takePhoto(SuggestionsActivity.this);
                            }
                        });

                        break;

                    //获取本地图片
                    case R.id.btn_02:
                        fileList.remove("add");
                        clippHelper.selectPictures(SuggestionsActivity.this, (ArrayList<String>) fileList, limitCount, null);


                        break;
                }
            }
        };
    }

    private void initLayout() {

        relativeLayouts = new ArrayList<>();
        images = new ArrayList<>();
        relativeLayouts.add(((RelativeLayout) findViewById(R.id.receive_01)));
        relativeLayouts.add(((RelativeLayout) findViewById(R.id.receive_02)));
        relativeLayouts.add(((RelativeLayout) findViewById(R.id.receive_03)));
        relativeLayouts.add(((RelativeLayout) findViewById(R.id.receive_04)));
        images.add(((CheckBoxView) findViewById(R.id.iv_01)));
        images.add(((CheckBoxView) findViewById(R.id.iv_02)));
        images.add(((CheckBoxView) findViewById(R.id.iv_03)));
        images.add(((CheckBoxView) findViewById(R.id.iv_04)));
        images.get(0).setChecked(true);

    }

    private void changeSelectedImageBg(int position) {
        for (int i = 0; i < relativeLayouts.size(); i++) {
            CheckBoxView iv = images.get(i);
            if (position == i) {
                iv.setChecked(true);
            } else {
                iv.setChecked(false);
            }
        }
    }


    //根据内容来改变提交按钮的背景色
    private void changePostBtnBg() {
        if (StringUtil.isEmpty(et_tell.getText().toString()) || StringUtil.isEmpty(ed_content.getText().toString())) {
            btn.setBackground(getResources().getDrawable(R.drawable.gray_button_3dp_bg));
            btn.setEnabled(false);
        } else {
            btn.setBackground(getResources().getDrawable(R.drawable.red_button_3dp_bg));
            btn.setEnabled(true);
        }
    }

    private int getProblemType() {
        int type = 0;
        for (int i = 0; i < relativeLayouts.size(); i++) {
            CheckBoxView iv = images.get(i);
            if (iv.isChecked()) {

                switch (i) {
                    case 0:
                        type = 1;
                        break;
                    case 1:
                        type = 3;
                        break;
                    case 2:
                        type = 4;
                        break;
                    case 3:
                        type = 2;
                        break;
                    default:
                        type = 0;
                        break;
                }
            }
        }
        return type;
    }

    private void initRecyclerView() {

        fileList = new ArrayList<>();
        fileList.add("add");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        listener = new UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener() {
            @Override
            public void setOnItemClickListener(View v, int postion) {

                if (postion == fileList.size() - 1) {
                    if (fileList.get(fileList.size() - 1).equals("add")) {
                        showTakePicView();
                    } else {

                        goToScafImage(postion);
                    }

                } else {
                    goToScafImage(postion);
                }
            }
        };

        addImageAdapter = new UploadImageRecyclerViewAdapter(fileList, listener);
        recyclerView.setAdapter(addImageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(limitCount, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            //图片查看器返回没有删除图片路径的集合

            case 100:

                if (resultCode == 200) {
                    fileList.clear();
                    if (data != null)
                        fileList.addAll(data.getStringArrayListExtra("return"));
                    if (fileList.size() < limitCount) {
                        fileList.add("add");
                    }

                    addImageAdapter.notifyDataSetChanged();
                }

                break;

            //拍照数据返回
            case PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode != -1) return;

                if (fileList.size() < limitCount) {
                    fileList.add(fileList.size() - 1, clippHelper.photo_path.getAbsolutePath());
                } else {
                    fileList.remove("add");
                    fileList.add(clippHelper.photo_path.getAbsolutePath());
                }
                addImageAdapter.notifyDataSetChanged();

                break;

            case PictureTailorHelper.PHOTO_REQUEST_GALLERYS:
                if (resultCode == 200) {
                    fileList.clear();
                    List<String> list_tmp = data.getStringArrayListExtra("return");
                    if (list_tmp != null && list_tmp.size() > 0)
                        fileList.addAll(list_tmp);
                }
                if (fileList.size() < limitCount)
                    fileList.add("add");
                addImageAdapter.notifyDataSetChanged();

//                for (String s : fileList) {
//                    Log.e("FilePath--->", s);
//                }

                break;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                this.finish();
                break;


            //提交
            case R.id.btn:

                showLoadingImage(SuggestionsActivity.this, LoadingType.FLOWER);
                fileList.remove("add");
                if (fileList.size() > 0)
                    compressPicList.addAll(ImageUtils.comPressBitmaps(SuggestionsActivity.this, fileList, 80));
                doPostAction();
                break;

            case R.id.receive_01:
                changeSelectedImageBg(0);

                break;

            case R.id.receive_02:
                changeSelectedImageBg(1);

                break;
            case R.id.receive_03:
                changeSelectedImageBg(2);

                break;
            case R.id.receive_04:
                changeSelectedImageBg(3);

                break;

        }
    }


    //检查联系方式是否正确
    private boolean CheckContact() {

        contactWay = et_tell.getText().toString().trim();
        fbContent = ed_content.getText().toString().trim();

        if (TellUtils.isQQNumber(contactWay) || contactWay.length() == 11) {
            return false;

        } else {
            Toast.makeText(SuggestionsActivity.this, "联系方式填写有误！", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    //提交操作
    private void doPostAction() {

        closeLoadingImage();
        if (CheckContact())
            return;
        Map<String, Object> map = new HashMap<>();

        /**
         * 意见类型 (0 : Other , 1 : Bug , 2 : Taste , 3 : NewFunction , 4 : AboutAccount )
         */
        map.put("problemType", getProblemType() + "");
        map.put("contactWay", contactWay);
        map.put("fbContent", fbContent);
        map.put("haveImg", fileList.size() > 1 ? true : false);

        HiStudentHttpUtils.postImageByOKHttp(this, fileList, map, HistudentUrl.commitSuggestion, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {


                ReminderHelper.getIntentce().ToastShow_with_image(SuggestionsActivity.this,
                        "提交成功，谢谢你的意见！", R.string.icon_check);

//                Toast.makeText(SuggestionsActivity.this, "提交成功，谢谢你的意见！", Toast.LENGTH_LONG).show();
                ImageUtils.deleteCompressFile();//删除压缩文件
                SuggestionsActivity.this.finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    private void showTakePicView() {

        List<String> list_name = new ArrayList<>();
        list_name.add("拍照");
        list_name.add("本地图片");

        List<Integer> list_color = new ArrayList<>();
        list_color.add(Color.rgb(51, 51, 51));
        list_color.add(Color.rgb(51, 51, 51));

        takePicPopupWindow = new TopMenuPopupWindow(this, onItemClick, list_name, list_color, false);
        takePicPopupWindow.showAtLocation(findViewById(R.id.title_right), Gravity.CENTER, 0, 0);
    }

    private void goToScafImage(int postion) {

        if (fileList != null && fileList.size() > 1) {
            ActionListBean.ImagesBean imagesBean;
            listImageBean.clear();
            for (String picId : fileList) {
                if (picId.equals("add"))
                    continue;
                imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setBigSizeUrl(picId);
                imagesBean.setThumbnailUrl(picId);
                listImageBean.add(imagesBean);
            }

            ImageBrowserActivity.start(SuggestionsActivity.this, postion, 100,
                    (ArrayList<ActionListBean.ImagesBean>) listImageBean, ShowImageType.SCANF, 0, "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ImageUtils.deleteCompressFile();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        changePostBtnBg();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
