package com.ryd.gyy.guolinstudy.Model;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 根据对象关系映射模式的理念，每一张表news都应该对应一个模型(Model)News
 */
public class News extends LitePalSupport {
    private int id;

    private String title;

    private String content;

    private Date publishDate;

    private int commentCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


    //    News和Introduction是一对一的关系
    private Introduction introduction;

    //     Comment和News是多对一的关系
    private List<Comment> commentList = new ArrayList<Comment>();

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    /**
     * @return 获取此条news的评论内容(从少者出发,查询每条新闻对应的评论内容,而不是查询每个评论对应的新闻)
     */
    public List<Comment> getComments() {
        return LitePal.where("news_id = ?", String.valueOf(id)).find(Comment.class);
    }

    //    Category和News多对多的关系
    private List<Category> categoryList = new ArrayList<Category>();

    // 自动生成get、set方法

}
