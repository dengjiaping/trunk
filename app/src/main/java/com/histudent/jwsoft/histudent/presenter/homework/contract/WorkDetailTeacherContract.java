package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;

import java.io.File;

/**
 * Created by huyg on 2017/10/25.
 */

public interface WorkDetailTeacherContract {


    interface View extends BaseView {
        void showHomeworkDetail(HomeworkDetailBean homeworkDetail);
        void getHomeworkDetailFail();
        void downloadVoiceSuccess(File file);
    }

    interface Presenter extends BasePresenter<View> {
        void getHomeworkDetail(String homeworkId);
        boolean getAudioState();
        void playAudio(String source);
        void pauseAudio();
        void stopAudio();
        void downloadVoice(String voiceId);
    }

}
