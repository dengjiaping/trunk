package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;

import me.codeboy.android.aligntextview.AlignTextView;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionGroupViewHolder extends ActionViewHolder {

    public TextView huodong_summer, huodong_title;
    public TextView huodong_content;
    public HiStudentHeadImageView huodong_cover;

    public ActionGroupViewHolder(View view) {
        huodong_summer = (TextView) view.findViewById(R.id.huodong_summer);
        huodong_title = (TextView) view.findViewById(R.id.huodong_title);
        huodong_content = (TextView) view.findViewById(R.id.huodong_content);
        huodong_cover = (HiStudentHeadImageView) view.findViewById(R.id.huodong_cover);
        setCommen(view);
    }
}
