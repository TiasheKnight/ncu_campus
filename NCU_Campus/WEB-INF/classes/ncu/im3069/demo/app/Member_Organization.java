package ncu.im3069.demo.app;
import ncu.im3069.tools.JsonReader;


import org.json.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Member_Organization {
    private int ID, Organization_ID, User_ID;
    private String Authority;
    private ArrayList<Member_Organization> org = new ArrayList<Member_Organization>();
    private Member_OrganizationHelper MOH = Member_OrganizationHelper.getHelper();

    public Member_Organization(int ID, int Organization_ID, int User_ID, String Authority) {
        this.ID = ID;
        this.Organization_ID = Organization_ID;
        this.User_ID = User_ID;
        this.Authority = Authority;
        getOrganizationFromDB(Organization_ID);
    } //Instantiates 一個新的Member_Organization 物件，採用Overload方法進行，此建構子用於

    private void getOrganizationFromDB(int Organization_ID){
        ArrayList<Member_Organization> data = MOH.getMemberOrganizationbyId(Organization_ID);
        org = data;

    } //從DB中取得組織

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrganization_ID() {
        return Organization_ID;
    }
    public void setOrganization_ID(int Organization_ID) {
        this.Organization_ID = Organization_ID;
    }
    
    public String getAuthority() {
        return Authority;
    }
    public void setAuthority(String Authority) {
        this.Authority = Authority;
    }

    public int getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }
    
    public JSONObject update(){
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = MOH.update(this);
        }
        return data;
    }
    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("User_ID", getUser_ID());
        jso.put("Organization_ID", getOrganization_ID());
        jso.put("Authority", getAuthority());

        return jso;
    }
}