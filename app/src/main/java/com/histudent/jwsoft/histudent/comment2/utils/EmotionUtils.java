package com.histudent.jwsoft.histudent.comment2.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import android.widget.TextView;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.body.hiworld.emotion.util.FaceManager;
import com.histudent.jwsoft.histudent.commen.view.CustomImageSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入表情的工具类
 * Created by ZhangYT on 2016/8/11.
 */
public class EmotionUtils {


    //将输入的文本和表情设置到EditText上

    public static SpannableString convertNormalStringToSpannableString(String message) {

        if (TextUtils.isEmpty(message))
            return null;

        String re2 = "\\[(\\S+?)\\]";
        Pattern p = Pattern.compile(re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        FaceManager fm = FaceManager.getInstance();
        SpannableString value = SpannableString.valueOf(message);
        Matcher localMatcher = p.matcher(value);

        while (localMatcher.find()) {
            String str2 = localMatcher.group(0);
            String str3 = str2.replace(FaceManager.getHeaderStr() + "[", "[");


            int k = localMatcher.start();
            int m = localMatcher.end();
            if (m - k < 12) {
                int face = fm.getFaceId(str3);
                if (-1 != face) {
                    Drawable drawable = HTApplication.getInstance().getResources().getDrawable(face);
                    CustomImageSpan imageSpan = new CustomImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    drawable.setBounds(0, 0, 40, 40);//这里设置图片的大小
                    value.setSpan(imageSpan, k, m, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }
        return value;
    }



    //将输入的文本和表情设置到EditText上

    public static SpannableString convertNormalStringToSpannableString(SpannableString value) {

        if (TextUtils.isEmpty(value))
            return null;

        String re2 = "\\[(\\S+?)\\]";
        Pattern p = Pattern.compile(re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        FaceManager fm = FaceManager.getInstance();
        Matcher localMatcher = p.matcher(value);

        while (localMatcher.find()) {
            String str2 = localMatcher.group(0);
            String str3 = str2.replace(FaceManager.getHeaderStr() + "[", "[");


            int k = localMatcher.start();
            int m = localMatcher.end();
            if (m - k < 12) {
                int face = fm.getFaceId(str3);
                if (-1 != face) {
                    Drawable drawable = HTApplication.getInstance().getResources().getDrawable(face);
                    CustomImageSpan imageSpan = new CustomImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    drawable.setBounds(0, 0, 40, 40);//这里设置图片的大小
                    value.setSpan(imageSpan, k, m, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }
        return value;
    }

    public static SpannableString convertNormalStringToSpannableString(String message, final TextView tv) {
        Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
        FaceManager fm = FaceManager.getInstance();
        SpannableString value = SpannableString.valueOf(message);
        Matcher localMatcher = EMOTION_URL.matcher(value);

        while (localMatcher.find()) {
            String str2 = localMatcher.group(0);

            int k = localMatcher.start();
            int m = localMatcher.end();
            if (m - k < 16) {
                int face = fm.getFaceId(str2);
                if (-1 != face) {//wrapping with weakReference

                    Bitmap bitmap = BitmapFactory.decodeResource(HTApplication.getInstance().getResources(), face);
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

    //表情图片 进行压缩
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {

        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


}
