package com.luck.cloud.function.study.model;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class StudyModel {
    private List<LocalMedia> pictureList;
    private String singleImage;

    public List<LocalMedia> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<LocalMedia> pictureList) {
        this.pictureList = pictureList;
    }

    public String getSingleImage() {
        return singleImage;
    }

    public void setSingleImage(String singleImage) {
        this.singleImage = singleImage;
    }
}
