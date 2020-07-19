package com.luck.cloud.function.mine.bean;

import java.util.List;

public class PersonInfoBean {

    /**
     * peopleId : 299
     * peopleLoginname : first
     * peopleName : 真实姓名
     * peopleMobile : 15001121167
     * peopleType : 1
     * parkId : 1
     * roles : []
     * userType : 1
     * idCard : 3729221994
     * nickname : 昵称
     * village :
     * school :
     * industry :
     * affiliatedUnit : 单位
     * fansCount : 0
     * attentionCount : 0
     * dynamicCount : 0
     */

    private int peopleId;
    private String peopleLoginname;
    private String peopleName;
    private String peopleMobile;
    private String peopleType;
    private int parkId;
    private int userType;
    private String idCard;
    private String nickname;
    private String village;
    private String school;
    private String industry;
    private String affiliatedUnit;
    private int fansCount;
    private int attentionCount;
    private int dynamicCount;
    private String photoLogo;
    private List<?> roles;

    public String getPhotoLogo() {
        return photoLogo;
    }

    public void setPhotoLogo(String photoLogo) {
        this.photoLogo = photoLogo;
    }

    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleLoginname() {
        return peopleLoginname;
    }

    public void setPeopleLoginname(String peopleLoginname) {
        this.peopleLoginname = peopleLoginname;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleMobile() {
        return peopleMobile;
    }

    public void setPeopleMobile(String peopleMobile) {
        this.peopleMobile = peopleMobile;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAffiliatedUnit() {
        return affiliatedUnit;
    }

    public void setAffiliatedUnit(String affiliatedUnit) {
        this.affiliatedUnit = affiliatedUnit;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(int dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public List<?> getRoles() {
        return roles;
    }

    public void setRoles(List<?> roles) {
        this.roles = roles;
    }
}
