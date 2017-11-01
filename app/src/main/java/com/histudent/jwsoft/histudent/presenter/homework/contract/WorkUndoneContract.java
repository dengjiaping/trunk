package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;

import java.io.File;

/**
 * Created by huyg on 2017/11/1.
 */

public interface WorkUndoneContract {

    interface View extends BaseView {
        void downloadVoiceSuccess(File file);
        void showHomeworkDetail(HomeworkDetailBean homeworkDetail);
        void getHomeworkDetailFail();
        void finishHomework();
    }

    interface Presenter extends BasePresenter<View>{
        boolean getAudioState();
        void playAudio(String source);
        void pauseAudio();
        void stopAudio();
        void downloadVoice(String voiceId);
        void getHomeworkDetail(String homeworkId);
        void completeHomework(String homeworkId);
    }

}
