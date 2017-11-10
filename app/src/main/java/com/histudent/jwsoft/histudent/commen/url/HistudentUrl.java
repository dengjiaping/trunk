package com.histudent.jwsoft.histudent.commen.url;

import android.text.TextUtils;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;

/**
 * Created by liuguiyu-pc on 2016/7/26.
 */
public class HistudentUrl {

    public static final String getBaseUrl() {
        String baseUrl;
        if (!HTApplication.isOnLine) {
            baseUrl = TextUtils.isEmpty(HiCache.getInstance().getUrl()) ? "http://192.168.0.252:8290" : HiCache.getInstance().getUrl();
        } else {
            baseUrl = "https://api2.hitongx.com/v2";
//            baseUrl = "http://101.37.162.49:8080/v2";
        }
        return baseUrl;
    }

    public static final String BASE_URL = getBaseUrl();
    public static final String BASE_H5_URL = getH5BaseUrl();

    public static final String getH5BaseUrl() {
        String baseUrl;
        if (!HTApplication.isOnLine) {
            baseUrl = "http://192.168.0.252:8020";
        } else {
            baseUrl = "https://h5.hitongx.com";
        }
        return baseUrl;
    }

    public static final String getDisclaimerH5BaseUrl() {
        String baseUrl;
        if (!HTApplication.isOnLine) {
            baseUrl = "http://192.168.0.6:8080";
        } else {
            baseUrl = "https://h5.hitongx.com/app";
        }
        return baseUrl;
    }


    /**
     * 首页
     */
    public static final String agetRecommendClasses_url = getBaseUrl() + "/home/getRecommendClasses";// 获取推荐班级列表
    public static final String getRecommendUsers_url = getBaseUrl() + "/home/getRecommendUsers";// 获取推荐用户列表
    public static final String getRecommendTags_url = getBaseUrl() + "/home/getRecommendTags";// 获取热门列表
    public static final String homePageInfo_url = getBaseUrl() + "/home/homePageInfo";// 首页信息接口(轮播图、推荐班级、推荐用户、热门话题、推荐动态)
    public static final String getRecommendActivities_url = getBaseUrl() + "/home/getRecommendActivities";// 获取推荐动态
    public static final String topicMicroBlogs_url = getBaseUrl() + "/topic/topicMicroBlogs";// 获取话题对应的随记列表
    public static final String topicActivities_url = getBaseUrl() + "/topic/topicActivities";// 获取话题对应的动态列表
    public static final String topicTagInfo_url = getBaseUrl() + "/topic/topicTagInfo";// 话题详情介绍
    public static final String getPraiseUsers_url = getBaseUrl() + "/sns/action/getPraiseUsers";// 获取某个对象（动态、日志、相册、随记）“已点赞的人”详情
    public static final String notices_list_url = getBaseUrl() + "/notices/list";// 通知列表
    public static final String createNotice_url = getBaseUrl() + "/notices/create";// 发布通知
    public static final String noticeDetail_url = getBaseUrl() + "/notices/detail";// 通知详情
    public static final String unreadCount_url = getBaseUrl() + "/topic/unreadCount";// 未读话题的动态数
    public static final String singNotice_url = getBaseUrl() + "/notices/singleNotice";// 未读话题的动态数
    /**
     * 用户协议
     */
    public static final String agreement_url = "http://www.hitongx.com/MobileApp/Account/Agreement";

    /**
     * 系统消息
     */
    public static final String systemInfo_url = getBaseUrl() + "/official/officialSystemMsg";// 系统消息

    public static final String officialInfo_url = getBaseUrl() + "/official/officialHuoDong"; //官方活动

    public static final String signCheck_url = getBaseUrl() + "/appinfo/SignCheck";// 签名验证接口

    public static final String downloadfile_url = getBaseUrl() + "/file/downloadfile";// 下载文件


    /**
     * 视频相关
     */
    public static final String getVodUploadAuth_url = getBaseUrl() + "/user/cloudAcl/getVodUploadAuth";// 获取上传视频相关的凭证

    /**
     * 分享
     */
    public static final String share_url = getBaseUrl() + "/share/acticityShare";// 获取动态分享的内容
    public static final String share_url_ = getBaseUrl() + "/share/objectShareContents";// 主体对象的分享内容(非动态的分享)
    public static final String shareCallBack = getBaseUrl() + "/share/shareCallBack";// 分享成功后回调

    /**
     * 与我相关未读信息
     */
    public static final String aboutNum_url = getBaseUrl() + "/timeline/unreadActivityCount";// 获取时间点后动态的未读信息

    /**
     * 验证码
     */
    public static final String verfiyCode_url = getBaseUrl() + "/user/security/sendVerfiyCode";//验证码
    public static final String verfiyEmailCode_url = getBaseUrl() + "/user/security/sendVerfiyCodeByEmail";// 发送验证码通过邮箱

    public static final String recommondUserList_url = getBaseUrl() + "/sns/follow/recommondUserList";// 新手引导界面获取推荐关注的人
    public static final String follwUserBatch_url = getBaseUrl() + "/sns/follow/follwUserBatch";// 批量关注用户

