package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.model.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkDetailBean;

import java.io.File;

/**
 * Created by huyg on 2017/10/25.
 */

public interface WorkDetailStudentContract {

    interface View extends BaseView {
        void showCompleteDetail(CompleteDetailBean completeDetailBean);

        void getCompleteDetailFail();

        void downloadVoiceSuccess(File file, int type);

        void showHomeworkDetail(HomeworkDetailBean homeworkDetail);

        void getHomeworkDetailFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getHomeworkDetail(String homeworkId);

        void getCompleteDetail(String homeworkId, String userId);

        boolean getAudioState();

        void playAudio(String source);

       void  playAudio(String source, int position);

        void pauseAudio();

        void stopAudio();

        void downloadVoice(String voiceId, int type);
    }


}
