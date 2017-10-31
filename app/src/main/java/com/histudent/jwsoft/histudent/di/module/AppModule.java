package com.histudent.jwsoft.histudent.di.module;


import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.component.AudioManager;
import com.histudent.jwsoft.histudent.component.RecordManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {

    private final HiStudentApplication application;

    public AppModule(HiStudentApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    HiStudentApplication provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiFactory provideRetrofitHelper() {
        return new ApiFactory();
    }

    @Provides
    @Singleton
    AudioManager provideAudioManager(){
        return new AudioManager(application);
    }

    @Provides
    @Singleton
    RecordManager provideRecordManager(){
        return new RecordManager();
    }

}