    /**
     * 登录
     */
    public static final String login_url = getBaseUrl() + "/user/account/login";//账号登录
    public static final String loginByMobile_url = getBaseUrl() + "/user/account/loginByMobile";// 手机登入
    public static final String getOpenIdBindInfo_url = getBaseUrl() + "/user/account/getOpenIdBindInfo ";//判断openid是否已绑定帐号
    public static final String verfiyBindingMobile_url = getBaseUrl() + "/user/account/verfiyBindingMobile";// 判断绑定时手机帐号是否存在
    public static final String bindingAccount_url = getBaseUrl() + "/user/account/bindingAccount";// 绑定已有账号
    public static final String bindingThirdparty_url = getBaseUrl() + "/user/account/bindingThirdparty";// 绑定第三方账号
    public static final String relieveThirdparty_url = getBaseUrl() + "/user/account/relieveThirdparty";// 解除第三方账号


    public static final String getVodPlayAuth = getBaseUrl() + "/user/cloudAcl/getVodPlayAuth";// 获取视频播放凭证


    /**
     * 生成邀请地址
     */
    public static final String invitationShareUrl_url = getBaseUrl() + "/invitation/invitationShareUrl";// 生成邀请地址

    /**
     * 创建班级
     */
    public static final String classCreate_url = getBaseUrl() + "/class/classCreate";//创建班级

    /**
     * 注册
     */
    public static final String register_url = getBaseUrl() + "/user/account/register";//注册

    /**
     * 找回密码
     */
    public static final String isValidVerfiyCode_url = getBaseUrl() + "/user/security/isValidVerfiyCode";//验证码是否正确
    public static final String resetPassword_url = getBaseUrl() + "/user/security/resetPassword";//重置密码
    public static final String exchangeClassOwner = getBaseUrl() + "/class/transferByMobile";// 转让班级

    /**
     * 我的
     */
    public static final String getUserInfo = getBaseUrl() + "/user/info/detail";//获取用户详细信息
    public static final String deletHoner = getBaseUrl() + "/user/info/removeHoner";//删除用户获奖信息
    public static final String addHoner = getBaseUrl() + "/user/info/addHoner";// 添加获奖信息(需要图片)
    public static final String editUserInfo = getBaseUrl() + "/user/info/editProfile";//编辑用户资料信息
    public static final String myClassList = getBaseUrl() + "/class/classList";// 我的班级列表

    /**
     * 排行榜
     */
    public static final String userRank = getBaseUrl() + "/school/userRank";// 学校用户排行
    public static final String classRank = getBaseUrl() + "/school/classRank";// 学校班级排行
    public static final String myClassRank = getBaseUrl() + "/school/myClassRank";// 我的班级在学校的班级排行
    public static final String myUserRank = getBaseUrl() + "/school/myUserRank";// 我在学校用户排行

    /**
     * 加入班级
     */
    public static final String addInClass = getBaseUrl() + "/class/applyJoin";//申请加入班级（注：如果根据班主任手机号申请加入班级，会加入班主任最新创建的班级中）
    public static final String setClassAdmin = getBaseUrl() + "/class/setAdmin";// 设置班级管理员
    public static final String setMembers = getBaseUrl() + "/class/setMembers";// 设置班级成员(返回值：successNum：成功个数，faultNum：失败个数)
    public static final String reSetClassPwd = getBaseUrl() + "/class/reSetPwd";// 重置密码
    public static final String classDelete = getBaseUrl() + "/class/classDelete";// 解散班级
    public static final String removeUser = getBaseUrl() + "/class/removeUser";// 移除班级成员
    public static final String setClassApplyAudit = getBaseUrl() + "/class/setClassApplyAudit";// 设置班级是否可以申请

    /**
     * 获取随记信息
     */
    public static final String getEsseyInfo_url = getBaseUrl() + "/microBlog/list";// 获取用户随记列表

    /**
     * 个人主页
     */
    public static final String getBlogInfo_url = getBaseUrl() + "/blog/list";//获取相关日志信息
    public static final String getTimeThingsInfo_url = getBaseUrl() + "/timeline/personal";//个人主页时间轴（只显示我发送的随记 日志 相册等）
    public static final String getlbumInfo_url = getBaseUrl() + "/album/list";//获取相册列表信息
    public static final String getRecentlyPictureInfo_url = getBaseUrl() + "/photo/recentlyList";//获取用户最近上传的照片
    public static final String getPhotoInAlbum_url = getBaseUrl() + "/photo/list";// 获取相册下的照片

    /**
     * 社群相关
     */
    public static final String singleGroup = getBaseUrl() + "/group/singleGroup";// 获取单个社群Model
    public static final String activities_group = getBaseUrl() + "/group/activities";// 社群的动态
    public static final String applyJoin_group = getBaseUrl() + "/group/applyJoin";// 申请加入社群
    public static final String setMembers_group = getBaseUrl() + "/group/setMembers";// 设置社群成员
    public static final String groupTransfer_group = getBaseUrl() + "/group/groupTransfer";// 转让社群
    public static final String groupMembers_group = getBaseUrl() + "/group/groupMembers";// 社群成员信息
    public static final String groupDelete_group = getBaseUrl() + "/group/groupDelete";// 删除社群
    public static final String setAdmin_group = getBaseUrl() + "/group/setAdmin";// 设置社群管理员
    public static final String findGroupsList = getBaseUrl() + "/group/findGroupsList";// 发现社群
    public static final String noteHotList = getBaseUrl() + "/group/noteHotList";// 热门帖子动态列表
    public static final String groupUpdate = getBaseUrl() + "/group/groupUpdate";// 社群更新

