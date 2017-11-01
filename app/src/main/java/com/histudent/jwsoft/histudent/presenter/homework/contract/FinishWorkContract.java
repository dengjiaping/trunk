package com.histudent.jwsoft.histudent.presenter.homework.contract;

import android.content.Context;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.UploadAuthBean;

import java.io.File;
import java.util.List;

/**
 * Created by huyg on 2017/10/30.
 */

public interface FinishWorkContract {

    interface View extends BaseView {
        void showVideoList(String videoId);

        void uploadFile(UploadAuthBean uploadAuthBean);

        void showDialog();

        void closeDialog();

        void createWorkSucceed();

        void showImgList(List<File> files);
    }

    interface Presenter extends BasePresenter<View> {
        void startRecord();

        void stopRecord();

        boolean getRecordState();

        boolean getAudioState();

        void playAudio(String source);

        void pauseAudio();

        void stopAudio();

        /**
         * 获取上传视频凭证
         *
         * @param fileSize
         */
        void getVodUploadAuth(long fileSize);

        /**
         * 上传视频
         *
         * @param uploadAuthBean
         */
        void uploadVideo(Context context, String fileName, UploadAuthBean uploadAuthBean);

        /**
         * 压缩文件图片
         */

        void compressImg(Context context, List<String> imgUrls);

        /**
         * 创建作业任务
         *
         * @param videoIds    视频id
         * @param audioFiles  音频
         */
        void completeHomeWork(String homeworkId,
                              String content,
                              String videoIds,
                              File audioFiles,
                              List<File> imgFiles
                            );
    }
}
