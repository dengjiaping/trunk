package com.histudent.jwsoft.histudent.model.entity;

import com.histudent.jwsoft.histudent.body.message.model.ClassModel;

import java.util.List;

/**
 * Created by huyg on 2017/8/9.
 */

public class BadgeClickEvent {

    public List<ClassModel.ClassBadgesBean> badgesBeen;

    public BadgeClickEvent(List<ClassModel.ClassBadgesBean> badgesBeen) {
        this.badgesBeen = badgesBeen;
    }
}