    /**
     * 学校相关
     */
    public static final String home_school = getBaseUrl() + "/school/home";// 学校主页
    public static final String activities_school = getBaseUrl() + "/school/activities";// 学校的动态

    /**
     * 班级相关
     */
    public static final String getClassAction_url = getBaseUrl() + "/class/timeline";// 班级本身的动态
    public static final String getClassModel_url = getBaseUrl() + "/class/singleClass";// 获取单个班级Model
    public static final String getNewestNotice_url = getBaseUrl() + "/class/newestNotice";// 获取班级最新公告
    public static final String getClassNoticeList_url = getBaseUrl() + "/class/noticeList";// 获取班级公告
    public static final String getClassTeamber_url = getBaseUrl() + "/class/classMembers";// 班级成员
    public static final String deleteBlog_url = getBaseUrl() + "/blog/delete";// 删除日志
    public static final String deleteTimeline_url = getBaseUrl() + "/timeline/delete";// 动态删除
    public static final String searchSchoolListByName_url = getBaseUrl() + "/class/searchSchoolListByName";// 查询学校（根据地区名称。注：学校->学段->年级->班级为级联选择，每一个选项的值变化，下级的选项必须重置。）
    public static final String gradeList_url = getBaseUrl() + "/class/gradeList";// 获取年级列表（注：学校->学段->年级->班级为级联选择，每一个选项的值变化，下级的选项必须重置。）
    public static final String classList_url = getBaseUrl() + "/class/classList";// 班级下拉选项（选择对应的班级之后，必须调用IfClassExsit接口判断此班级是否存在，如果存在该班级，弹出相应提示“班级已存在，是否要加入该班级？，如果选择确定则要调用ClassTransferByCreateing接口，否则该班级不允许创建”） （注：学校->学段->年级->班级为级联选择，每一个选项的值变化，下级的选项必须重置。）
    public static final String classTransferByCreateing_url = getBaseUrl() + "/class/classTransferByCreateing";// 创建班级过程中加入/转让班级（在创建或者修改班级过程中，检测到班级已存在，并且用户同意加入到该班级，调用此接口。如果该班级原有班主任是默认班主任，直接转让，如果不是默认班级，直接加入该班级。）
    public static final String applyClass_url = getBaseUrl() + "/class/applyClass";// 申请加入班级
    public static final String classUpdate_url = getBaseUrl() + "/class/classUpdate";// 更新班级(注：传输数据方式和之前建立社群的时候一样)
    public static final String activeStars_url = getBaseUrl() + "/class/activeStars";//朋友圈活跃之星(前N条）
    public static final String applyClassListByMobile_url = getBaseUrl() + "/class/applyClassListByMobile";// 根据手机号获取用户班级
    /**
     * 好友相关
     */
    public static final String getFriendsList_url = getBaseUrl() + "/sns/follow/FollowedCategory";//获得关注好友的分类信息
    public static final String getFriendsInList_url = getBaseUrl() + "/sns/follow/followTypeList";//获得分类中具体成员信息
    public static final String getRecommondFriendsInList_url = getBaseUrl() + "/sns/follow/getRecommondFriends";// 推荐好友列表
    public static final String doAction_url = getBaseUrl() + "/sns/follow/doAction";//关注相关动作（关注/取消关注，特别关注/取消特别关注，加入黑名单/取消黑名单，屏蔽动态/取消屏蔽动态）
    public static final String creatGroup_url = getBaseUrl() + "/sns/follow/createCategory";//创建分组
    public static final String findUsers_url = getBaseUrl() + "/sns/follow/findUsers";// 查找用户
    public static final String editCategory = getBaseUrl() + "/sns/follow/editCategory";//编辑分组（操作为添加时，分组ID=>categoryId可为空，为编辑时，不可为空）

    public static final String userFollowGroups = getBaseUrl() + "/sns/follow/userFollowGroups";// 获取用户所在分组信息
    public static final String editUserFollowCatrgoryNew = getBaseUrl() + "/sns/follow/editUserFollowCatrgoryNew";// 移动分组（最新接口：2017-03-29）

    public static final String rankList_url = getBaseUrl() + "/user/info/rankList";// 获取所有等级
    public static final String pointRecords_url = getBaseUrl() + "/user/info/pointRecords";// 获取用户的积分日志

    /**
     * 更换封面
     */
    public static final String exchangeClassCover_url = getBaseUrl() + "/class/editClassCoverImg";// 更换班级封面
    public static final String exchangePersionCover_url = getBaseUrl() + "/user/info/changeCover";// 上传封面(需要图片,成功时,msg为封面全路径)
    public static final String setalbumcover_url = getBaseUrl() + "/photo/setalbumcover";// 设置相册封面

    /**
     * 动态
     */
    public static final String goodFriendAction_url = getBaseUrl() + "/timeline/friend";// 获取好友动态
    //    public static final String classAction_url = getBaseUrl() + "/timeline/class";// 获取班级动态
    public static final String aboutMine_url = getBaseUrl() + "/timeline/relatedToMe";// 获取与我相关的动态
    public static final String classAction_url = getBaseUrl() + "/class/timeline";// 班级本身的动态
    public static final String disableActivities_url = getBaseUrl() + "/class/disableActivities";// 班级圈屏蔽的动态列表

