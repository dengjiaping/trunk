package com.histudent.jwsoft.histudent.model.bean.homework;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 通用成员显示条目
 */

public class CommonMemberBean implements Parcelable {
    private String mName;
    private String mHeadIconUrl;
    private boolean isCheck;
    private String mUserId;
    private String mTeamMemberId;
    private String mTeamName;


    public CommonMemberBean() {
    }


    protected CommonMemberBean(Parcel in) {
        mName = in.readString();
        mHeadIconUrl = in.readString();
        isCheck = in.readByte() != 0;
        mUserId = in.readString();
        mTeamMemberId = in.readString();
        mTeamName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mHeadIconUrl);
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(mUserId);
        dest.writeString(mTeamMemberId);
        dest.writeString(mTeamName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonMemberBean> CREATOR = new Creator<CommonMemberBean>() {
        @Override
        public CommonMemberBean createFromParcel(Parcel in) {
            return new CommonMemberBean(in);
        }

        @Override
        public CommonMemberBean[] newArray(int size) {
            return new CommonMemberBean[size];
        }
    };

    public String getTeamMemberId() {
        return mTeamMemberId;
    }

    public CommonMemberBean setTeamMemberId(String teamMemberId) {
        mTeamMemberId = teamMemberId;
        return this;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public CommonMemberBean setTeamName(String teamName) {
        mTeamName = teamName;
        return this;
    }

    public String getName() {
        return mName;
    }

    public CommonMemberBean setName(String name) {
        mName = name;
        return this;
    }

    public String getHeadIconUrl() {
        return mHeadIconUrl;
    }

    public CommonMemberBean setHeadIconUrl(String headIconUrl) {
        mHeadIconUrl = headIconUrl;
        return this;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public CommonMemberBean setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public String getUserId() {
        return mUserId;
    }

    public CommonMemberBean setUserId(String userId) {
        mUserId = userId;
        return this;
    }

}
