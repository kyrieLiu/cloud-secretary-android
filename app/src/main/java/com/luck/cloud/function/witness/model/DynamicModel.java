package com.luck.cloud.function.witness.model;

import java.util.List;

public class DynamicModel {
    private int isLike;
    private List<CommentModel> commentModel;
    private List<String> likeUsers;

    public List<String> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<String> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public List<CommentModel> getCommentModel() {
        return commentModel;
    }

    public void setCommentModel(List<CommentModel> commentModel) {
        this.commentModel = commentModel;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