    /**
     * SNS社交相关
     */
    public static final String praise_url = getBaseUrl() + "/sns/action/praise";// 点赞/取消点赞
    public static final String getComment_url = getBaseUrl() + "/sns/action/getComments";// 获取动态评论
    public static final String publicComment_url = getBaseUrl() + "/sns/action/comment";// 发表评论
    public static final String deletComment_url = getBaseUrl() + "/sns/action/deleteComment";// 删除评论
    public static final String getPraiseDatil_url = getBaseUrl() + "/sns/action/getPraiseUsers";// 获取某个对象（动态、日志、相册、随记）“已点赞的人”详情
    public static final String editUsersCategory_url = getBaseUrl() + "/sns/follow/editUsersCategoryAndDelete";// 移动分组同时删除旧分组信息
    public static final String categoryDelete_url = getBaseUrl() + "/sns/follow/categoryDelete";// 删除接口
    public static final String activityInfo_url = getBaseUrl() + "/timeline/activityInfo";// 动态详情
    public static final String setActivityRecommend_url = getBaseUrl() + "/sns/action/setActivityRecommend";// 设置动态的推荐状态
    public static final String disableActivity_url = getBaseUrl() + "/sns/action/disableActivity";// 屏蔽动态

    /**
     * IM相关
     */
    public static final String registIMaccount = getBaseUrl() + "/user/info/bindImAccount";//向im服务器注册im帐号accid
    public static final String noticeList = getBaseUrl() + "/message/noticeList";// 获取通知列表
    public static final String unreadMessageCount = getBaseUrl() + "/message/unreadMessageCount";// 获取用户新的未读通知
    public static final String unreadEachCount = getBaseUrl() + "/message/unreadEachCount";//获取用户新的未读通知数(详细：1@我，2评论，3赞)


    public static final String recordMessage = getBaseUrl() + "/imChat/addMessage";
    /**
     * 头像
     */
    public static final String uploadAvatar = getBaseUrl() + "/user/info/uploadAvatar";//上传头像

    /**
     * 版本更新
     */
    public static final String updateApp = getBaseUrl() + "/oauth/updateApp";// APP更新
    public static final String deleteAlbum = getBaseUrl() + "/album/delete";// 删除相册
    public static final String deleteMicroBlog = getBaseUrl() + "/microBlog/delete";// 删除随记
    public static final String classHuoDongList = getBaseUrl() + "/class/huodong/list";//获取班级活动列表


    /**
     * 退出
     */
    public static final String logout_delet = getBaseUrl() + "/user/account/logout";// 退出登录
    public static final String photofilelist_url = getBaseUrl() + "/album/list";//获取相册集合
    public static final String createphotofilelist_url = getBaseUrl() + "/album/create";//创建相册
    public static final String createmicroblog_url = getBaseUrl() + "/microBlog/create";//发布随记
    public static final String postMicroBlog_url = getBaseUrl() + "/common/postMicroBlog";// 发布随记
    public static final String upload_url = getBaseUrl() + "/photo/uploadtest";//随记图片上传
    public static final String getAlbumInfo = getBaseUrl() + "/album/detail";// 相册详情
    public static final String createBlog = getBaseUrl() + "/blog/create";// 创建日志
    public static final String createCommonBlog = getBaseUrl() + "/common/postBlog";// 公用发布读后感接口(@同学，表情)
    public static final String createTrack = getBaseUrl() + "/readingtrack/uploadreadnotes";//发表轨迹
    public static final String createbookreview = getBaseUrl() + "/readingtrack/createbookreview";//发表读后感
    public static final String uploadAttach = getBaseUrl() + "/readingtrack/uploadAttach";// 单图片上传(附件表)


    /**
     * 账号安全
     */
    public static final String validatePwd_url = getBaseUrl() + "/user/security/validatePwd";// 验证当前密码是否正确
    public static final String exchangePwd_url = getBaseUrl() + "/user/security/setPwd";// 修改密码
    public static final String resetMobile_url = getBaseUrl() + "/user/security/resetMobile";// 重置安全手机
    public static final String setMobile_url = getBaseUrl() + "/user/security/setMobile";// 设置安全手机
    public static final String resetEmail_url = getBaseUrl() + "/user/security/resetEmail";// 重置安全邮箱
    public static final String setEmail_url = getBaseUrl() + "/user/security/setEmail";// 设置安全邮箱


    //贴吧

    public static final String hibai_block_list_url = getBaseUrl() + "/hiba/blockList";//获取hiBa所有专栏
    public static final String hibai_recommand_list_url = getBaseUrl() + "/hiba/recommandList";//获取hiBa精选
    public static final String hibai_topics_list_url = getBaseUrl() + "/hiba/topics";//获取hiBa帖子列表
    public static final String hibai_my_topics_url = getBaseUrl() + "/hiba/myTopics";//hiba我的帖子，发表帖子，收藏帖子/hiba/postTopic
    public static final String hibai_make_post_url = getBaseUrl() + "/hiba/postTopic";//hiba发帖子
    public static final String hibai_topic_operation_url = getBaseUrl() + "/hiba/topicOperation";//hiba对帖子的操作


