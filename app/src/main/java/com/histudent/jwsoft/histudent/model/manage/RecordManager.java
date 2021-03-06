package com.histudent.jwsoft.histudent.model.manage;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.model.constant.Const;
import com.histudent.jwsoft.histudent.model.entity.RecordInfoEvent;
import com.histudent.jwsoft.histudent.model.entity.RecordStatusEvent;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by huyg on 2017/10/23.
 * 语音录制管理类
 */

public class RecordManager {

    private AudioRecorder mRecorder;
    private static final int MAX_TIME = 60 * 5;
    private File mFile;
    private RecordType mRecordType;

    public RecordManager() {
        mRecorder = new AudioRecorder(HTApplication.getInstance(), RecordType.AAC, MAX_TIME, callback);
    }


    /**
     * 录制音频监听
     */
    IAudioRecordCallback callback = new IAudioRecordCallback() {
        @Override
        public void onRecordReady() {
            EventBus.getDefault().post(new RecordStatusEvent(Const.READY));
        }

        @Override
        public void onRecordStart(File file, RecordType recordType) {
            RecordManager.this.mFile = file;
            RecordManager.this.mRecordType = recordType;
            EventBus.getDefault().post(new RecordStatusEvent(Const.START));
        }

        @Override
        public void onRecordSuccess(File file, long l, RecordType recordType) {
            EventBus.getDefault().post(new RecordStatusEvent(Const.SUCCESS));
            EventBus.getDefault().post(new RecordInfoEvent(file,l,recordType));
        }

        @Override
        public void onRecordFail() {
            HiStudentLog.e("录音出错");
            EventBus.getDefault().post(new RecordStatusEvent(Const.FAIL));
        }

        @Override
        public void onRecordCancel() {
            HiStudentLog.e("取消录音");
            EventBus.getDefault().post(new RecordStatusEvent(Const.CANCEL));
        }

        @Override
        public void onRecordReachedMaxTime(int i) {
            HiStudentLog.e("到达指定的最长录音时间");
            EventBus.getDefault().post(new RecordStatusEvent(Const.MAX));
            EventBus.getDefault().post(new RecordInfoEvent(RecordManager.this.mFile,i,RecordManager.this.mRecordType));
        }
    };


    /**
     * 开始录音
     */
    public void startRecord() {
        if (mRecorder != null && !mRecorder.isRecording()) {
            mRecorder.startRecord();
        }
    }

    public void stopRecord() {
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.completeRecord(false);
        }
    }

    public boolean isRecording() {
        return mRecorder != null && mRecorder.isRecording();
    }

}
