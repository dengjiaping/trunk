package com.histudent.jwsoft.histudent.commen.photo_selecter.imageloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.photo_selecter.bean.ImageFolder;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.netease.nim.uikit.common.util.string.StringUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class SelectPhotoActivity extends BaseActivity implements ListImageDirPopupWindow.OnImageDirSelected {

    private ProgressDialog mProgressDialog;
    private boolean isFisrt;
    private List<String> list;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private final List<String> mImgs = new ArrayList<>();
    private PullToRefreshGridView mGirdView;
    private MyAdapter mAdapter;
    private int limitCount;
    private TextView title;
    private TextView tv_sure;
    public final static int CAMERA = -100;
    private PictureTailorHelper clippHelper;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFolder> mImageFolders = new ArrayList<>();
    private LinearLayout titleMiddle;
    int totalCount = 0;
    private IconView iv_tag;
    private int mScreenHeight;
    private ListImageDirPopupWindow mListImageDirPopupWindow;
    static Intent intent;
    private ArrayList<String> list_url;
    private MyAdapter.getImageCountCallBack callBack;
    private ArrayList<RelationPersonModel> relations;
    private boolean flage;
    private static final String TAG = "SelectPhotoActivity";
    private SmartRefreshLayout mRefreshLayout;


    /**
     * @param activity
     * @param urls       已经选择图片的url
     * @param limitCount 选择图片的限制数量
     * @param relations  关联关系结合，如果不需要添加图片关联关系则传空
     */
    public static void start(Activity activity, ArrayList<String> urls, int limitCount, ArrayList<RelationPersonModel> relations) {
        intent = new Intent(activity, SelectPhotoActivity.class);
        intent.putStringArrayListExtra("list", urls);
        intent.putExtra("limitCount", limitCount);
        intent.putExtra("relations", relations);//存放图片关联人信息集合
        activity.startActivityForResult(intent, PictureTailorHelper.PHOTO_REQUEST_GALLERYS);
    }
    /**
     * @param activity
     * @param urls       已经选择图片的url
     * @param limitCount 选择图片的限制数量
     */
    public static void start(Activity activity, List<String> urls, int limitCount) {
        intent = new Intent(activity, SelectPhotoActivity.class);
        intent.putStringArrayListExtra("list", (ArrayList<String>) urls);
        intent.putExtra("limitCount", limitCount);
        activity.startActivityForResult(intent, PictureTailorHelper.PHOTO_REQUEST_GALLERYS);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindow
            initListDirPopupWindow();

        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {

        if (mImgDir == null) {
            ReminderHelper.getIntentce().ToastShow_with_image(this,
                    "没扫描到任何图片", R.string.icon_remind);
            return;
        }

        mImgs.clear();
        for (ImageFolder imageFolder : mImageFolders) {
            File file = new File(imageFolder.getDir());
            for (String name : Arrays.asList(file.list())) {
                if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg")) {
                    File file1 = new File(file + "/" + name);
                    try {
                        if (FileUtil.getFileSize(file1) > 0) {
                            mImgs.add(file1.getAbsolutePath());
                        } else {
                            file1.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        Collections.sort(mImgs, new FileComparator());
        mImgs.add(0, "camera");
        initPullToRefreshGridView();
    }

    private List<String> mShowListData;
    private int mIndex;

    private void initPullToRefreshGridView() {
        if (mShowListData == null) {
            mShowListData = new ArrayList<>();
        } else {
            mShowListData.clear();
        }

        //分页加载图片 默认加载18张
        for (int i = 0; i < mImgs.size(); i++) {
            if (i == 18)
                break;
            mShowListData.add(mImgs.get(i));
        }
        PullToRefreshUtils.initPullToRefreshGridView(mGirdView);
        mGirdView.setMode(PullToRefreshBase.Mode.DISABLED);
        mAdapter = new MyAdapter(getApplicationContext(), mShowListData, R.layout.grid_item, list_url, limitCount, callBack);
        mGirdView.setAdapter(mAdapter);


        mRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            mShowListData.clear();
            mIndex = 0;
            for (int i = 0; i < mImgs.size(); i++) {
                if (i == 18)
                    break;
                mShowListData.add(mImgs.get(i));
            }
            mAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadmore();
            mRefreshLayout.finishRefresh();
        });

        mRefreshLayout.setOnLoadmoreListener((RefreshLayout refreshLayout) -> {
            mIndex += 18;
            if (mIndex < mImgs.size()) {
                for (int i = mIndex; i < mImgs.size(); i++) {
                    if (i == mIndex + 18)
                        break;
                    mShowListData.add(mImgs.get(i));
                }
                mAdapter.notifyDataSetChanged();
            } else {
                ToastTool.showCommonToast(SelectPhotoActivity.this, "暂无更多数据");
            }
            mRefreshLayout.finishLoadmore();
            mRefreshLayout.finishRefresh();
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:
                setResult(-2);
                finish();
                break;

            case R.id.id_total_count:

                setResult();

                break;

            case R.id.id_choose_dir:

                gotoImageBrower();
                break;

        }
    }


    //返回结果
    private void setResult() {

        Intent intent = new Intent();
        intent.putStringArrayListExtra("return", list_url);
        if (relations != null) {
            intent.putExtra("relations", relations);
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        setResult(200, intent);
        finish();
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindow() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, SystemUtil.dp2px(295),
                mImageFolders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                iv_tag.setText(R.string.icon_arrowbottom);
                findViewById(R.id.frame1).setVisibility(View.GONE);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_selecter_photo;
    }

    @Override
    public void initView() {
        clippHelper = PictureTailorHelper.getInstance(false);
        mGirdView = (PullToRefreshGridView) findViewById(R.id.id_gridView);
        titleMiddle = (LinearLayout) findViewById(R.id.title_middle);


        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        iv_tag = ((IconView) findViewById(R.id.title_middle_image));
        iv_tag.setVisibility(View.VISIBLE);
        iv_tag.setText(R.string.icon_arrowbottom);
        title = (TextView) findViewById(R.id.title_middle_text);
        title.setText("相机胶卷");
        tv_sure = ((TextView) findViewById(R.id.id_total_count));

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_refresh_layout);
        mRefreshLayout.setEnableAutoLoadmore(true);
        list_url = new ArrayList<>();

        if (intent != null) {
            if (intent.getSerializableExtra("relations") != null) {
                relations = new ArrayList<>();
                relations.addAll((ArrayList<RelationPersonModel>) intent.getSerializableExtra("relations"));
            }
        }

        callBack = new MyAdapter.getImageCountCallBack() {
            @Override
            public void getImageCount(int count) {
                if (count != CAMERA) {

                    //选择本地图片
                    if (limitCount != 1) {
                        changTextColor(list_url);
                        if (limitCount != 0) {
                            if (count == limitCount) {

                                if (isFisrt) {

                                    ReminderHelper.getIntentce().ToastShow_with_image(SelectPhotoActivity.this,
                                            "最多只能选择" + limitCount + "张", R.string.icon_remind);
                                } else {
                                    isFisrt = true;
                                    tv_sure.setText("完成(" + count + ")");
                                }
                            } else {
                                tv_sure.setText("完成(" + count + ")");
                            }

                        } else {
                            tv_sure.setText("完成(" + count + " )");
                        }
                    } else {
                        setResult();
                    }

                    //拍照
                } else {
                    if (list_url.size() >= limitCount) {
                        ReminderHelper.getIntentce().ToastShow_with_image(SelectPhotoActivity.this,
                                "最多只能选择" + limitCount + "张", R.string.icon_remind);
                    } else {
                        clippHelper.takePhoto(SelectPhotoActivity.this);
                    }
                }

            }
        };
        list = new ArrayList<>();
        list_url.addAll(getIntent().getStringArrayListExtra("list"));
        list.addAll(list_url);
        limitCount = getIntent().getIntExtra("limitCount", 0);

        if (limitCount != 1) {
            if (limitCount != 0) {
                tv_sure.setText("完成(" + list_url.size() + ")");
            } else {
                tv_sure.setText("完成(" + list_url.size() + " )");
            }
            changTextColor(list_url);
        } else {
            findViewById(R.id.title_right_text).setVisibility(View.GONE);
        }

        getImages();
        initEvent();

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = SelectPhotoActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFolder imageFolder = null;

                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {

                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFolder = new ImageFolder();
                        imageFolder.setDir(dirPath);
                        imageFolder.setFirstImagePath(path);
                    }

                    if (parentFile.list() == null) continue;

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFolder.setCount(picSize);
                    mImageFolders.add(imageFolder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    private class FileComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            File file1 = new File(lhs);
            File file2 = new File(rhs);

            if (file1.lastModified() < file2.lastModified()) {
                return 1;//最后修改的照片在前
            } else if (file1.lastModified() > file2.lastModified()) {
                return -1;
            } else {
                return 0;
            }
        }
    }


    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        titleMiddle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_tag.setText(R.string.icon_arrowup);
                mListImageDirPopupWindow.showAsDropDown(findViewById(R.id.title_middle), 0, 0);

                findViewById(R.id.frame1).setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void selected(ImageFolder folder) {
        mImgs.clear();
        mImgDir = new File(folder.getDir());
        List<String> imageList = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));

        for (String imgPath : imageList) {
            mImgs.add(folder.getDir() + "/" + imgPath);
        }

        Collections.sort(mImgs, new FileComparator());
        mImgs.add(0, "camera");

        mAdapter = new MyAdapter(getApplicationContext(), mImgs, R.layout.grid_item, list_url, limitCount, callBack);
        mGirdView.setAdapter(mAdapter);
        if (!StringUtil.isEmpty(folder.getName()) && folder.getName().contains("/")) {
            title.setText(folder.getName().substring(folder.getName().indexOf("/") + 1));
        } else {
            title.setText(folder.getName());
        }
        mListImageDirPopupWindow.dismiss();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }


    //跳转到预览界面
    public void gotoImageBrower() {

        List<ActionListBean.ImagesBean> listBeanList = new ArrayList<>();
        if (list_url != null && list_url.size() > 0) {
            ActionListBean.ImagesBean imagesBean;
            listBeanList.clear();
            for (String picId : list_url) {
                File f = new File(picId);
                imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setBigSizeUrl(picId);
                imagesBean.setName(f.getName());
                imagesBean.setThumbnailUrl(picId);
                listBeanList.add(imagesBean);
            }

            if (relations == null) {
                ImageBrowserActivity.start(this, 0, 100, (ArrayList<ActionListBean.ImagesBean>) listBeanList,
                        ShowImageType.SCANF, null);
            } else {
                ImageBrowserActivity.start(this, 0, 100, (ArrayList<ActionListBean.ImagesBean>) listBeanList,
                        ShowImageType.DISMISS_RELATION, relations);
            }

        } else {
            Toast.makeText(this, "请先选择图片！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode:" + requestCode + "resultCode" + resultCode);
        switch (requestCode) {
            case 100:

                if (resultCode == 200) {
                    if (data != null) {
                        ArrayList<String> list = data.getStringArrayListExtra("return");

                        if (data.getSerializableExtra("relations") != null && relations != null) {
                            relations.clear();
                            relations.addAll((ArrayList<RelationPersonModel>) data.getSerializableExtra("relations"));
                        }

                        if (list != null) {
                            tv_sure.setText("完成(" + list.size() + ")");
                            changTextColor(list);
                            list_url.clear();
                            list_url.addAll(list);
                            mAdapter = new MyAdapter(getApplicationContext(), mImgs, R.layout.grid_item, list_url, limitCount, callBack);
                            mGirdView.setAdapter(mAdapter);
                            flage = true;

                        }
                    }
                }

                //拍照返回
            case PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode == -1) {

                    if (limitCount == 1) {
                        list_url.clear();
                    }
                    final String absolutePath = clippHelper.photo_path.getAbsolutePath();
                    if (absolutePath != null) {
                        list_url.add(absolutePath);
                    }
                    setResult();
                }
        }
    }

    //改变发布的点击状态
    private void changTextColor(List<String> list) {
        if (list != null && list.size() > 0) {
            tv_sure.setBackground(getResources().getDrawable(R.drawable.btn_click_true));
            tv_sure.setEnabled(true);
        } else {
            tv_sure.setBackground(getResources().getDrawable(R.drawable.btn_click_false));
            tv_sure.setEnabled(false);
        }
    }

}
