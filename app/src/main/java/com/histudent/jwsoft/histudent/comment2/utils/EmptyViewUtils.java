package com.histudent.jwsoft.histudent.comment2.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by ZhangYT on 2016/8/24.
 */
public class EmptyViewUtils {


    //网络数据没有返回时，填充的空布局
    public static ImageView EmptyView(int ResId, Context context) {
        ImageView textView = new ImageView(context);
        textView.setImageResource(ResId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 200);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        return textView;
    }

    //网络数据没有返回时，填充的空布局

    /**
     * @param DefaultImageId 默认图片资源id
     * @param context
     * @param DefaultTextId  默认提示内容
     * @return
     */
    public static View EmptyViewTextAndImage(int DefaultImageId, Context context, int DefaultTextId) {

        View view = View.inflate(context, R.layout.empty_view, null);
        ImageView iv = ((ImageView) view.findViewById(R.id.iv));
        TextView tv = ((TextView) view.findViewById(R.id.tv));
        tv.setTextColor(context.getResources().getColor(R.color.text_gray_h1));
        iv.setImageResource(DefaultImageId);
        tv.setText(DefaultTextId);
        return view;
    }


    /**
     * @param DefaultImageId 默认图片资源id
     * @param context
     * @param DefaultTextId  默认提示内容
     * @return
     */
    public static View EmptyViewTextAndImage(int DefaultImageId, Context context, String DefaultTextId) {

        final View view = View.inflate(context, R.layout.empty_view, null);

        ImageView iv = ((ImageView) view.findViewById(R.id.iv));
        TextView tv = ((TextView) view.findViewById(R.id.tv));
        tv.setTextColor(context.getResources().getColor(R.color.text_gray_h1));
        iv.setImageResource(DefaultImageId);
        tv.setText(DefaultTextId);
        return view;
    }


    /**
     * @param context
     * @param DefaultImageId      默认图片资源id
     * @param DefaultTextId       TextView默认提示内容
     * @param DefaultButtonTextId Button默认提示内容
     * @param listener            Button的监听事件
     * @return
     */
    public static View NewEmptyViewTextAndImage(Context context, int DefaultImageId,
                                                int DefaultTextId, int DefaultButtonTextId, View.OnClickListener listener) {

        View view = View.inflate(context, R.layout.new_empty_view, null);

        ImageView mImageView = (ImageView) view.findViewById(R.id.empty_view_iv);
        TextView mTextView = (TextView) view.findViewById(R.id.empty_view_tv);
        TextView mButton = (TextView) view.findViewById(R.id.empty_view_btn);

        mTextView.setTextColor(context.getResources().getColor(R.color.text_gray_h1));
        mImageView.setImageResource(DefaultImageId);
        mTextView.setText(DefaultTextId);
        mButton.setText(DefaultButtonTextId);

        mButton.setOnClickListener(listener);
        return view;
    }

}
