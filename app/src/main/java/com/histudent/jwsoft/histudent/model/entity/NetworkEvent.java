package com.histudent.jwsoft.histudent.model.entity;

/**
 * Created by lichaojie on 2017/7/18.
 */

public class NetworkEvent {
    private boolean isNetworkUsed=true;
    public void setNetworkUsed(boolean networkUsed) {
        isNetworkUsed = networkUsed;
    }

    public boolean isNetwordkUsed() {
        return isNetworkUsed;
    }

}
