package com.histudent.jwsoft.histudent.commen.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.RecentContactsModel;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.bean.CodeModel;
import com.histudent.jwsoft.histudent.commen.keyword.utils.SharedPreferencedUtils;
import com.histudent.jwsoft.histudent.commen.model.FileInfo;
import com.histudent.jwsoft.histudent.commen.model.SaveAccountModel;
import com.histudent.jwsoft.histudent.commen.model.SavePostDataModel;
import com.histudent.jwsoft.histudent.model.constant.ParamKeys;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.netease.nim.uikit.NimUIKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jezhee on 2/20/15.
 * 缓存类
 */
public class HiCache {

    private Context context;
    private CurrentUserDetailInfoModel userUserInfo;//当前访问的用户的详细信息
    private ClassModel currentClassInfo;//当前访问的班级的详细信息
    private GroupBean groupBean;//当前访问的社群的详细信息
    private static HiCache cache;
    private Map<String, CodeModel> codeMap;

    private HiCache() {
    }

    private static final class HiCacheHolder{
        private static final HiCache INSTANCE = new HiCache();
    }

    public static final HiCache getInstance(){
        return HiCacheHolder.INSTANCE;
    }


    public ClassModel getClassDetailInfo() {
        return currentClassInfo;
    }

    public void setClassDetailInfo(ClassModel currentClassInfo) {
        this.currentClassInfo = currentClassInfo;
    }

    public GroupBean getGroupDetailInfo() {
        return groupBean;
    }

    public void setGroupDetailInfo(GroupBean groupBean) {
        this.groupBean = groupBean;
    }

    public CurrentUserDetailInfoModel getUserDetailInfo() {
        return userUserInfo;
    }

    public void setUserDetailInfo(CurrentUserDetailInfoModel userDetailInfo) {
        this.userUserInfo = userDetailInfo;
    }

