package com.histudent.jwsoft.histudent.body.mine.viewholder;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;

import me.codeboy.android.aligntextview.AlignTextView;

/**
 * Created by liuguiyu-pc on 2017/4/21.
 */

public class ActionAlbumViewHolder extends ActionViewHolder {
    public TextView title, action_album_name;
    public GridView picture_list;
    public TextView album_summer;
    public TextView action_album_title;

    public ActionAlbumViewHolder(View view) {
        title = (TextView) view.findViewById(R.id.action_album_title);
        action_album_name = (TextView) view.findViewById(R.id.action_album_name);
        album_summer = (TextView) view.findViewById(R.id.album_summer);
        picture_list = (GridView) view.findViewById(R.id.action_album_gridView);
        setCommen(view);
    }
}
