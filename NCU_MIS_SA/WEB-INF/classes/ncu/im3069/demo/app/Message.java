package ncu.im3069.demo.app;
import ncu.im3069.tools.JsonReader;


import org.json.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Message {
    private int ID, User_ID, Friends_User_ID;
    private String Message_Content, Message_TimeStamp;
    private MessageHelper MH = MessageHelper.getHelper();

    public Message(int ID, int User_ID, int Friends_User_ID, String Message_Content, String Message_TimeStamp) {
        this.ID = ID;
        this.User_ID = User_ID;
        this.Friends_User_ID = Friends_User_ID;
        this.Message_Content = Message_Content;
        this.Message_TimeStamp = Message_TimeStamp;
        update();
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }
    public int getFriends_User_ID() {
        return Friends_User_ID;
    }
    public void setFriends_User_ID(int Friends_User_ID) {
        this.Friends_User_ID = Friends_User_ID;
    }
    public String getMessage_Content() {
        return Message_Content;
    }
    public void setMessage_Content(String Message_Content) {
        this.Message_Content = Message_Content;
    }
    public String getMessage_TimeStamp() {
        return Message_TimeStamp;
    }
    public void setMessage_TimeStamp(String Message_TimeStamp) {
        this.Message_TimeStamp = Message_TimeStamp;
    }
    public JSONObject update(){
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = MH.update(this);
        }
        return data;
    }

    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("User_ID", getUser_ID());
        jso.put("Friends_User_ID", getFriends_User_ID());
        jso.put("Message_Content", getMessage_Content());
        jso.put("Message_TimeStamp", getMessage_TimeStamp());

        return jso;
    }
}