    public void init(String account, String password, CurrentUserInfoModel currentUserInfo) {
        String imId = currentUserInfo.getUserId().replace("-", "");
        NimUIKit.setAccount(imId);
        com.netease.nim.uikit.NimUIKit.setAccount(imId);
        saveAccount(account);
        saveLoginUserInfo(currentUserInfo);
        saveAccountIntoDB(account, password, currentUserInfo);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * ====================================本地存储相关==========================================================================================
     */
    private String saveName_login = "saveIsFristLogin";//保存所有登录成功账号的文件名称
    private String saveccount = "saveAccounts";//保存上一次登录成功账号的文件名称
    private String saveUrl = "saveUrl";
    private String saveRecentContactsTopFlag = "saveRecentContactsTopFlag";

    /**
     * 保存置顶会话
     */
    public void setRecentContactsTopFlag(String accountId) {
        if (TextUtils.isEmpty(accountId)) return;
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(saveRecentContactsTopFlag, HTApplication.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(accountId, System.currentTimeMillis());
        editor.apply();
    }

    /**
     * 取消置顶会话
     */
    public void removeRecentContactsTopFlag(String accountId) {
        if (TextUtils.isEmpty(accountId)) return;
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(saveRecentContactsTopFlag, HTApplication.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(accountId, 0);
        editor.apply();
    }

    /**
     * 获取置顶会话
     */
    public long getRecentContactsTopFlag(String accountId) {
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(saveRecentContactsTopFlag, HTApplication.getInstance().MODE_PRIVATE);
        return sharedPreferences.getLong(accountId, 0);
    }

    /**
     * 登录状态标记（0：首次登录，1：登录成功 2：退出登录）
     */
    public void exchangeLoginStatue(int loginStatue) {
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(saveName_login, HTApplication.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("loginStatue", loginStatue);
        editor.apply();
    }

    /**
     * 是否是第一次启动
     */
    public int getLoginStatue() {
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(
                saveName_login, HTApplication.getInstance().MODE_PRIVATE);
        return sharedPreferences.getInt("loginStatue", 0);
    }

    /**
     * 记录网址（测试）
     */
    public void saveUrl(String url) {
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(
                saveUrl, HTApplication.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", url);
        editor.apply();
    }

    /**
     * 读出网址（测试）
     */
    public String getUrl() {
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(
                saveUrl, HTApplication.getInstance().MODE_PRIVATE);
        return sharedPreferences.getString("url", null);
    }

    /**
     * 保存上一次登录成功后的账号
     *
     * @param userName
     */
    public void saveAccount(String userName) {

        if (TextUtils.isEmpty(userName))
            return;
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(
                saveccount, HTApplication.getInstance().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.apply();
    }

    /**
     * 取出上一次登录成功后的账号
     *
     * @return
     */
    public String getAcount() {
        SharedPreferences sharedPreferences = HTApplication.getInstance().getSharedPreferences(
                saveccount, HTApplication.getInstance().MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", null);
        return userName;
    }

    /**
     * ====================================数据库存储相关==========================================================================================
     */

    public DbUtils getBD() {
        DbUtils db = DbUtils.create(context, "hitongx.db", 4,
                new DbUtils.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {
                        try {
                            dbUtils.createTableIfNotExist(RecentContactsModel.class);
                            dbUtils.createTableIfNotExist(SavePostDataModel.class);
                            //dbUtils.execNonQuery("alter table ShoppingCar add shopId text");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        db.configAllowTransaction(true);// 开启事务
        db.configDebug(false);// debug，输出sql语句
        return db;
    }

    /**
     * =========================================账号相关============================================
     */

    /**
     * 取出当前登录用户的信息model
     *
     * @return
     */
    public CurrentUserInfoModel getLoginUserInfo() {
        CurrentUserInfoModel model = null;
        try {
            model = getBD().findById(CurrentUserInfoModel.class, "当前登录用户信息");
        } catch (DbException e) {
            e.printStackTrace();
        } finally {
            return model;
        }
    }

    /**
     * 保存当前登录用户的信息model
     *
     * @return
     */
    public void saveLoginUserInfo(CurrentUserInfoModel currentUserInfo) {
        try {
            currentUserInfo.setId("当前登录用户信息");
            getBD().saveOrUpdate(currentUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将账号数据存入数据库
     */
    public void saveAccountIntoDB(String userName, String pwd, CurrentUserInfoModel currentUserInfo) {

        if (currentUserInfo == null || userName == null) {
            return;
        }

        try {
            //将账号信息存入数据库
            SaveAccountModel model = new SaveAccountModel();
            model.setId(currentUserInfo.getUserId());
            model.setAccount(userName);
            model.setPwd(pwd);
            model.setAvatar(currentUserInfo.getAvatar());
            getBD().saveOrUpdate(model);
        } catch (Exception e) {
            HiStudentLog.e("---------saveAccountIntoDB----->账号存入数据库异出现异常 ：" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 修改当前登录用户的头像信息
     *
     * @return
     */
    public void saveLoginUserAvatar(String path) {
        try {
            CurrentUserInfoModel model = getBD().findById(CurrentUserInfoModel.class, "当前登录用户信息");
            model.setAvatar(path);
            getBD().saveOrUpdate(model);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的账号信息
     */
    public List<SaveAccountModel> getAllAccountDataInDB() {
        try {
            return getBD().findAll(SaveAccountModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据账号获取的账号信息
     */
    public SaveAccountModel getAccountDataInDB(String account) {

        try {
            SaveAccountModel model = getBD().findFirst(Selector.from(SaveAccountModel.class).where("account", "=", account));
            if (model == null) {
                HiStudentLog.e("--------------> 数据库中没有  " + account + "   的账号数据");
                return null;
            } else {
                HiStudentLog.e("--------------> 从数据库获取账号数据");
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace();
            HiStudentLog.e("-------------->从数据库获取指定账号数据出现异常 ：" + e.toString());
            return null;
        }

    }

    /**
     * 更新头像信息
     */
    public SaveAccountModel updataAccountDataInDB(String avatar) {
        try {
            SaveAccountModel model = getBD().findById(SaveAccountModel.class, getLoginUserInfo().getUserId());
            model.setAvatar(avatar);
            getBD().saveOrUpdate(model);
            if (model == null) {
                return null;
            } else {
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 账号密码赋空
     */
    public void clearAccountInDB() {
        try {

            SaveAccountModel model = getBD().findById(SaveAccountModel.class, getLoginUserInfo().getUserId());
            model.setPwd("-1");
            getBD().saveOrUpdate(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除数据库中的一条账号信息
     */
    public void deletAccountDataInDB(SaveAccountModel model) {
        try {
            getBD().delete(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * =========================================录音相关============================================
     */

    /**
     * 取出录音信息
     *
     * @return
     */
    public FileInfo getVoiceInfo(String id) {
        FileInfo info = null;
        try {
            info = getBD().findById(FileInfo.class, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 保存录音信息
     *
     * @return
     */
    public void saveVoiceInfo(String id, int time, String path) {
        FileInfo info = new FileInfo();
        info.setId(id);
        info.setTime(time);
        info.setFile(path);
        try {
            getBD().saveOrUpdate(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * =========================================推送相关============================================
     */

    /**
     * 将传送过来的系统通知入库
     */
    public void saveSystemNotification(RecentContactsModel model) {
        if (model == null) return;
        try {
            model.setId(model.getTime() + "");
            getBD().saveOrUpdate(model);
            HiStudentLog.e("-------入库消息----->" + "  成功");
        } catch (Exception e) {
            HiStudentLog.e("-------入库消息----->" + "  失败：" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 取出入库的系统通知
     */
    public List<RecentContactsModel> getSystemNotification(int chatType) {
        try {
            List<RecentContactsModel> models = getBD().findAll(Selector.from(RecentContactsModel.class).where("chatType", "=", chatType).and("userId", "=", HiCache.getInstance().getUserDetailInfo().getUserId()));
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询数据库中是否已有该条数据
     */
    public boolean isHaveTheSystemNotification(String batchNumber) {
        try {
            List<RecentContactsModel> models = getBD().findAll(Selector.from(RecentContactsModel.class).where("batchNumber", "=", batchNumber));
            return models.size() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改入库系统通知的未读数量
     */
    public void exchangeSystemNotificationUnreadNum(int chatType) {
        try {
            List<RecentContactsModel> models = getBD().findAll(Selector.from(RecentContactsModel.class).where("chatType", "=", chatType).and("userId", "=", HiCache.getInstance().getUserDetailInfo().getUserId()));
            for (RecentContactsModel recentContactsModel : models) {
                recentContactsModel.setIsRead(-1);
                getBD().saveOrUpdate(recentContactsModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * =========================================网络请求相关============================================
     */

    /**
     * 将网络请求数据存入数据库
     *
     * @param url
     * @param data
     */
    public void saveHttpDataIntoDB(String url,String param, String data) {
        try {
            //将数据信息存入数据库
            SavePostDataModel model = new SavePostDataModel();
            model.setId(url+param);
            model.setData(data);
            HiStudentLog.e("-------------->保存或更新网络数据入库");
            getBD().saveOrUpdate(model);
        } catch (Exception e) {
            e.printStackTrace();
            HiStudentLog.e("-------------->存储网络数据到数据库出现异常 ：" + e.toString());

        }
    }

    /**
     * 根据url从数据库中获取网络请求的数据
     *
     * @param url
     */
    public String getHttpDataInDB(String url,String param) {
        try {
            SavePostDataModel model = getBD().findById(SavePostDataModel.class, url+param);
            if (model == null) {
                HiStudentLog.e("--------------> 数据库中没有该网络数据：");
                return null;
            } else {
                String info = model.getData();
                HiStudentLog.e("--------------> 从数据库获取到网络数据：");
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
            HiStudentLog.e("--------------> 从数据库获取网络数据出现异常 ：" + e.toString());
            return null;
        }

    }

    /**
     * =========================================验证码相关============================================
     */


    /**
     * 是否需要获取验证码
     *
     * @param phone
     */
    public int isNeedGetCode(String phone) {
        if (codeMap == null) {//第一次请求验证码
            codeMap = new HashMap<>();
            return 0;
        } else {
            CodeModel model = codeMap.get(phone);
            if (model == null) {//此号码第一次请求
                return 0;
            } else {
                int num = (int) (System.currentTimeMillis() - model.getTime()) / 1000;
                return num > 60 ? 0 : 60 - num;
            }
        }
    }

    public void saveCodeModel(CodeModel codeModel) {
        if (codeMap == null) return;
        codeMap.put(codeModel.getPhone(), codeModel);
    }

    /**
     * 清空用户Token
     */
    public void clearUserToken() {
        CurrentUserInfoModel loginUserInfo = HiCache.getInstance().getLoginUserInfo();
        loginUserInfo.setToken(null);
        HiCache.getInstance().saveLoginUserInfo(loginUserInfo);
    }

    /**
     * 取出SP中所保存的token
     *
     * @return
     */
    public String getUserLoginToken() {
        return SharedPreferencedUtils.getString(HTApplication.getInstance(), ParamKeys.TOKEN);
    }

}
