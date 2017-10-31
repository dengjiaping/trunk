package com.histudent.jwsoft.histudent.body.homepage.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.RichTextView;

import butterknife.BindView;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class EssayViewHolder extends ViewHolder {

    public TextView action_essay_images;
    public RichTextView content;
    public ImageView action_essay_cover;
    public IconView video_play;
    public RelativeLayout action_essay_layout;
//
//    @BindView(R.id.action_essay_images)
//    TextView mEssayImges;
//    @BindView(R.id.action_essay_content)
//    RichTextView mContent;
//    @BindView(R.id.action_essay_cover)
//    ImageView mCover;
//    @BindView(R.id.video_play)
//    IconView mVideoPlay;
//    @BindView(R.id.action_essay_layout)
//    RelativeLayout mActionEssay;
    public EssayViewHolder(View view) {
        action_essay_images = (TextView) view.findViewById(R.id.action_essay_images);
        content = (RichTextView) view.findViewById(R.id.action_essay_content);
        action_essay_cover = (ImageView) view.findViewById(R.id.action_essay_cover);
        video_play = (IconView) view.findViewById(R.id.video_play);
        action_essay_layout = (RelativeLayout) view.findViewById(R.id.action_essay_layout);
        setBottom(view);
    }
}
