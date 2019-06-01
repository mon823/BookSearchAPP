package kr.ac.jbnu.se.mm2019Group1.models;

import java.io.Serializable;

public class Comment implements Serializable {
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String comment;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String writer;

    public String getWirterUid() {
        return wirterUid;
    }

    public void setWirterUid(String wirterUid) {
        this.wirterUid = wirterUid;
    }

    public String wirterUid;

    public Comment(){

    }

    public Comment(String comment,String date,String writer, String wirterUid){
        this.comment = comment;
        this.date = date;
        this.writer = writer;
        this.wirterUid = wirterUid;
    }
}
