//package com.histudent.jwsoft.histudent.hiworld.bean;
//
///**
// * 用于选择用户是否有查看相册权限的实体类
// * Created by ZhangYT on 2016/8/4.
// */
//public myclass SelectStudentBean {
//    private String id;//用户的id
//    private boolean isSelected;//是否被选择的标记
//    private String stuName;//用户名称
//
//    public String getStuName() {
//        return stuName;
//    }
//
//    public void setStuName(String stuName) {
//        this.stuName = stuName;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public boolean isSelected() {
//        return isSelected;
//    }
//}
package com.histudent.jwsoft.histudent.body.hiworld.bean;

/**
 * 用于选择用户是否有查看相册权限的实体类
 * Created by ZhangYT on 2016/8/4.
 */
public class SelectStudentBean {
    private String id;//用户的id
    private boolean isSelected;//是否被选择的标记
    private String stuName;//用户名称

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