    //社群

    public static final String group_list_url = getBaseUrl() + "/group/myGroupList";//获取我的社群
    public static final String single_group_list_url = getBaseUrl() + "/group/singleGroup";//h获取单个社群数据
    public static final String single_group_members_url = getBaseUrl() + "/group/members";//h获取单个社群成员
    public static final String single_group_apply_join_url = getBaseUrl() + "/group/applyJoin";//h申请加入社群
    public static final String creata_group_url = getBaseUrl() + "/group/creategroup";//创建社群
    public static final String creata_group_group_type_url = getBaseUrl() + "/group/groupTagList";//创建社群社群分类
    //    public static final String discover_list_url = getBaseUrl() + "/group/discoverList";//发现社群
    public static final String group_tag_list_url = getBaseUrl() + "/group/groupTagList";//社群分类
    public static final String group_set_admin_of_remove_url = getBaseUrl() + "/group/setAdmin";//设置管理员或者删除成员
    public static final String group_toplist_url = getBaseUrl() + "/group/groupTopicList";//社群讨论群/group/setMembers
    public static final String group_remove_group_member_url = getBaseUrl() + "/group/setMembers";//设置群成员
    public static final String group_reply_url = getBaseUrl() + "/group/processJoin";//处理申请加入社群的操作
    public static final String group_join_list_url = getBaseUrl() + "/group/applyList";//处理申请加入社群的消息队列
    public static final String group_edit_group_cover_image_url = getBaseUrl() + "/group/editGroupCoverImg";//编辑社群封面
    public static final String group_transfer_url = getBaseUrl() + "/group/groupTransfer";//转让社群/huodong/officialHuoDongs
    public static final String group_get_discover_group_url = getBaseUrl() + "/group/discoverListByPaing";//发现社群分页获取


    //活动
    public static final String huodong_list_url = getBaseUrl() + "/huodong/huodongList"; //发现活动集合/huodong/huodongInfo
    public static final String huodong_detail_url = getBaseUrl() + "/huodong/huodongInfo"; //发现活动集合
    public static final String huodong_join_url = getBaseUrl() + "/huodong/signupuserList"; //获取报名列表
    public static final String huodong_my_url = getBaseUrl() + "/huodong/myhuodong"; //我的活动列表
    public static final String huodong_hand_url = getBaseUrl() + "/huodong/posthuodong"; //提交活动
    public static final String getHuodong_join_or_cancel_url = getBaseUrl() + "/huodong/postsignup";//报名或者取消报名
    public static final String huodong_categroy_url = getBaseUrl() + "/huodong/huodongcategory";//获得分类活动
    public static final String huodong_my_post_url = getBaseUrl() + "/hiba/myTopics";//我的帖子/huodong/cancelhuodong
    public static final String huodong_cancel_url = getBaseUrl() + "/huodong/cancelhuodong";//取消活动


    //附近的人
    public static final String find_nearby_person_url = getBaseUrl() + "/timeline/nearpeople";

    //相册
    public static final String hiworld_upload_image_url = getBaseUrl() + "/photo/uploadImg";//上传照片的相册
    public static final String mine_delete_imageFile_url = getBaseUrl() + "/album/delete";//删除相册
    public static final String mine_edit_image_file_url = getBaseUrl() + "/album/edit";//编辑相册/user/info/usersByIds
    public static final String get_user_infor_by_ids_url = getBaseUrl() + "/user/info/usersByIds";//根据用户的ids获取多个用户的信息
    public static final String renamephoto = getBaseUrl() + "/photo/renamephoto";// 照片重命名
    //举报
    public static final String report_url = getBaseUrl() + "/sns/action/report";//举报updateLocation
    public static final String update_user_location_url = getBaseUrl() + "/user/info/updateLocation";//更新用户当前的位置信息

    public static final String visitFace = getBaseUrl() + "/user/info/visitFace";// 访问权限

    public static final String update_apk_url = getBaseUrl() + "/appinfo/updateApp";//版本更新

    public static final String getPhotoInfo = getBaseUrl() + "/sns/action/objectPraiseAndComment";// 获取对象的统计数(评论数、点赞数、浏览数)

    public static final String checkAnswer = getBaseUrl() + "/user/info/checkAnswer";// 校验访问问题

    public static final String blackSomebody = getBaseUrl() + "/sns/action/blackSomebody";// 拉黑用户

    public static final String activityRank = getBaseUrl() + "/class/activityRank";// 我的>活跃度排行榜

    //隐私权限
    public static final String mine_userBlackList_url = getBaseUrl() + "/user/privacy/userBlackList";//黑名单/user/privacy/disabledFriends
    public static final String mine_disableUserList_url = getBaseUrl() + "/user/privacy/disabledFriends";//获取屏蔽人集合
    public static final String mine_submitprivacy_url = getBaseUrl() + "/user/privacy/home/submitPrivacy";//提交用户隐私设置
    public static final String mine_disableuser_of_myhome_url = getBaseUrl() + "/user/privacy/home/getDisableUsers";//屏蔽访问主页用户集合
    public static final String mine_delete_disable_user_url = getBaseUrl() + "/user/privacy/home/cancelDisableUser";//删除屏蔽用户/user/privacy/home/getUserPrivacy
    public static final String mine_get_user_quality_setting_url = getBaseUrl() + "/user/privacy/home/getUserPrivacy";//获取用户设置的隐私权限--主页
    public static final String hieldUser_url = getBaseUrl() + "/user/privacy/home/disableUser";// 屏蔽用户(隐私设置用)

