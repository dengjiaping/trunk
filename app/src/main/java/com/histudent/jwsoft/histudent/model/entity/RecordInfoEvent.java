package com.histudent.jwsoft.histudent.model.entity;

import com.netease.nimlib.sdk.media.record.RecordType;

import java.io.File;

/**
 * Created by huyg on 2017/10/26.
 */

public class RecordInfoEvent {
    public File mFile;
    public long time;
    public RecordType mRecordType;

    public RecordInfoEvent(File mFile, long time, RecordType mRecordType) {
        this.mFile = mFile;
        this.time = time;
        this.mRecordType = mRecordType;
    }
}
