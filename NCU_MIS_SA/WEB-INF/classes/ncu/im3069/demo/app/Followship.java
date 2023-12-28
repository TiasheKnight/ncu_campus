package ncu.im3069.demo.app;
import ncu.im3069.tools.JsonReader;

import org.json.*;
import java.io;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Followship {
    private int ID, Follower_User_ID, Followed_User_ID;
    private FollowshipHelper FSH = FollowshipHelper.getHelper();

    public Followship(int ID, int Follower_User_ID, int Followed_User_ID) {
        this.ID = ID;
        this.Follower_User_ID = Follower_User_ID;
        this.Followed_User_ID = Followed_User_ID;
        update();
    }

    public int getID(){
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getFollower_User_ID(){
        return Follower_User_ID;
    }
    public void setFollower_User_ID(int Follower_User_ID) {
        this.Follower_User_ID = Follower_User_ID;
    }
    public int getFollowed_User_ID(){
        return Followed_User_ID;
    }
    public void setFollowed_User_ID(int Followed_User_ID) {
        this.Followed_User_ID = Followed_User_ID;
    }
    
    public JSONObject update(){
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = FSH.update(this);
        }
        return data;
    }

    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("Follower_User_ID", getFollower_User_ID());
        jso.put("Followed_User_ID", getFollowed_User_ID());

        return jso;
    } //JSONObject回傳資料
}
