package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;

import java.io.File;
import java.util.List;

/**
 * Created by huyg on 2017/10/25.
 */

public interface WorkDetailTeacherContract {


    interface View extends BaseView {
        void showHomeworkDetail(HomeworkDetailBean homeworkDetail);

        void getHomeworkDetailFail();
        void showCompleteList(boolean isComplete,List<WorkCompleteBean.ItemsBean> itemsBeen);
        void downloadVoiceSuccess(File file);

        void controlDialogStatus(String message);

        void deleteHomeworkSuccess();
        void getCompleteListFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getHomeworkDetail(String homeworkId);
        void singleNotice(String homeworkId, String userId, int type);
        void getCompleteList(String homeworkId, boolean isComplete, int index, int size);
        boolean getAudioState();

        void playAudio(String source);

        void playAudio(String source, int position);

        void pauseAudio();

        void stopAudio();

        void downloadVoice(String voiceId);
    }

}
