package com.histudent.jwsoft.histudent.model.bean.homework;

import java.io.Serializable;

/**
 * Created by huyg on 2017/11/6.
 * /**
 * "aliVideoId": "a0cf5537116d4b798f2fbac9150f0392",
 "aliVideoCover": "http://vod.hitongx.com/snapshot/a0cf5537116d4b798f2fbac9150f039200001.jpg",
 "aliVideoPlayAuth": "eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklwWTdrSXQvM2k1UjIrcFNPTkVQZXFWQUJYOTFObXZITWlUejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRk1RVkZSYVpNNUVndjg4S29Wbi9KcExGc3QySjZyOEpqc1Ura3NSL21sMnBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZOLzducFNVUmJ2M2I0eGxMZXVrQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWRQTDVwZ2lhTTFyOUVQYmRnZmhtaTRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDR4ckRoRzVnS3NNUWFnQUVQWEJraXd0QWhBQkYyN2RQa2lFMFBSWDFvK3NPOU9HQ0trTFkwK2FzdThxRlArYXIzUWtMRVc3enV5dnBZWEFxOHo0YVZSM25XRnBPSzdISDNraGRSZlZDcGprSEliUmlvWnR3T0xlTzR1SDVzM0dWVHN5ZmxHcEVLWTRvVG11bHk4UzliVGh6ZlptWWpUM0Z5UVlMTUVUQnhnSVV0SWh5MHAxTnZBK2w5eEE9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCJ4bW04THNkczFLY0FzZW1wcmh2RitaTTVZL0dLU0sxYnRteTZKUzlOMzRJPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxNy0xMS0wNlQwODoyOToyMlpcIixcIk1lZGlhSWRcIjpcImEwY2Y1NTM3MTE2ZDRiNzk4ZjJmYmFjOTE1MGYwMzkyXCIsXCJTaWduYXR1cmVcIjpcIlF1Y2xuWjlxaXh2OXlTQ2xTL0pHTnhXRlF2az1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6ImEwY2Y1NTM3MTE2ZDRiNzk4ZjJmYmFjOTE1MGYwMzkyIiwiVGl0bGUiOiI5Mjk3NmQxYmVkMjQ0MGJkYTkwY2E2MTE4ZTBmMDdjMC5tcDQiLCJDb3ZlclVSTCI6Imh0dHA6Ly92b2QuaGl0b25neC5jb20vc25hcHNob3QvYTBjZjU1MzcxMTZkNGI3OThmMmZiYWM5MTUwZjAzOTIwMDAwMS5qcGciLCJEdXJhdGlvbiI6My40NTZ9LCJBY2Nlc3NLZXlJZCI6IlNUUy5ETVFpZUNmS1dNVmU2ZW9NUFRTUmF1NGdrIiwiUGxheURvbWFpbiI6InZvZC5oaXRvbmd4LmNvbSIsIkFjY2Vzc0tleVNlY3JldCI6IjJxcVUyR1NpQVNWRXlVNnEyUG9pYXBqRnFOYnNBOUNCYndjUGJMY2FZZ0VnIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjoxNzM4ODI1NjI4OTM1ODI2fQ==",
 "aliVideoPlayDuration": 3
 */


public class VideoDetailBean implements Serializable{
    private String aliVideoId;
    private String aliVideoCover;
    private String aliVideoPlayAuth;
    private long  aliVideoPlayDuration;

    public String getAliVideoId() {
        return aliVideoId;
    }

    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
    }

    public String getAliVideoCover() {
        return aliVideoCover;
    }

    public void setAliVideoCover(String aliVideoCover) {
        this.aliVideoCover = aliVideoCover;
    }

    public String getAliVideoPlayAuth() {
        return aliVideoPlayAuth;
    }

    public void setAliVideoPlayAuth(String aliVideoPlayAuth) {
        this.aliVideoPlayAuth = aliVideoPlayAuth;
    }

    public long getAliVideoPlayDuration() {
        return aliVideoPlayDuration;
    }

    public void setAliVideoPlayDuration(long aliVideoPlayDuration) {
        this.aliVideoPlayDuration = aliVideoPlayDuration;
    }
}