    /**
     * 0：取消关注；
     * 1：关注;
     * 2：特别关注；
     * 3：取消特别关注；
     * 4：加入黑名单
     * 5：取消加入黑名单，
     * 6：屏蔽动态；
     * 7：取消屏蔽动态；
     */
    public static final String sns_doActio_url = getBaseUrl() + "/sns/follow/doAction";//动态的操作/class/classApplyList
    //加入班级请求
    public static final String class_get_class_apply_list_url = getBaseUrl() + "/class/classApplyList";//获取申请加入班级的列表
    public static final String applyJoinByClassId_url = getBaseUrl() + "/class/applyJoinByClassId";// 加入班级申请（需要审核）
    //加入班级请求
    public static final String class_handle_class_apply_list_url = getBaseUrl() + "/class/processJoin";//处理班级加入请求
    public static final String group_getOfficeHuodongs_url = getBaseUrl() + "/slideshow/getSlideShows";//获取官方活动
    public static final String album_delete_images = getBaseUrl() + "/photo/delete";//批量删除照片
    public static final String applyclass_byQRcode = getBaseUrl() + "/class/applyJoinByQRCode";//通过扫描二维码加入班级/appinfo/qrCodeCreate
    public static final String getQRCOde_url = getBaseUrl() + "/appinfo/qrCodeCreate";//获取而二维码

    public static final String uploadfile_url = getBaseUrl() + "/file/uploadfile";// 通用上传文件

    //发现活动h5
    public static final String hai_shi_er = "http://www.hitongx.com/MobileApp/Account/HaiShiErApp";//
    public static final String authapp_url = getBaseUrl() + "/authapp/authAppInfos";//
    public static final String qrScanf_action_url = getBaseUrl() + "/appinfo/QrCodeScaning";//二维码扫描动作处理方式的请求
    public static final String photo_move_photos_to_other_album = getBaseUrl() + "/photo/movephoto";//将图片移动的其他相册
    public static final String group_update_group = getBaseUrl() + "/group/groupEdit";//编辑社群

    public static final String class_update_class = getBaseUrl() + "/class/classUpdate";//编辑班级
    public static final String getGrade = getBaseUrl() + "/class/gradeEditList";//获取学校下的年级
    public static final String getSchool = getBaseUrl() + "/class/schoolEditListByName";//获取学校
    public static final String getClass = getBaseUrl() + "/class/classEditList";//获取年级下的班级
    public static final String gradeGetList = getBaseUrl() + "/class/gradeGetList";// 引导界面获取年级下拉选项
    public static final String classGetList = getBaseUrl() + "/class/classGetList";// 引导界面获取班级下拉选项
    public static final String getSchoolSystemList = getBaseUrl() + "/class/eductionSystemList";//获取学校下的学段
    public static final String getHiBaAuthorty = getBaseUrl() + "/hiba/isIdentity";//判断hiba访问帖子者的权限
    public static final String commitSuggestion = getBaseUrl() + "/user/account/uploadFeedBack";//反馈意见
    public static final String dimissGroup = getBaseUrl() + "/group/groupDelete";//社群解散
    public static final String classSetMemberByPhone = getBaseUrl() + "/class/addMembers";//通过手机号添加老师或者学生为班级成员
    public static final String group_invitation_friends = getBaseUrl() + "/group/groupInvitation";//邀请好友加入社群
    public static final String hiBa_collectTopic = getBaseUrl() + "/hiba/collectTopic";//搜藏帖子

    public static final String group_invitation_list = getBaseUrl() + "/group/applyInvitationList";
    public static final String group_deal_invitation = getBaseUrl() + "/group/setInvitation";//处理被邀请加入社群消息

    public static final String getRecentlyApp = getBaseUrl() + "/authapp/appsUserInPlay";//获取用户最新玩的应用//
    public static final String getRecommondUserList_url = getBaseUrl() + "/timeline/recommendActivity";//获取推荐好友"http://121.40.167.81:9089"
    public static final String getAllApp = getBaseUrl() + "/app/list";//获取所有的应用
    public static final String sortUserApp = getBaseUrl() + "/app/setApp";//将用户排序后的应用信息提交给后台
    public static final String follwClassMembers = getBaseUrl() + "/sns/follow/follwClassMembers";//批量关注
    public static final String classIsExsit = getBaseUrl() + "/class/ifClassExsit";//判断某个班级是否存在
    public static final String joinOrTransferClassOnCreatingClass = getBaseUrl() + "/class/classTransferByCreateing";//创建班级过程中转让或者加入班级

