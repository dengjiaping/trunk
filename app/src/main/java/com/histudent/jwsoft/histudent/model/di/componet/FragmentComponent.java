package com.histudent.jwsoft.histudent.model.di.componet;

import android.app.Activity;

import com.histudent.jwsoft.histudent.commen.fragment.AlbumFragment;
import com.histudent.jwsoft.histudent.model.di.FragmentScope;
import com.histudent.jwsoft.histudent.model.di.module.FragmentModule;
import com.histudent.jwsoft.histudent.view.fragment.image.UploadImgFragment;
import com.histudent.jwsoft.histudent.view.fragment.main.HomeFragment;
import com.histudent.jwsoft.histudent.view.fragment.work.WorkCompleteFragment;
import com.histudent.jwsoft.histudent.view.fragment.work.WorkNoCompleteFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(AlbumFragment albumFragment);

    void inject(UploadImgFragment uploadImgFragment);

    void inject(WorkCompleteFragment workCompleteFragment);

    void inject(WorkNoCompleteFragment workNoCompleteFragment);

    void inject(HomeFragment homeFragment);
}
