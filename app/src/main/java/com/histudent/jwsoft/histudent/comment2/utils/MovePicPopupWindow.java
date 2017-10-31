package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SelectFileAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.FileListBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 图片移动工具类
 * Created by ZhangYT on 2016/11/21.
 */

public class MovePicPopupWindow extends PopupWindow {


    private Button btn_cancel;
    private View mMenuView;
    private OnCloseListener onCloseListener;
    private PullToRefreshListView refreshListView;
    private List<FileListBean.ItemsBean> fileList;
    private SelectFileAdapter fileAdapter;
    private static int currentIndex;
    public static boolean isSuccess;
    private Activity context;
    private isSuccessCallBack isSuccessback;
    private int type;


    /**
     * @param context
     * @param picIds        移动图片的ids
     * @param map           需要提交的参数
     * @param isSuccessback 操作结果
     * @param type          相册的类型（0：个人相册 1：班级或者社群相册）
     */
    public MovePicPopupWindow(Activity context, List<String> picIds, Map<String, Object> map, isSuccessCallBack isSuccessback, int type) {
        super(context);
        this.type = type;
        this.context = context;
        this.isSuccessback = isSuccessback;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.move_pic_layout, null);
        currentIndex = 0;
        isSuccess = false;


        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        refreshListView = ((PullToRefreshListView) mMenuView.findViewById(R.id.refresh_listview));
        initPullToRefreshListView(context, map, picIds);


        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });


        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffececec);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                dismiss();
                return true;
            }
        });

        getAlbumInfor(map);

    }

    private void initPullToRefreshListView(final Activity context, final Map<String, Object> map, final List<String> listids) {
        fileList = new ArrayList<>();
        fileAdapter = new SelectFileAdapter(fileList, context, type);

        refreshListView.setAdapter(fileAdapter);

        PullToRefreshUtils.initPullToRefreshListView2(refreshListView);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                currentIndex++;
                getAlbumInfor(map);
            }
        });

        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                ReminderHelper.getIntentce().showDialog(context, "提示", "将图片移动到 《"
                        + fileList.get(position - 1).getAlbumName() + "》 相册？", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        movePicToOtherAlbum(listids, fileList.get(position - 1).getAlbumId());
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });


            }


        });
    }


    //将图片移动到另一个相册中
    private void movePicToOtherAlbum(List<String> listids, String albumId) {
        Map<String, Object> map2 = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        if (listids != null && listids.size() > 0) {
            builder.append("[");
            for (String ids : listids) {
                builder.append("\"").append(ids);
                builder.append("\"").append(",");
            }
            builder.deleteCharAt(builder.toString().length() - 1);
            builder.append("]");
        }

        map2.put("photoIds", builder.toString());
        map2.put("albumId", albumId);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map2, HistudentUrl.photo_move_photos_to_other_album, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                isSuccessback.isSuccess(true);
                MovePicPopupWindow.this.dismiss();
                ReminderHelper.getIntentce().ToastShow_with_image(context,
                        "移动图片成功", R.string.icon_check);
//                Toast.makeText(context, "移动图片成功！", Toast.LENGTH_LONG).show();
                context.setResult(200);
                Log.e("result111======>", result);

            }

            @Override
            public void onFailure(String msg) {
                isSuccessback.isSuccess(false);
            }
        });


    }

    private void getAlbumInfor(Map<String, Object> map) {


        Map<String, Object> map2 = new HashMap<>();
        map2.putAll(map);
        map2.put("pageSize", 4 + "");
        map.put("categoryid", "");
        map2.put("pageIndex", currentIndex);
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map2, HistudentUrl.photofilelist_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                refreshListView.onRefreshComplete();

                FileListBean albumModel = JSON.parseObject(result, FileListBean.class);
                if (albumModel != null) {
                    fileList.addAll(albumModel.getItems());
                    if (albumModel.getItems().size() == 0) {
                        Toast.makeText(context, R.string.no_more_information, Toast.LENGTH_LONG).show();
                    }
                }
                fileAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {
                refreshListView.onRefreshComplete();
            }
        });


    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (onCloseListener != null) {
            onCloseListener.onDismiss();
        }
    }

    public interface OnCloseListener {
        void onDismiss();
    }

    public interface isSuccessCallBack {
        public void isSuccess(boolean isSuccess);
    }

}
