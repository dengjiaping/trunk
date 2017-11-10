package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.ClassMemberEntity;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 处理添加成员列表数据
 */

public class AddMemberDataConvert extends DataConvert {

    @Override
    public List<CommonMemberBean> convert() {
        final JSONObject jsonObject = JSONObject.parseObject(mJsonData);
        final JSONArray studentJsonArray = jsonObject.getJSONArray("stuClassMembers");
        final int size = studentJsonArray.size();
        CommonMemberBean commonMemberBean = null;
        for (int i = 0; i < size; i++) {
            commonMemberBean = new CommonMemberBean();
            final JSONObject subJsonObject = studentJsonArray.getJSONObject(i);
            final String userId = subJsonObject.getString("userId");
            final String userRealName = subJsonObject.getString("userRealName");
            final String userAvatar = subJsonObject.getString("userAvatar");
            commonMemberBean.setUserId(userId)
                    .setName(userRealName)
                    .setHeadIconUrl(userAvatar);
            ENTITYS.add(commonMemberBean);
        }
        return ENTITYS;
    }

    public List<CommonMemberBean> convertEntity(ClassMemberEntity classMemberEntity) {
        final List<CommonMemberBean> commonMemberBeanList = new ArrayList<>();
        final List<ClassMemberEntity.SubMember> stuClassMembers = classMemberEntity.getStuClassMembers();
        CommonMemberBean commonMemberBean;
        if (stuClassMembers.size() > 0)
            for (ClassMemberEntity.SubMember stuClassMember : stuClassMembers) {
                final int userType = stuClassMember.getUserType();
                if (userType == 1) {
                    //仅仅是学生的话才 加入成员列表
                    final String userAvatar = stuClassMember.getUserAvatar();
                    final String userId = stuClassMember.getUserId();
                    final String userRealName = stuClassMember.getUserRealName();
                    commonMemberBean = new CommonMemberBean();
                    commonMemberBeanList.add(commonMemberBean.setUserId(userId)
                            .setName(userRealName)
                            .setHeadIconUrl(userAvatar));
                }
            }
        return commonMemberBeanList;
    }

}