    public static final String GetUserSyncClassList = getBaseUrl() + "/class/userSyncClassList";//获取用户同步班级
    public static final String SetDefaultSyncClasses = getBaseUrl() + "/class/setDefaultClass";//设置用户默认同步班级
    public static final String GetTopicList = getBaseUrl() + "/topic/topicList";//获取话题集合
    public static final String getAtMemberList = getBaseUrl() + "/class/classmates";//获取at的队形集合
    public static final String getDraftDetail = getBaseUrl() + "/blog/detail";//获取日志草稿详情
    public static final String getDraftList = getBaseUrl() + "/blog/list";//获取日志列表
    public static final String getDeleteDraft = getBaseUrl() + "/blog/delete";//删除日志
    public static final String getClassInfor = getBaseUrl() + "/class/classInfo";//获取班级信息
    public static final String getClassHuoDongList = getBaseUrl() + "/class/huodong/list";//获取班级活动列表
    public static final String createClassHuoDong = getBaseUrl() + "/class/huodong/postHuodong";//创建班级活动
    public static final String myClassHuoDongList = getBaseUrl() + "/class/huodong/myList";
    public static final String getAssociateClassMembers = getBaseUrl() + "/class/userAssociateClassMembers";
    public static final String ClassHuoDongInfor = getBaseUrl() + "/class/huodong/huodongInfo";//班级活动详情
    public static final String ClassHuoDongMembers = getBaseUrl() + "/class/huodong/signupUserList";//获取班级活动成员
    public static final String ClassHuoDongCreateBlog = getBaseUrl() + "/class/huodong/postHuodongBlog";//发布班级活动随记/class/huodong/postHuodongBlog
    public static final String ClassHuoDongCancel = getBaseUrl() + "/class/huodong/cancel";//删除班级活动
    public static final String ClassHuoDongTimeLine = getBaseUrl() + "/class/huodong/timeline";//班级活动时间轨迹

    public static final String ClassHuoDongSignUpOrExit = getBaseUrl() + "/class/huodong/postSignup";//班级活动报名或者退出
    public static final String getGrouthTaskList = getBaseUrl() + "/classTask/list";//成长任务
    public static final String getPointRecords = getBaseUrl() + "/classTask/pointRecordList";//成长值列表
    public static final String Advertisement = getBaseUrl() + "/home/getAdvertisement";//活动推广
    public static final String specialRightList = getBaseUrl() + "/user/info/specialRightList";// 用户拥有的特权

    //h5页面
    public static final String userleve = getH5BaseUrl() + "/app/?token={token}&userId=0c227414-b856-4abd-93ba-6b4190be7b46#/userleve";//等级轴
    public static final String commonproblem = getH5BaseUrl() + "/app/?token={token}#/commonproblem";//常见问题
    public static final String userleveldetail = getH5BaseUrl() + "/app/?token={token}#/userleveldetail";//了解等级特权
    public static final String userlevel = getH5BaseUrl() + "/app/?token={token}#/userlevel";//用户等级
    public static final String search = getH5BaseUrl() + "/app/?token={token}#/search";//搜索
    public static final String playvideo = getH5BaseUrl() + "/app/#/playvideo?videoId=";//视频播放h5地址
    public static final String jifenShop = "https://www.hitongx.com/jifenShop?token={token}";//积分商城
    public static final String huodongDisclaimer = getH5BaseUrl() + "/app/#/huodongdisclaimer";//视频播放h5地址 http://192.168.0.6:8080/#/huodongdisclaimer
    /**
     * 阅读打卡-用户选择书架上书
     */
    public static final String USER_SELECT_BOOK_INFORMATION = getH5BaseUrl() + "/punch/?token={token}#/chooseBook?userId={userId}&classId={classId}";

    //    public static final String growthrecord = getH5BaseUrl() + "/growthrecord/?token={token}&userId={userId}#/index";//成长记录
    public static final String growthrecord = getH5BaseUrl() + "/growthrecord/?token={token}#/index?userId={userId}";//成长记录
    public static final String homework = getH5BaseUrl() + "/homework/#!/index?token={token}";//发作业
    //选修课


    //发现模块
    public static final String findInfor = getBaseUrl() + "/find/list";//获取发现模块数据
    public static final String getCategory = getBaseUrl() + "/group/tagList";//社群一级分类，二级分类

    public static final String getGroupHuoDongList = getBaseUrl() + "/group/huodong/list";//社群中活动列表
    public static final String postGroupHuoDong = getBaseUrl() + "/group/huodong/postHuodong";//社群活动的创建和编辑
    public static final String getGroupHuoDongDetailInfor = getBaseUrl() + "/group/huodong/huodongInfo";//获取社群活动详情
    public static final String groupHuoDongSignOrQuit = getBaseUrl() + "/group/huodong/postSignup";//社群活动报名或者退出
    public static final String getGroupHuoDongMembers = getBaseUrl() + "/group/huodong/signupUserList";//获取社群活动成员
    public static final String createGroupTopic = getBaseUrl() + "/group/noteCreate";//社群发帖

    public static final String getSchoolClasses = getBaseUrl() + "/school/classList";//获取学校下的班级

