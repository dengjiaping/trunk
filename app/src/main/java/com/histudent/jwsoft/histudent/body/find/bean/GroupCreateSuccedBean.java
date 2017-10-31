package com.histudent.jwsoft.histudent.body.find.bean;

import java.io.Serializable;

/**
 * Created by liuguiyu-pc on 2017/5/19.
 */

public class GroupCreateSuccedBean implements Serializable {


    /**
     * status : 1
     * groupName : 菠萝
     * groupLogo : http://img.hitongx.com/UploadFiles/groupLogo/20170526/4fbb25d4-7118-462e-b282-f3df68d0cb4c.jpg@116w_116h_1e_1c_2o_58-1ci.jpg
     * groupMasterName : 刘桂禹
     * groupNumber : 51966921
     * groupId : aa9a48fb-22d5-4e74-b338-1dd36e50a9b5
     */

    private int status;
    private String groupName;
    private String groupLogo;
    private String groupMasterName;
    private String groupNumber;
    private String groupId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        this.groupLogo = groupLogo;
    }

    public String getGroupMasterName() {
        return groupMasterName;
    }

    public void setGroupMasterName(String groupMasterName) {
        this.groupMasterName = groupMasterName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
