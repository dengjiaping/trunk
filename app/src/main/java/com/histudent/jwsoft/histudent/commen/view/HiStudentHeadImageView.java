package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.lidroid.xutils.BitmapUtils;
import com.netease.nim.uikit.ImageLoaderKit;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.nos.model.NosThumbParam;
import com.netease.nimlib.sdk.nos.util.NosThumbImageUtil;
import com.netease.nimlib.sdk.team.model.Team;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by huangjun on 2015/11/13.
 */
public class HiStudentHeadImageView extends CircleImageView {

    private static final String fileName = "cacheFile";
    private BitmapUtils bitmapUtils;
    private String cachePath;

    public static final int DEFAULT_AVATAR_THUMB_SIZE = (int) NimUIKit.getContext().getResources().getDimension(R.dimen.avatar_max_size);
    public static final int DEFAULT_AVATAR_NOTIFICATION_ICON_SIZE = (int) NimUIKit.getContext().getResources().getDimension(R.dimen.avatar_notification_size);

    private DisplayImageOptions options = createImageOptions();

    private static final DisplayImageOptions createImageOptions() {
        int defaultIcon = NimUIKit.getUserInfoProvider().getDefaultIconResId();
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultIcon)
                .showImageOnFail(defaultIcon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public HiStudentHeadImageView(Context context) {
        super(context);
    }

    public HiStudentHeadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HiStudentHeadImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 加载用户头像（默认大小的缩略图）
     */
    public void loadBuddyAvatar(String url) {
        loadBuddyAvatar(url, R.mipmap.avatar_def);
    }

    /**
     * 加载图片（默认大小的缩略图）
     */
    public void loadBuddyAvatar(String url, int defImageId) {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        BitmapUtils bitmapUtils = new BitmapUtils(HiStudentApplication.getInstance(), "/data/data/files/imgs", cacheSize);

        bitmapUtils.configDefaultBitmapMaxSize(120, 120);
        bitmapUtils.configDefaultLoadingImage(defImageId);
        bitmapUtils.configDefaultLoadFailedImage(defImageId);
        bitmapUtils.display(this, url);

    }


    public void loadTeamIcon(String tid) {
        Bitmap bitmap = NimUIKit.getUserInfoProvider().getTeamIcon(tid);
        setImageBitmap(bitmap);
    }

    public void loadTeamIconByTeam(final Team team) {
        // 先显示默认头像
        setImageResource(R.drawable.nim_avatar_group);

        // 判断是否需要ImageLoader加载
        boolean needLoad = team != null && ImageLoaderKit.isImageUriValid(team.getIcon());

        doLoadImage(needLoad, team != null ? team.getId() : null, team.getIcon(), DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * ImageLoader异步加载
     */
    private void
    doLoadImage(final boolean needLoad, final String tag, final String url, final int thumbSize) {
        if (needLoad) {
            setTag(tag); // 解决ViewHolder复用问题
            /**
             * 若使用网易云信云存储，这里可以设置下载图片的压缩尺寸，生成下载URL
             * 如果图片来源是非网易云信云存储，请不要使用NosThumbImageUtil
             */
            final String thumbUrl = makeAvatarThumbNosUrl(url, thumbSize);
//            final String thumbUrl = url;

            // 异步从cache or NOS加载图片
            ImageLoader.getInstance().displayImage(thumbUrl, new NonViewAware(new ImageSize(thumbSize, thumbSize),
                    ViewScaleType.CROP), options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (getTag() != null && getTag().equals(tag)) {
                        setImageBitmap(loadedImage);
                    }
                }
            });
        } else {
            setTag(null);
        }
    }

    /**
     * 解决ViewHolder复用问题
     */
    public void resetImageView() {
        setImageBitmap(null);
    }

    /**
     * 生成头像缩略图NOS URL地址（用作ImageLoader缓存的key）
     */
    private static String makeAvatarThumbNosUrl(final String url, final int thumbSize) {
        return thumbSize > 0 ? NosThumbImageUtil.makeImageThumbUrl(url, NosThumbParam.ThumbType.Crop, thumbSize, thumbSize) : url;
    }

    public static String getAvatarCacheKey(final String url) {
        return makeAvatarThumbNosUrl(url, DEFAULT_AVATAR_THUMB_SIZE);
    }
}
