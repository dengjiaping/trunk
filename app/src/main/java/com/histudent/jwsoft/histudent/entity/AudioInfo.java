package com.histudent.jwsoft.histudent.entity;

import com.netease.nimlib.sdk.media.record.RecordType;

import java.io.File;

/**
 * Created by huyg on 2017/10/26.
 */

public class AudioInfo {
    private File file;
    private long time;
    private RecordType mRecordType;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public RecordType getRecordType() {
        return mRecordType;
    }

    public void setRecordType(RecordType mRecordType) {
        this.mRecordType = mRecordType;
    }
}
