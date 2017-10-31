package com.histudent.jwsoft.histudent.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lichaojie on 2017/9/18.
 * desc:
 * 学生阅读信息
 */

public class UserReadBookInformation implements Parcelable {

    private String mBookBn = null;
    private String mBookName = null;
    private int mStartPageIndex = 0;
    private int mEndPageIndex = 0;
    private String mUserReadFeel = null;
    private String mBookTaskId = null;
    private double mLongitude = 0.0;
    private double mLatitude = 0.0;
    private String mLocation = null;
    private int mReadBookTotalTime = 0;

    public UserReadBookInformation() {
    }

    protected UserReadBookInformation(Parcel in) {
        mBookBn = in.readString();
        mBookName = in.readString();
        mStartPageIndex = in.readInt();
        mEndPageIndex = in.readInt();
        mUserReadFeel = in.readString();
        mBookTaskId = in.readString();
        mLongitude = in.readDouble();
        mLatitude = in.readDouble();
        mLocation = in.readString();
        mReadBookTotalTime = in.readInt();
    }

    public static final Creator<UserReadBookInformation> CREATOR = new Creator<UserReadBookInformation>() {
        @Override
        public UserReadBookInformation createFromParcel(Parcel in) {
            return new UserReadBookInformation(in);
        }

        @Override
        public UserReadBookInformation[] newArray(int size) {
            return new UserReadBookInformation[size];
        }
    };

    public int getReadBookTotalTime() {
        return mReadBookTotalTime;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public String getLocation() {
        return mLocation;
    }


    public static final UserReadBookInformation create() {
        return new UserReadBookInformation();
    }

    public final String getBookBn() {
        return mBookBn;
    }

    public final String getBookName() {
        return mBookName;
    }

    public final int getStartPageIndex() {
        return mStartPageIndex;
    }

    public final int getEndPageIndex() {
        return mEndPageIndex;
    }

    public final String getUserReadFeel() {
        return mUserReadFeel;
    }

    public final String getBookTaskId() {
        return mBookTaskId;
    }

    public final UserReadBookInformation setBookBn(String bookBn) {
        mBookBn = bookBn == null ? "" : bookBn;
        return this;
    }

    public final UserReadBookInformation setBookName(String bookName) {
        mBookName = bookName == null ? "" : bookName;
        return this;
    }

    public final UserReadBookInformation setStartPageIndex(int startPageIndex) {
        mStartPageIndex = startPageIndex;
        return this;
    }

    public final UserReadBookInformation setEndPageIndex(int endPageIndex) {
        mEndPageIndex = endPageIndex;
        return this;
    }

    public final UserReadBookInformation setUserReadFeel(String userReadFeel) {
        mUserReadFeel = userReadFeel == null ? "" : userReadFeel;
        return this;
    }

    public final UserReadBookInformation setBookTaskId(String bookTaskId) {
        mBookTaskId = bookTaskId == null ? "" : bookTaskId;
        return this;
    }

    public final UserReadBookInformation setLongitude(double longitude) {
        mLongitude = longitude;
        return this;
    }

    public final UserReadBookInformation setLatitude(double latitude) {
        mLatitude = latitude;
        return this;
    }

    public final UserReadBookInformation setLocation(String location) {
        mLocation = location == null ? "" : location;
        return this;
    }

    public final UserReadBookInformation setReadBookTotalTime(int readBookTotalTime) {
        mReadBookTotalTime = readBookTotalTime;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookBn);
        dest.writeString(mBookName);
        dest.writeInt(mStartPageIndex);
        dest.writeInt(mEndPageIndex);
        dest.writeString(mUserReadFeel);
        dest.writeString(mBookTaskId);
        dest.writeDouble(mLongitude);
        dest.writeDouble(mLatitude);
        dest.writeString(mLocation);
        dest.writeInt(mReadBookTotalTime);
    }
}
