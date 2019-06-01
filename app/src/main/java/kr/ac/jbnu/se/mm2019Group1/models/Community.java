package kr.ac.jbnu.se.mm2019Group1.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String title;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String mainText;

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String writer;

    public String getWriterUid() {
        return writerUid;
    }

    public void setWriterUid(String writerUid) {
        this.writerUid = writerUid;
    }

    public String writerUid;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String reference;


    public Community() {
    }
    public Community(String title,String date, String mainText, String writer,String writerUid) {
        this.title = title;
        this.date = date;
        this.mainText = mainText;
        this.writer = writer;
        this.writerUid = writerUid;
    }

    public Community(String title,String date, String mainText, String writer,String writerUid,String reference) {
        this.title = title;
        this.date = date;
        this.mainText = mainText;
        this.writer = writer;
        this.writerUid = writerUid;
        this.reference = reference;
    }

}
