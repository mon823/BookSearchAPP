package kr.ac.jbnu.se.mm2019Group1.models;

public class User {
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public User(){
        this.nickName ="";
    }
    public User(String nickName){
        this.nickName = nickName;
    }
}
