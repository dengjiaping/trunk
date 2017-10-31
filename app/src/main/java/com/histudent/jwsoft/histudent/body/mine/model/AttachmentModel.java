package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/8/3.
 * 日志的list Model
 */
public class AttachmentModel {

    private String id;//主键
    private String associateId;//关联ID
    private String ownerId;//拥有者ID
    private String userId;//用户ID
    private String userDisplayName;//用户姓名
    private String fileName;//存储的文件名称
    private String friendlyFileName;//文件显示名称
    private int mediaType;//媒体类型=['1','2','3','4','5','6','7','8','9','99'] integer Enum:1,2,3,4,5,6,7,8,9,99
    private String contentType;//附件MIME类型
    private int fileLength;//文件大小
    private int height;//图片高度
    private int width;//图片宽度
    private String password;//下载密码
    private String uploadIP;//上传IP
    private String createDate;//上传时间
    private int downloadNum;//下载次数
    private boolean isDeleted;//是否已删除
    private String friendlyFileLength;//友好的附件大小信息


}
