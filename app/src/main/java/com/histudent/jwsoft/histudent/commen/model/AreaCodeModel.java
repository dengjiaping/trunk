package com.histudent.jwsoft.histudent.commen.model;

/**
 * Created by liuguiyu-pc on 2016/8/9.
 */
public class AreaCodeModel {

    private String AreaCode;
    private String ParentCode;
    private String Name;
    private String PinyingName;
    private String Depth;
    private char firstCharacter;//后添加，不手动赋值为空
    private String PinYinNameAll;//后添加，不手动赋值为空
    private boolean isSelected; //后添加，表示该地区是否是被选择状态

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public char getFirstCharacter() {
        return firstCharacter;
    }

    public void setFirstCharacter(char firstCharacter) {
        this.firstCharacter = firstCharacter;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPinyingName() {
        return PinyingName;
    }

    public void setPinyingName(String pinyingName) {
        PinyingName = pinyingName;
    }

    public String getDepth() {
        return Depth;
    }

    public void setDepth(String depth) {
        Depth = depth;
    }

    public String getPinYinNameAll() {
        return PinYinNameAll;
    }

    public void setPinYinNameAll(String pinYinNameAll) {
        PinYinNameAll = pinYinNameAll;
    }

    @Override
    public String toString() {
        return "AreaCodeModel{" +
                "AreaCode='" + AreaCode + '\'' +
                ", ParentCode='" + ParentCode + '\'' +
                ", Name='" + Name + '\'' +
                ", PinyingName='" + PinyingName + '\'' +
                ", Depth='" + Depth + '\'' +
                ", firstCharacter=" + firstCharacter +
                ", PinYinNameAll='" + PinYinNameAll + '\'' +
                '}';
    }
}
