package com.histudent.jwsoft.histudent.model.di.componet;


import com.histudent.jwsoft.histudent.model.manage.AudioManager;
import com.histudent.jwsoft.histudent.model.manage.RecordManager;
import com.histudent.jwsoft.histudent.model.di.module.AppModule;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    ApiFactory ApiFactory();  //提供http的帮助类

    AudioManager getAudioManager();//语音播放类

    RecordManager getRecordManager();//语音录制类
}
