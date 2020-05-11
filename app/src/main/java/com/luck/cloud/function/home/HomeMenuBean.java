package com.luck.cloud.function.home;

/**
 * Created by liuyin on 2019/2/21 14:04
 */
public class HomeMenuBean {
    private String menuName;
    private int iconPath;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getIconPath() {
        return iconPath;
    }

    public void setIconPath(int iconPath) {
        this.iconPath = iconPath;
    }
}