    public static final String getGroupnoInvitationMembers = getBaseUrl() + "/group/noInvitationMembers";//获取班级内未被邀请加入社群的成员
    public static final String groupInvitation = getBaseUrl() + "/group/groupInvitation";//社群邀请
    public static final String setInvitation = getBaseUrl() + "/group/setInvitation";//处理社群邀请请求
    public static final String getGroupHotTopicList = getBaseUrl() + "/group/topicList";//获取社群中热门的话题列表
    public static final String GroupHandleJoinNew = getBaseUrl() + "/group/processJoin";//处理社群加入请求
    public static final String cancelGroupHuoDong = getBaseUrl() + "/group/huodong/cancel";//取消社群活动
    public static final String getMyGroupHuoDongList = getBaseUrl() + "/group/huodong/myList";//获取社群中我的活动


    /*关于加入班级模块*/
    public static final String joinSpecClass = getBaseUrl() + "/class/applyClassById";//加入班级
    /**
     * 创建班级过程中加入/转让班级（在创建或者修改班级过程中，
     * 检测到班级已存在，并且用户同意加入到该班级，调用此接口。
     * 如果该班级原有班主任是默认班主任，直接转让，如果不是默认班级，直接加入该班级。）
     */
    public static final String joinSpecCreateClass = getBaseUrl() + "/class/classTransferByCreateing";//加入班级
    public static final String getSpecGradeList = getBaseUrl() + "/class/applyGetGradeList";//获取指定学校的年级列表
    public static final String getSpecClassList = getBaseUrl() + "/class/applyGetClassList";//获取指定年级的班次列表


    /**
     * 支持多个图片上传，返回的是图片全路径的图片数组。上传时指定宽度，返回时也会返回缩略图
     */
    public static final String uploadAttachment = getBaseUrl() + "/common/postAttachmentUpload";


    /****************************社群模块****************************************/
    /**
     * 社群标签
     */
    public static final String communityTag = getBaseUrl() + "/group/tagList";
    /**
     * 获得安全票据接口
     */
    public static final String VerfiyCode = getBaseUrl() + "/user/security/isValidVerfiyCode";
    /**
     * 获取二级标签的数据
     */
    public static final String secondGroupInfor = getBaseUrl() + "/group/findSingleTagGroupsListPaing";
    /**
     * 创建社群
     */
//    public static final String create_group_community = getBaseUrl() + "/group/groupCreate";
    public static final String create_group_community = getBaseUrl() + "/group/groupCreate";
    /**
     * 社群签到接口
     */
    public static final String group_community_sign = getBaseUrl() + "/group/groupVisitList";
    public static final String getUserLastCreateAlbum = getBaseUrl() + "/album/getLatestAlbum";


    /**
     * 获取社群或者班级成员身份的类型
     */
    public static final String getMemberList = getBaseUrl() + "/common/getMemberList";

    /**
     * 阅读打卡获取上一次阅读书籍
     */
    public static final String GET_LAST_BOOK_INFORMATION = BASE_URL + "/readPunch/bookStudent/getLastBookInfo";


    /**
     * 发布读书记录
     */
    public static final String PUBLISH_BOOK_RECORD = BASE_URL + "/readPunch/bookStudent/createBookRecord";

    /**
     * 阅读任务H5
     */
    public static final String READ_TASK = "http://192.168.0.95:8080" + "/#/!/index?token={token}&readTrackId=2";


    /**
     * 根据扫描书码去获取书籍信息
     */
    public static final String GET_SCAN_BOOK_INFORMATION = BASE_URL + "/readPunch/book/getBookByIsbn";
    /**
     * 用户手动输入的书籍信息
     */
    public static final String ADD_BOOK_INFORMATION = BASE_URL + "/readPunch/book/bookAdd";


    /**
     * ***************************************************************
     * 作业相关接口
     * ***************************************************************
     */

    /**
     * 老师或者学生某段时间内作业列表
     */
    public static final String TEACHER_OR_STUDENT_ALREADY_HOMEWORK_LIST = BASE_URL + "/homework/homeWorkListNew";
    /**
     * 老师---所有作业列表
     */
    public static final String TEACHER_ALREADY_HOMEWORK_LIST_ALL = BASE_URL + "/homework/homeWorkAllListNew";
    /**
     * 添加小组
     */
    public static final String TEACHER_HOMEWORK_ADD_TEAM = BASE_URL + "/homework/team/add";
    /**
     * 修改小组
     */
    public static final String TEACHER_HOMEWORK_EDIT_TEAM = BASE_URL + "/homework/team/edit";
    /**
     * 删除小组
     */
    public static final String TEACHER_HOMEWORK_DEL_TEAM = BASE_URL + "/homework/team/del";
    /**
     * 小组列表信息
     */
    public static final String TEACHER_HOMEWORK_LIST_TEAM = BASE_URL + "/homework/team/list";
    /**
     * 小组成员列表信息
     */
    public static final String TEACHER_HOMEWORK_LIST_TEAM_MEMBER = BASE_URL + "/homework/teamMember/list";

    /**
     * 科目列表
     */
    public static final String HOMEWORK_SUBJECT_LIST = BASE_URL + "/homework/subject/list";
    /**
     * 科目添加
     */
    public static final String HOMEWORK_SUBJECT_ADD = BASE_URL + "/homework/subject/add";
    /**
     * 科目删除
     */
    public static final String HOMEWORK_SUBJECT_DEL = BASE_URL + "/homework/subject/del";

    /**
     * 获取作业完成情况
     */
    public static final String HOMEWORK_STATUS = BASE_URL + "/homework/homeworkStatus";

}
