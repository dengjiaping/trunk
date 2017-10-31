package com.histudent.jwsoft.histudent.body.hiworld.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.hiworld.emotion.util.FaceManager;
import com.histudent.jwsoft.histudent.commen.view.CircleImageView;
import com.histudent.jwsoft.histudent.comment2.bean.CommentBean;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhangYT on 2016/6/24.
 */
public class CommentAdpater extends BaseAdapter {
    private Context context;
    private List<CommentBean> list;
    private Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
    private FaceManager fm;


    public CommentAdpater(List<CommentBean> list, Context context) {
        this.context = context;
        this.list = list;
        fm = FaceManager.getInstance();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        convertView = LayoutInflater.from(context).inflate(R.layout.comment, null);


        if (list.get(position).getReplyName() != null) {


            //表示添加回复页面
            viewHolder = new ViewHolder(convertView, true);
            viewHolder.tv_to_name.setText(list.get(position).getReplyName());

        } else {

            viewHolder = new ViewHolder(convertView, false);

        }


        viewHolder.tv_name.setText(list.get(position).getName());
        if (!StringUtil.isEmpty(list.get(position).getContent())) {
            viewHolder.tv_comment.setText(convertNormalStringToSpannableString(list.get(position).getContent(), viewHolder.tv_comment));
        }
        viewHolder.tv_time.setText(list.get(position).getTime());


        return convertView;

    }

    class ViewHolder {
        private CircleImageView iv;
        private TextView tv_name, tv_to_name, tv_comment, tv_time, tv;

        public ViewHolder(View convertView, boolean tag) {
            iv = ((CircleImageView) convertView.findViewById(R.id.iv));
            tv_name = ((TextView) convertView.findViewById(R.id.tv_name));
            tv_comment = ((TextView) convertView.findViewById(R.id.tv_comment));
            tv_time = ((TextView) convertView.findViewById(R.id.tv_time));
            tv = ((TextView) convertView.findViewById(R.id.tv));
            tv.setVisibility(View.GONE);


            //表示为回复页面
            if (tag) {
                tv_to_name = ((TextView) convertView.findViewById(R.id.tv_to_name));
                tv.setVisibility(View.VISIBLE);
            }
        }
    }


    private SpannableString convertNormalStringToSpannableString(String message, final TextView tv) {
        SpannableString value = SpannableString.valueOf(message);
        Matcher localMatcher = EMOTION_URL.matcher(value);

        while (localMatcher.find()) {
            String str2 = localMatcher.group(0);

            int k = localMatcher.start();
            int m = localMatcher.end();
            if (m - k < 16) {
                int face = fm.getFaceId(str2);
                if (-1 != face) {//wrapping with weakReference

                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), face);
                    bitmap = zoomImage(bitmap, 40, 40);
                    Drawable drawable = new BitmapDrawable(bitmap);

                    ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    drawable.setBounds(0, 0, 40, 40);//这里设置图片的大小
                    tv.postInvalidate();
                    value.setSpan(imageSpan, k, m, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }
        return value;
    }

    public Bitmap zoomImage(Bitmap bgimage, double newWidth,
                            double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
