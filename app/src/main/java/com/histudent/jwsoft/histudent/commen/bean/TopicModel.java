package com.histudent.jwsoft.histudent.commen.bean;

import java.io.Serializable;

/**
 * Created by ZhangYT on 2017/6/27.
 */
// "topicId": "787a50a2-2e43-413d-82ee-c32d0c084ae7",
//         "topicName": "30天"
public class TopicModel implements Serializable{
    private String topicId;
    private String topicName;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
