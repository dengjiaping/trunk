package com.histudent.jwsoft.histudent.body.hiworld.helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.Dialog_confirm;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.utils.FileUtils;
import com.histudent.jwsoft.histudent.comment2.utils.MovePicPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/2/22.
 */

public class ImageActivityHelper {

    private TopMenuPopupWindow menuWindow;
    private Activity activity;
    private Handler handler;
    private ActionListBean.ImagesBean bean;
    private View.OnClickListener itemsOnClick;
    int type_from;//0:个人 ; 1:班级 ; 2:社群
    private String id;
    private PictureTailorHelper clippHelper;

    public ImageActivityHelper(Activity activity, Handler handler, ActionListBean.ImagesBean bean, PictureTailorHelper clippHelper, int type_from, String id) {
        this.activity = activity;
        this.handler = handler;
        this.bean = bean;
        this.clippHelper = clippHelper;
        this.type_from = type_from;
        this.id = id;
    }

    public void setImageBean(ActionListBean.ImagesBean bean) {
        this.bean = bean;
    }

    /**
     * 显示底部菜单栏
     */
    public void showBottomMenue(final ShowImageType type) {
        List<String> list_name = new ArrayList<>();
        List<Integer> list_color = new ArrayList<>();
        switch (type) {
            case EXCHANGE:
                list_name.add("拍照");
                list_name.add("从相册中选择");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                break;
            case MOVE:
                //当该相册为自己的相册时，显示转移相册功能
                list_name.add("保存到本地");
                list_name.add("转移到其他相册");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                break;
            case SHOW:
                //当该相册为自己的相册时，显示转移相册功能
                list_name.add("保存到本地");
                list_name.add("转移到其他相册");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                break;
            case EDIT:
                list_name.add("设为封面");
                list_name.add("移 动");
                list_name.add("修 改");
                list_name.add("保存到本地");
                list_name.add("删 除");
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(51, 51, 51));
                list_color.add(Color.rgb(255, 88, 88));
                break;

        }

        itemsOnClick = new View.OnClickListener() {

            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_01:
                        switch (type) {
                            case EXCHANGE:
                                clippHelper.takePhoto(activity);
                                break;
                            case MOVE:
                            case SHOW:
                                saveImageAction();
                                break;
                            case EDIT:
                                ((BaseActivity) activity).checkTakePhotoPermission(new IPermissionCallBackListener() {
                                    @Override
                                    public void doAction() {
                                        setCover();
                                    }
                                });
                                break;
                        }
                        break;

                    case R.id.btn_02:
                        switch (type) {
                            case EXCHANGE:
                                clippHelper.selectPicture(activity);
                                break;
                            case MOVE:
                            case SHOW:
                            case EDIT:
                                movePicture();
                                break;
                        }
                        break;

                    case R.id.btn_03:
                        exhangeImageName();
                        break;

                    case R.id.btn_04:
                        saveImageAction();
                        break;

                    case R.id.btn_05:
                        deletePhoto();


                        break;

                    default:
                        break;
                }
            }
        };

        menuWindow = new TopMenuPopupWindow(activity, itemsOnClick, list_name, list_color, false);
        menuWindow.showAtLocation(activity.findViewById(R.id.layout), Gravity.CENTER, 0, 0);
    }

    /**
     * 保存图片到本地
     */
    public void saveImageAction() {
        if (FileUtils.imageFileIsExist(bean.getBigSizeUrl())) {

            ReminderHelper.getIntentce().ToastShow_with_image(activity,
                    "图片已经存在", R.string.icon_remind);
//            Toast.makeText(activity, "图片已经存在！", Toast.LENGTH_LONG).show();
        } else {
            FileUtils.saveImageToAlbum(activity, handler, 0, bean.getBigSizeUrl());
        }
    }

    /**
     * 上传相册封面
     */
    public void setCover() {

        Map<String, Object> map = new TreeMap<>();
        map.put("photoId", bean.getImgId());
        map.put("isCover", true);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.setalbumcover_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                ReminderHelper.getIntentce().ToastShow_with_image(activity,
                        "设置封面成功", R.string.icon_check);
//                Toast.makeText(activity, "设置封面成功!", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(result);
                    Intent cover_url = new Intent();
                    cover_url.putExtra("cover_url", object.optString("url"));
                    activity.setResult(300, cover_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 移动照片
     */
    public void movePicture() {

        MovePicPopupWindow.isSuccessCallBack isSuccessCallBack = new MovePicPopupWindow.isSuccessCallBack() {
            @Override
            public void isSuccess(boolean isSuccess) {

                if (isSuccess) {
                    handler.sendEmptyMessage(2);
                }
            }
        };

        List<String> list_imageId = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ownerId", id);
        switch (type_from) {
            case 0:
                map.put("ownerType", "1");
                break;
            case 1:
                map.put("ownerType", "2");
                break;
            case 2:
                map.put("ownerType", "3");
                break;
        }
        list_imageId.add(bean.getImgId());
        MovePicPopupWindow movePicPopupWindow = new MovePicPopupWindow(activity, list_imageId, map, isSuccessCallBack, type_from);
        movePicPopupWindow.showAtLocation(activity.findViewById(R.id.title_left), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 修改照片名
     */
    public void exhangeImageName() {
        final Dialog_confirm confirmDialog = new Dialog_confirm(activity, bean.getName(), "取消", "确定");
        confirmDialog.show();
        confirmDialog.setClicklistener(new Dialog_confirm.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                confirmDialog.dismiss();

            }

            @Override
            public void doCancel(final String info) {
                confirmDialog.dismiss();

                if (TextUtils.isEmpty(info))
                    return;

                Map<String, Object> map = new TreeMap<>();
                map.put("name", info);
                map.put("photoId", bean.getImgId());

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.renamephoto, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        bean.setName(info);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

            }
        });
        confirmDialog.setTitle("修改照片名");
    }

    /**
     * 删除照片
     */
    public void deletePhoto() {

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定删除该张图片？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new TreeMap<>();
                map.put("photoIds", "[" + "\"" + bean.getImgId() + "\"" + "]");

                switch (type_from) {
                    case 0:
                        map.put("ownerType", "1");
                        break;
                    case 1:
                        map.put("ownerType", "2");
                        break;
                    case 2:
                        map.put("ownerType", "3");
                        break;
                }

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.album_delete_images, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        handler.sendEmptyMessage(3);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });

    }

    /**
     * 删除图片的操作
     */
    public void doDeleteImageAction() {

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定删除该张图片？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                handler.sendEmptyMessage(4);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });
    }

}
