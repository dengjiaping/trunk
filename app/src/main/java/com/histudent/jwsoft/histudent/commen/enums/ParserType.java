package com.histudent.jwsoft.histudent.commen.enums;

/**
 * Created by ZhangYT on 2017/5/8.
 */

public enum ParserType {

    /**
     * 对象Id对应的类型（如果是在动态上获取，则为1，否则为主题对象的类型）
     * (0 : 其他 , 1 : 动态 , 2 : 日志 , 3 : 随记 , 4 : 相册 , 5 : 照片 , 6 : 社群讨论 , 7 : 新加入成员 ,
     * 8 : 上传社群文件 , 9 : Hi吧帖子 , 10 : Hi吧回复 , 11 : 分享视频链接 , 12 : 读后感投票 ,
     * 1000 : 总计 , -3 : 社群 , -2 : 班级 , -1 : 个人 )
     */

    OTHER, DYNAMIC, LOG, ESSAY, IMAGE, ALBUM, GROUP_DISS, NEWMEMBERS, UPLOADFILE, HIBA_TOPIC, HIBA_REPLAY, SHARE_VIDEO, GROUP, CLASS, PERSON

}
