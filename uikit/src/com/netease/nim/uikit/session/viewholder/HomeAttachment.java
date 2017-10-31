//package com.netease.nim.uikit.session.viewholder;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * Created by zhoujianghua on 2015/4/9.
// */
//public class HomeAttachment extends CustomAttachment {
//
//
//    JSONObject data;
//
//    public HomeAttachment() {
//        super(0);
//    }
//
//    @Override
//    protected void parseData(JSONObject data) {
//        this.data = data;
//    }
//
//    @Override
//    protected JSONObject packData() {
//        return null;
//    }
//
//    public String getContent() {
//        return data != null ? data.getString("content") : "";
//    }
//
//    public String getTitle() {
//        return data != null ? data.getString("title") : "";
//    }
//
//    public String getImag() {
//        return data != null ? data.getString("img") : "";
//    }
//
//    public String getUrl() {
//        return data != null ? data.getString("url") : "";
//    }
//
//}
