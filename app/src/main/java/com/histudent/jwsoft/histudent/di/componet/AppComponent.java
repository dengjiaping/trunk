package com.histudent.jwsoft.histudent.di.componet;




import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.component.AudioManager;
import com.histudent.jwsoft.histudent.component.RecordManager;
import com.histudent.jwsoft.histudent.di.module.AppModule;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    HiStudentApplication getContext();  // 提供App的Context

    ApiFactory ApiFactory();  //提供http的帮助类

    AudioManager getAudioManager();//语音播放类

    RecordManager getRecordManager();//语音录制类



}
