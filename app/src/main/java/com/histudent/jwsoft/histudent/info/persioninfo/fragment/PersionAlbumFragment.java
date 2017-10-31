package com.histudent.jwsoft.histudent.info.persioninfo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.BaseFragment;

/**
 * Created by liuguiyu-pc on 2016/12/21.
 */

public class PersionAlbumFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_persion_album, container, false);
        return view;
    }
}
