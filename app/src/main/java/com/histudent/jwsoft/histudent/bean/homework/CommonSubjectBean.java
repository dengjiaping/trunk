package com.histudent.jwsoft.histudent.bean.homework;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 科目实体
 */

public class CommonSubjectBean implements Parcelable {
    private String mSubjectName;
    private boolean isCheck;
    private String mSubjectId;
    private boolean isShowDeleteIcon;


    public CommonSubjectBean() {
    }


    protected CommonSubjectBean(Parcel in) {
        mSubjectName = in.readString();
        isCheck = in.readByte() != 0;
        mSubjectId = in.readString();
        isShowDeleteIcon = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSubjectName);
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(mSubjectId);
        dest.writeByte((byte) (isShowDeleteIcon ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonSubjectBean> CREATOR = new Creator<CommonSubjectBean>() {
        @Override
        public CommonSubjectBean createFromParcel(Parcel in) {
            return new CommonSubjectBean(in);
        }

        @Override
        public CommonSubjectBean[] newArray(int size) {
            return new CommonSubjectBean[size];
        }
    };

    public String getSubjectName() {
        return mSubjectName;
    }

    public CommonSubjectBean setSubjectName(String subjectName) {
        mSubjectName = subjectName;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public CommonSubjectBean setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public String getSubjectId() {
        return mSubjectId;
    }

    public CommonSubjectBean setSubjectId(String subjectId) {
        mSubjectId = subjectId;
        return this;
    }

    public boolean isShowDeleteIcon() {
        return isShowDeleteIcon;
    }

    public CommonSubjectBean setShowDeleteIcon(boolean showDeleteIcon) {
        isShowDeleteIcon = showDeleteIcon;
        return this;
    }

}
