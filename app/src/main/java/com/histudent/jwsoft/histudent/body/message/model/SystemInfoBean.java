package com.histudent.jwsoft.histudent.body.message.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/9/11.
 * 系统消息bean
 */
public class SystemInfoBean {


    /**
     * officialId : 00000000-0000-0000-0000-000000000000
     * officialName : 系统消息
     * logo : http://img.hitongx.com/Logo/SystemMsgLogo.png@_310w_240h_1e_1c.png
     * banner : https://upload.bugtags.com/avatar/1608/06/1538346319872391_1470449701.jpg@_310w_240h_1e_1c.jpg
     * intro : 嗨同学 — 是以“德育教育” 为出发点，致力于帮助学生纪录成长、自我展示和健康社交的平台。它既能帮助教师把日常的家校互联和班级展示工作变得更加丰富和便捷；还能协助家长构建孩子健康的网络社交环境和记录孩子的成长过程。
     * isSystem : false
     * fowllowsCount : 10001
     * itemCount : 1
     * sort : 0
     * isSubscribe : true
     */

    private InfoBean info;
    /**
     * info : {"officialId":"00000000-0000-0000-0000-000000000000","officialName":"系统消息","logo":"http://img.hitongx.com/Logo/SystemMsgLogo.png@_310w_240h_1e_1c.png","banner":"https://upload.bugtags.com/avatar/1608/06/1538346319872391_1470449701.jpg@_310w_240h_1e_1c.jpg","intro":"嗨同学 \u2014 是以\u201c德育教育\u201d 为出发点，致力于帮助学生纪录成长、自我展示和健康社交的平台。它既能帮助教师把日常的家校互联和班级展示工作变得更加丰富和便捷；还能协助家长构建孩子健康的网络社交环境和记录孩子的成长过程。","isSystem":false,"fowllowsCount":10001,"itemCount":1,"sort":0,"isSubscribe":true}
     * itemCount : 1
     * pageCount : 1
     * items : [{"logo":"https://upload.bugtags.com/avatar/1608/06/1538346319872391_1470449701.jpg@100w_100h_100Q.png","title":"系统消息标题","content":"系统消息内容","url":"http://www.hitongx.com","createTime":"2016-09-14 21:26:51"}]
     */

    private int itemCount;
    private int pageCount;
    /**
     * logo : https://upload.bugtags.com/avatar/1608/06/1538346319872391_1470449701.jpg@100w_100h_100Q.png
     * title : 系统消息标题
     * content : 系统消息内容
     * url : http://www.hitongx.com
     * createTime : 2016-09-14 21:26:51
     */

    private List<ItemsBean> items;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class InfoBean {
        private String officialId;
        private String officialName;
        private String logo;
        private String banner;
        private String intro;
        private boolean isSystem;
        private int fowllowsCount;
        private int itemCount;
        private int sort;
        private boolean isSubscribe;

        public String getOfficialId() {
            return officialId;
        }

        public void setOfficialId(String officialId) {
            this.officialId = officialId;
        }

        public String getOfficialName() {
            return officialName;
        }

        public void setOfficialName(String officialName) {
            this.officialName = officialName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public boolean isIsSystem() {
            return isSystem;
        }

        public void setIsSystem(boolean isSystem) {
            this.isSystem = isSystem;
        }

        public int getFowllowsCount() {
            return fowllowsCount;
        }

        public void setFowllowsCount(int fowllowsCount) {
            this.fowllowsCount = fowllowsCount;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public boolean isIsSubscribe() {
            return isSubscribe;
        }

        public void setIsSubscribe(boolean isSubscribe) {
            this.isSubscribe = isSubscribe;
        }
    }

//    content: "你创建的社群活动"测试活动"将在2017-08-10 18:04进行,请合理安排时间"
//    createTime: "2017-08-10 17:34:09"
//    customMsgType: 3
//    logo: "http://img.hitongx.com/HuodongCover/Class/20170810/d6af9596-8b98-45e4-9166-8a9eb9b33d09.jpg@108w_1e_1l_2o.jpg.jpg"
//    neteaseImMsgType: 7
//    openMode: 2
//    openParam: "27254711-dba5-43c9-8b3e-0cd910c5cb78"
//    title: "社群活动开始提醒"
//    url: "http://192.168.0.252:8020/app/?token={token}#/communityeventdetails?huodongId=27254711-dba5-43c9-8b3e-0cd910c5cb78"
    public static class ItemsBean {
        private String logo;
        private String title;
        private String content;
        private String url;
        private String createTime;
        private int customMsgType;
        private int neteaseImMsgType;
        private int openMode;
        private String openParam;

    public int getCustomMsgType() {
        return customMsgType;
    }

    public void setCustomMsgType(int customMsgType) {
        this.customMsgType = customMsgType;
    }

    public int getNeteaseImMsgType() {
        return neteaseImMsgType;
    }

    public void setNeteaseImMsgType(int neteaseImMsgType) {
        this.neteaseImMsgType = neteaseImMsgType;
    }

    public int getOpenMode() {
        return openMode;
    }

    public void setOpenMode(int openMode) {
        this.openMode = openMode;
    }

    public String getOpenParam() {
        return openParam;
    }

    public void setOpenParam(String openParam) {
        this.openParam = openParam;
    }

    public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
