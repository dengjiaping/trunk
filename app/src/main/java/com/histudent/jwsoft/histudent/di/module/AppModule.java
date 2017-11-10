package com.histudent.jwsoft.histudent.di.module;


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

    @Provides
    @Singleton
    ApiFactory provideRetrofitHelper() {
        return new ApiFactory();
    }

    @Provides
    @Singleton
    AudioManager provideAudioManager() {
        return new AudioManager();
    }

    @Provides
    @Singleton
    RecordManager provideRecordManager() {
        return new RecordManager();
    }

}
