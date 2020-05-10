package com.luck.cloud.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
    /**
     * 节点id
     */
    private int id;
    /**
     * 父节点id
     */
    private String pId;
    /**
     * 是否展开
     */
    private boolean isExpand = false;
    private boolean isChecked = false;
    private boolean isHideChecked = true;
    /**
     * 节点名字
     */
    private String name;
    /**
     * 条目不可再选择了
     */
    private boolean haveSelect;

    /**
     * 节点展示图标
     */
    private int icon;
    /**
     * 节点所含的子节点
     */
    private List<Node> childrenNodes = new ArrayList<Node>();
    /**
     * 节点的父节点
     */
    private Node parent;
    /**
     * 已经选择的最后一级的条目数
     */
    private int selectCount;

    /**
     * 子条目最后一级的总数
     */
    private int allCount;
    /**
     * roleLevel==2的时候为最后一层
     */
    private int roleLevel;

    private String showID;

    /**
     * 装载其他参数
     */
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node() {
    }

    public Node(String pId, String name, int roleLevel, String showID, String data) {
        this.pId = pId;
        this.name = name;
        this.roleLevel = roleLevel;
        this.showID = showID;

        this.data = data;
    }


    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public boolean isHaveSelect() {
        return haveSelect;
    }

    public void setHaveSelect(boolean haveSelect) {
        this.haveSelect = haveSelect;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 当父节点收起，其子节点也收起
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {

            for (Node node : childrenNodes) {
                node.setExpand(isExpand);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public List<Node> getChildrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(List<Node> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 判断是否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return childrenNodes == null || childrenNodes.size() == 0;
    }


    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isHideChecked() {
        return isHideChecked;
    }

    public void setHideChecked(boolean isHideChecked) {
        this.isHideChecked = isHideChecked;
    }

    @Override
    public String toString() {
        return "Node{" +
                "showID=" + showID +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Node node = (Node) obj;//List中已经包含的Node
        boolean isContains = node.getShowID().equals(this.showID) && node.getId() == this.id && node.getRoleLevel() == this.roleLevel && node.getpId() == this.pId;
        return isContains;
    }
}
