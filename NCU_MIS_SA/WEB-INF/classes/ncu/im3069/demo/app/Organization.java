package ncu.im3069.demo.app;
import ncu.im3069.tools.JsonReader;

import org.json.*;
import java.io;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Organization {
    private int ID;
    private String Organization_Name, Organization_Detail;
    private OrganizationHelper oh = OrganizationHelper.getHelper();

    public Organization(int ID, String Organization_Name, String Organization_Detail) {
        this.ID = ID;
        this.Organization_Name = Organization_Name;
        this.Organization_Detail = Organization_Detail;
        update();
    }
    public Organization(int ID, String Organization_Name, String Organization_Detail) {
        this.ID = ID;
        this.Organization_Name = Organization_Name;
        this.Organization_Detail = Organization_Detail;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getOrganization_Name() {
        return Organization_Name;
    }
    public void setOrganization_Name(String Organization_Name) {
        this.Organization_Name = Organization_Name;
    }
    public String getOrganization_Detail() {
        return Organization_Detail;
    }
    public void setOrganization_Detail(String Organization_Detail) {
        this.Organization_Detail = Organization_Detail;
    }

    public JSONObject update(){
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = oh.update(this);
        }
        return data;
    }
    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("Organization_Detail", getOrganization_Detail());
        jso.put("Organization_Name", getOrganization_Name());

        return jso;
    }
}
