package com.histudent.jwsoft.histudent.body.homepage.holder;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class AlbumViewHolder extends ViewHolder {
    public TextView title;
    public GridView picture_list;

    public AlbumViewHolder(View view) {
        title = (TextView) view.findViewById(R.id.action_album_title);
        picture_list = (GridView) view.findViewById(R.id.action_album_list);
        setBottom(view);
    }
}
