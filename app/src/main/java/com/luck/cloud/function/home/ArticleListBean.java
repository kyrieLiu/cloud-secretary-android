package com.luck.cloud.function.home;

/**
 * Created by liuyin on 2018/11/14 13:57
 * @Describe 通知DTO
 */

public class ArticleListBean {


    /**
     * id : 2
     * relatedInformation : 2
     * projectId : 1
     * type : 2
     * title : 阿斯蒂芬2
     * createTime : 2018-11-15 13:19:00.0
     * distinguish : 1
     */

    private int id;
    private int relatedInformation;
    private int projectId;
    private int type;
    private String title;
    private String createTime;
    private int distinguish;
    private String article ; // 公告信息
    private String picture;


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelatedInformation() {
        return relatedInformation;
    }

    public void setRelatedInformation(int relatedInformation) {
        this.relatedInformation = relatedInformation;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDistinguish() {
        return distinguish;
    }

    public void setDistinguish(int distinguish) {
        this.distinguish = distinguish;
    }
}
