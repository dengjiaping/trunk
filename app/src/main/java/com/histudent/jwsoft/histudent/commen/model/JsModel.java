package com.histudent.jwsoft.histudent.commen.model;

import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/8.
 */

public class JsModel {


    /**
     * title : 测试标题
     * bgColor :
     * left : {"name":"btnLeft","control":true,"show":true,"position":"right","text":"","icon":""}
     * right : {"name":"btnRight","control":true,"show":true,"position":"right","text":"右侧按钮","icon":""}
     * items : [{"name":"defaultName","show":true,"position":"right","text":"","icon":""}]
     */

    private String title;
    private String bgColor;
    private LeftBean left;
    private RightBean right;
    private List<ItemsBean> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public LeftBean getLeft() {
        return left;
    }

    public void setLeft(LeftBean left) {
        this.left = left;
    }

    public RightBean getRight() {
        return right;
    }

    public void setRight(RightBean right) {
        this.right = right;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class LeftBean {
        /**
         * name : btnLeft
         * control : true
         * show : true
         * position : right
         * text :
         * icon :
         */

        private String name;
        private boolean control;
        private boolean show;
        private String position;
        private String text;
        private String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isControl() {
            return control;
        }

        public void setControl(boolean control) {
            this.control = control;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class RightBean {
        /**
         * name : btnRight
         * control : true
         * show : true
         * position : right
         * text : 右侧按钮
         * icon :
         */

        private String name;
        private boolean control;
        private boolean show;
        private String position;
        private String text;
        private String icon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isControl() {
            return control;
        }

        public void setControl(boolean control) {
            this.control = control;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class ItemsBean {
        /**
         * name : defaultName
         * show : true
         * position : right
         * text :
         * icon :
         */

        private String name;
        private boolean show;
        private String position;
        private String text;
        private String icon;
        /**
         * iconId :
         * control : true
         */

        private String iconId;
        private boolean control;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconId() {
            return iconId;
        }

        public void setIconId(String iconId) {
            this.iconId = iconId;
        }

        public boolean isControl() {
            return control;
        }

        public void setControl(boolean control) {
            this.control = control;
        }
    }
}
